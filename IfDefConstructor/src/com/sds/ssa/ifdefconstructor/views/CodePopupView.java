package com.sds.ssa.ifdefconstructor.views;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.sds.ssa.ifdefconstructor.VO.AttrVO;
import com.sds.ssa.ifdefconstructor.VO.CodeVO;
import com.sds.ssa.ifdefconstructor.datatransaction.AttrTableModify;
import com.sds.ssa.ifdefconstructor.labelprovider.CodeLabelProvider;
import com.sds.ssa.ifdefconstructor.provider.CodeProvider;
import com.sds.ssa.ifdefconstructor.util.FileIOUtil;
import com.sds.ssa.ifdefconstructor.util.VOUtil;

public class CodePopupView {
	static TableViewer tViewer;
	static Table tb;
	
	public void openPopupForNewIfScCd(Composite parent, final String code) {
		final Display display = parent.getDisplay();
		display.syncExec(new Runnable() {
			
			@Override
			public void run() {
				Shell shell = new Shell(display);
				shell.setText("구분코드 추가");
				shell.setBounds(100,100,350,220);
				shell.setLayout(new FormLayout());
				
				Button addRow = new Button(shell, SWT.PUSH); 
				addRow.setText("추가"); 
				FormData formData = new FormData();
				formData.right = new FormAttachment(100, -60);
				formData.top = new FormAttachment(0, 5);
				formData.width = 50;
				addRow.setLayoutData(formData);
				addRow.addSelectionListener(new SelectionAdapter(){ 
					public void widgetSelected(SelectionEvent event) { 
						CodeVO newVO = new CodeVO();
						newVO.setCode("");
						newVO.setName("");
						tViewer.add(newVO);
					}
					
				}); 
				
				tViewer = new TableViewer(shell, SWT.SINGLE | SWT.MULTI | SWT.FULL_SELECTION);
				tb = tViewer.getTable();
				tb.setHeaderVisible(true);
				tb.setLinesVisible(true);
				formData = new FormData();
				formData.left = new FormAttachment(0, 5);
				formData.right = new FormAttachment(100, -5);
				formData.top = new FormAttachment(addRow, 5);
				tb.setLayoutData(formData);
				
				final String[] columnNames = new String[]{"코드", "명칭 및 설명"};
				int[] columnWidths = new int[]{100, 200};
				int[] columnAligns = new int[]{SWT.LEFT, SWT.LEFT};
				for(int i = 0; i < columnNames.length; i++){
					TableColumn tableColumn = new TableColumn(tb, columnAligns[i]);
					tableColumn.setText(columnNames[i]);
					tableColumn.setWidth(columnWidths[i]);
				}
				
				final TableEditor editor = new TableEditor(tb);
				editor.horizontalAlignment = SWT.LEFT;
				editor.grabHorizontal = true;
				editor.minimumWidth = 50;
				
				tb.addListener(SWT.MouseDown, new Listener() {
					
					@Override
					public void handleEvent(Event event) {
						Point pt = new Point(event.x, event.y);
						TableItem item = tb.getItem(pt);
						if(item == null) return;
						int index = 0;
						for(int i = 0; i < columnNames.length ; i++){
							Rectangle rect = item.getBounds(i);
							if(rect.contains(pt)){
								index = i;
								break;
							}
						}
						
						final int fIndex = index;
						if(fIndex == 0 && !"".equals(item.getText(fIndex))){
							return;	//ATTR_CD 수정 불가
						}
						tb.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent e) {
								// Clean up any previous editor control
								Control oldEditor = editor.getEditor();
								if (oldEditor != null) oldEditor.dispose();
								
								// Identify the selected row
								TableItem item = (TableItem)e.item;
								if (item == null) return;
								
								// The control that will be the editor must be a child of the Table
								Text newEditor = new Text(tb, SWT.NONE);
								newEditor.setText(item.getText(fIndex));
								newEditor.addModifyListener(new ModifyListener() {
									public void modifyText(ModifyEvent me) {
										Color color = new Color(null, 254,243,107);
										Text text = (Text)editor.getEditor();
										String originText = editor.getItem().getText(fIndex);
										String newText = text.getText();
										if(null == originText){
											if(null != newText){
												editor.getItem().setBackground(fIndex, color);
											}
										}else if (!originText.equals(newText)){
											editor.getItem().setBackground(fIndex, color);
										}
										
										editor.getItem().setText(fIndex, text.getText());
									}
								});
								newEditor.selectAll();
								newEditor.setFocus();
								editor.setEditor(newEditor, item, fIndex);
							}
						});
					}
				});
				
				tViewer.setLabelProvider(new CodeLabelProvider());
				tViewer.setContentProvider(new CodeProvider());
				tViewer.setInput(FileIOUtil.getCodeListFromFile(code));	
				
				Button save = new Button(shell, SWT.PUSH); 
				save.setText("저장"); 
				formData = new FormData();
				formData.right = new FormAttachment(100, -5);
				formData.top = new FormAttachment(0, 5);
				formData.width = 50;
				save.setLayoutData(formData);
				save.addSelectionListener(new SelectionAdapter(){ 
					public void widgetSelected(SelectionEvent event) { 
						List<CodeVO> list = new ArrayList<CodeVO>();
						//변경된 데이터 있는 지 체크
						TableItem[] tableItems = tb.getItems();
						for(TableItem tableItem : tableItems){
							CodeVO codeVO = VOUtil.changeToCodeVO(tableItem);
							list.add(codeVO);
						}
						FileIOUtil.setCodeListToFile("IF_SC_CD", list);
						tViewer.setInput(FileIOUtil.getCodeListFromFile(code));
					}
					
				}); 
				
				shell.open();
				while (!shell.isDisposed()){
					if(!display.readAndDispatch()){
						display.sleep();
					}
				}
			}
		});
				
			     
		
		
	}
}
