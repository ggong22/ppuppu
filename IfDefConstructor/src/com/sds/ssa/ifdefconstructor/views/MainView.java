package com.sds.ssa.ifdefconstructor.views;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
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
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.sds.ssa.ifdefconstructor.VO.AttrVO;
import com.sds.ssa.ifdefconstructor.datatransaction.AttrTableDelete;
import com.sds.ssa.ifdefconstructor.datatransaction.AttrTableInqury;
import com.sds.ssa.ifdefconstructor.datatransaction.AttrTableModify;
import com.sds.ssa.ifdefconstructor.labelprovider.AttrLabelProvider;
import com.sds.ssa.ifdefconstructor.provider.AttrProvider;
import com.sds.ssa.ifdefconstructor.util.FileIOUtil;
import com.sds.ssa.ifdefconstructor.util.VOUtil;

public class MainView {
	private Group startGroup; 
	final Table table; 
	private TableViewer viewer;
	final Label l1;
//	final Text t1; 
	final Combo c1;
	private Button exe; 
	private Button addNewIfScCd; 
	private Button addRow; 
	private Button delRow; 
	private Button save; 
	
	
	
	public MainView(final Composite parent){ 
		//콤보관련 array
		final String[] IF_SC_CD = FileIOUtil.getCodeStringFromFile("IF_SC_CD");
		final String[] DATA_MNDT_SC_CD = FileIOUtil.getCodeStringFromFile("DATA_MNDT_SC_CD");
		final String[] DATA_ASN_CD = FileIOUtil.getCodeStringFromFile("DATA_ASN_CD");
		final String[] DATA_TP_CD = FileIOUtil.getCodeStringFromFile("DATA_TP_CD");
		final String[] USE_YN = {"Y", "N"};
		
		final Color delColor = new Color(null, 255,128,128);
		final Color newColor = new Color(null, 254,243,107);
		final Color modColor = new Color(null, 61,224,224);
		final Color defaultColor = new Color(null, 255, 255, 255);
		FormData formData = new FormData();
		parent.setLayout(new FormLayout());
		
		startGroup = new Group(parent, SWT.NULL); 
		startGroup.setText("조회 조건"); 
		formData.left = new FormAttachment(1, -5);
		formData.top = new FormAttachment(1, -5);
		formData.height = 40;
		startGroup.setLayoutData(formData);
		startGroup.setLayout(new FormLayout());

		l1 = new Label(startGroup, SWT.SINGLE);
		l1.setText("인터페이스 구분 :");
		formData = new FormData();
		formData.left = new FormAttachment(0, 10);
		formData.top = new FormAttachment(0, 7);
		l1.setLayoutData(formData);
		
		c1 = new Combo(startGroup, SWT.NONE | SWT.READ_ONLY);
		formData = new FormData();
		formData.left = new FormAttachment(l1, 10);
		formData.top = new FormAttachment(0, 5);
		formData.width = 100;
		c1.setLayoutData(formData);
		String[] comboData = IF_SC_CD;
		for(String temp : comboData){
			c1.add(temp);
		}
		c1.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		exe = new Button(startGroup, SWT.PUSH); 
		exe.setText("조회"); 
		formData = new FormData();
		formData.left = new FormAttachment(c1, 10);
		formData.top = new FormAttachment(0, 5);
		formData.width = 80;
		exe.setLayoutData(formData);
		exe.addSelectionListener(new SelectionAdapter(){ 
			public void widgetSelected(SelectionEvent event) { 
				inquryData();
			}
			
		}); 
		
		addNewIfScCd = new Button(startGroup, SWT.PUSH); 
		addNewIfScCd.setText("구분코드 추가"); 
		formData = new FormData();
		formData.left = new FormAttachment(exe, 10);
		formData.right = new FormAttachment(100, -5);
		formData.top = new FormAttachment(0, 5);
		formData.width = 100;
		addNewIfScCd.setLayoutData(formData);
		addNewIfScCd.addSelectionListener(new SelectionAdapter(){ 
			public void widgetSelected(SelectionEvent event) { 
				new CodePopupView().openPopupForNewIfScCd(parent, "IF_SC_CD");
			}
			
		}); 
		
		viewer = new TableViewer(parent, SWT.H_SCROLL|SWT.V_SCROLL|SWT.FULL_SELECTION);
		final String[] columnHeader = {"IF_SC_CD"
				, "ATTR_CD"
				, "ATTR_FLD_ID"
				, "ATTR_FLD_NM"
				, "DATA_MNDT_SC_CD"
				, "DATA_ASN_CD"
				, "DATA_TP_CD"
				, "DATA_VAL"
				, "DATA_LEN"
				, "REFR_TABLE_ID"
				, "REFR_FLD_ID"
				, "TABLE_SC_ID"
				, "USE_YN"};
		int[] bounds = {80
				, 80
				, 150
				, 150
				, 130
				, 120
				, 100
				, 100
				, 100
				, 100
				, 150
				, 100
				, 80};
		
		final String[] columnType = {"TEXT"
				, "TEXT"
				, "TEXT"
				, "TEXT"
				, "COMBO"
				, "COMBO"
				, "COMBO"
				, "TEXT"
				, "TEXT"
				, "TEXT"
				, "TEXT"
				, "TEXT"
				, "COMBO"};
		
		for(int i = 0 ; i < columnHeader.length ; i++){
			TableViewerColumn tableColumn = new TableViewerColumn(viewer, SWT.NONE);
			tableColumn.getColumn().setText(columnHeader[i]);
			tableColumn.getColumn().setWidth(bounds[i]);
			tableColumn.getColumn().setResizable(true);
			if("COMBO".equals(columnType[i])){
				EditingSupport editingSupport = new ComboEditingSupport(tableColumn.getViewer(), columnHeader[i]);
				tableColumn.setEditingSupport(editingSupport);
			}
		}
		
		table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		formData = new FormData();
		formData.left = new FormAttachment(1, -5);
		formData.top = new FormAttachment(startGroup, 10);
		formData.right = new FormAttachment(100, -20);
		formData.height = 400;
		table.setLayoutData(formData);

		final TableEditor editor = new TableEditor(table);
		editor.horizontalAlignment = SWT.LEFT;
		editor.grabHorizontal = true;
		editor.minimumWidth = 50;
		
		table.addListener(SWT.MouseDown, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				Point pt = new Point(event.x, event.y);
				TableItem item = table.getItem(pt);
				if(item == null) return;
				int index = 0;
				for(int i = 0; i < columnHeader.length ; i++){
					Rectangle rect = item.getBounds(i);
					if(rect.contains(pt)){
						index = i;
						break;
					}
				}

				final int fIndex = index;
				if(fIndex <= 1 ){
					return;	//IF_SC_CD, ATTR_CD 수정 불가
				}
				table.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						Control oldEditor = editor.getEditor();
						if (oldEditor != null) oldEditor.dispose();
						
						TableItem item = (TableItem)e.item;
						if (item == null) return;
						if("COMBO".equals(columnType[fIndex])){
							CCombo combo = new CCombo(table, SWT.NONE | SWT.READ_ONLY);
							String[] comboData = null;
							switch (columnHeader[fIndex]) {
								case "DATA_MNDT_SC_CD":
									comboData = DATA_MNDT_SC_CD;
									break;
								case "DATA_ASN_CD":
									comboData = DATA_ASN_CD;
									break;
								case "DATA_TP_CD":
									comboData = DATA_TP_CD;
									break;
								case "USE_YN":
									comboData = USE_YN;
									break;
								default:
									break;
							}
							for(String temp : comboData){
								combo.add(temp);
							}
							combo.addSelectionListener(new SelectionListener() {
								
								@Override
								public void widgetSelected(SelectionEvent e) {
									// TODO Auto-generated method stub
									CCombo combo = (CCombo) editor.getEditor();
									String originText = editor.getItem().getText(fIndex);
									String newText = combo.getText();
									String[] tempList = newText.split(" ", 2);
									
									if (!originText.equals(tempList[0])){
										editor.getItem().setBackground(fIndex, modColor);
									}
									
									editor.getItem().setText(fIndex, tempList[0]);
								}
								
								@Override
								public void widgetDefaultSelected(SelectionEvent e) {
									// TODO Auto-generated method stub
									
								}
							});
							editor.setEditor(combo, item, fIndex);
						}else{
							Text newEditor = new Text(table, SWT.NONE);
							newEditor.setText(item.getText(fIndex));
							newEditor.addModifyListener(new ModifyListener() {
								public void modifyText(ModifyEvent me) {
									Text text = (Text)editor.getEditor();
									String originText = editor.getItem().getText(fIndex);
									String newText = text.getText();
									if(null == originText){
										if(null != newText){
											editor.getItem().setBackground(fIndex, modColor);
										}
									}else if (!originText.equals(newText)){
										editor.getItem().setBackground(fIndex, newColor);
									}
									
									editor.getItem().setText(fIndex, text.getText());
								}
							});
							newEditor.selectAll();
							newEditor.setFocus();
							editor.setEditor(newEditor, item, fIndex);
						}
						
						save.setFocus();
					}
				});
			}
		});
		
		viewer.setContentProvider(new AttrProvider());
		viewer.setLabelProvider(new AttrLabelProvider());
		
		delRow = new Button(parent, SWT.PUSH); 
		delRow.setText("행삭제"); 
		formData = new FormData();
		formData.right = new FormAttachment(100, -20);
		formData.bottom = new FormAttachment(table, -5);
		formData.width = 80;
		delRow.setLayoutData(formData);
		delRow.addSelectionListener(new SelectionAdapter(){ 
			public void widgetSelected(SelectionEvent event) { 
				int selectedIndex = table.getSelectionIndex();
				TableItem item = table.getItem(selectedIndex);

				TableItem lastItem = table.getItem(table.getItemCount()-1);
				if(newColor.equals(item.getBackground(0))){
					//새로 추가된 Row
					item.setBackground(0, defaultColor);
					item.setBackground(1, defaultColor);
					//선택한 위치 이하의 라인 내용을 다시 옮기고
					for(int i = selectedIndex ; i < table.getItemCount()-1 ; i++){
						TableItem upItem = table.getItem(i);
						TableItem downItem = table.getItem(i+1);
						for(int j = 2; j < columnHeader.length ; j++){
							upItem.setText(j, downItem.getText(j));
							
							if(!newColor.equals(downItem.getBackground(j))){
								upItem.setBackground(j, downItem.getBackground(j));
							}else{
								upItem.setBackground(j, defaultColor);
							}
						}
					}
					table.remove(table.getItemCount()-1);
					return;
				}
				
				
				item.setBackground(delColor);
				if(!item.equals(lastItem)){
					for(int i = table.getItemCount()-1 ; i > selectedIndex ; i--){
						TableItem upItem = table.getItem(i-1);
						TableItem downItem = table.getItem(i);
						downItem.setText(1, upItem.getText(1));
						downItem.setBackground(1, newColor);
					}
				}
			}
			
		}); 
		
		addRow = new Button(parent, SWT.PUSH); 
		addRow.setText("행추가"); 
		formData = new FormData();
		formData.right = new FormAttachment(delRow, -5);
		formData.bottom = new FormAttachment(table, -5);
		formData.width = 80;
		addRow.setLayoutData(formData);
		addRow.addSelectionListener(new SelectionAdapter(){ 
			public void widgetSelected(SelectionEvent event) { 
				//선택정보
				int selectedIndex = table.getSelectionIndex();
				TableItem item = table.getItem(selectedIndex);
				String ifScCd = item.getText(0);
				//행추가 - 추가된 행에 attr_cd와 if_sc_Cd 넣어주기
				AttrVO newAttrVO = new AttrVO();
				newAttrVO.setIfScCd(ifScCd);
				newAttrVO.setAttrCd(getNewLastAttrCd());
				viewer.add(newAttrVO);
				
				//선택한 위치 이하의 라인 내용을 옮기고
				for(int i = table.getItemCount()-1 ; i >= selectedIndex ; i--){
					TableItem upItem = table.getItem(i-1);
					TableItem downItem = table.getItem(i);
					for(int j = 2; j < columnHeader.length ; j++){
						downItem.setText(j, upItem.getText(j));
						if(modColor.equals(upItem.getBackground(j))){
							downItem.setBackground(j, modColor);
						}else{
							downItem.setBackground(j, newColor);
						}
					}
				}
				//빈 Row에 default값 넣어주기
				item.setText(2, "");
				item.setText(3, "");
				item.setText(4, "");
				item.setText(5, "");
				item.setText(6, "");
				item.setText(7, "");
				item.setText(8, "");
				item.setText(9, "");
				item.setText(10, "");
				item.setText(11, "");
				item.setText(12, "");
				item.setBackground(newColor);
			}

			private String getNewLastAttrCd() {
				TableItem lastItem = table.getItem(table.getItemCount()-1);
				String lastAttrCd = lastItem.getText(1);
				BigDecimal nowLast = new BigDecimal(lastAttrCd);
				String newlastAttrCd = nowLast.add(BigDecimal.ONE).toString();
				if(newlastAttrCd.length() == 1){
					newlastAttrCd = "0" + newlastAttrCd;
				}
				return newlastAttrCd;
			}
			
		}); 
		
		save = new Button(parent, SWT.PUSH); 
		save.setText("저장"); 
		formData = new FormData();
		formData.right = new FormAttachment(100, -20);
		formData.top = new FormAttachment(table, 10);
		formData.width = 80;
		save.setLayoutData(formData);
		save.addSelectionListener(new SelectionAdapter(){ 
			public void widgetSelected(SelectionEvent event) { 
				int delCnt = 0;
				List<AttrVO> modList = new ArrayList<AttrVO>();
				//변경된 데이터 있는 지 체크
				TableItem[] tableItems = table.getItems();
				String ifScCd = "";
				for(TableItem tableItem : tableItems){
					for(int i = 0; i < columnHeader.length ; i++){
						if(!defaultColor.equals(tableItem.getBackground(i))){
							AttrVO attrVO = VOUtil.changeToAttrVO(tableItem);
							modList.add(attrVO);
							
							if(delColor.equals(tableItem.getBackground(i))){
								ifScCd = tableItem.getText(0);
								delCnt++;
							}
							break;
						}
					}
				}
				AttrTableModify modify = new AttrTableModify();
				modify.updateAttrMt(modList);
				
				if(delCnt > 0){
					AttrTableDelete delete = new AttrTableDelete();
					delete.deleteAttrMt(ifScCd, delCnt);
				}
				
				inquryData();
			} 
		});
		
		
	}
	
	private void inquryData() {
		AttrTableInqury inqury = new AttrTableInqury();
		String param = c1.getText();
		String[] tempList = param.split(" ", 2);
		viewer.setInput(inqury.selectAttrMt(tempList[0]));
	} 

}
