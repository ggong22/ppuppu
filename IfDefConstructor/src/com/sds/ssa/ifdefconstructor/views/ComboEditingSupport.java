package com.sds.ssa.ifdefconstructor.views;

import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.sds.ssa.ifdefconstructor.VO.CodeVO;
import com.sds.ssa.ifdefconstructor.util.FileIOUtil;
import com.sds.ssa.ifdefconstructor.util.VOUtil;

public class ComboEditingSupport extends EditingSupport {
	
	private ComboBoxViewerCellEditor cellEditor = null;

	public ComboEditingSupport(ColumnViewer viewer) {
		super(viewer);
		// TODO Auto-generated constructor stub
	}

	public ComboEditingSupport(ColumnViewer viewer, String code) {
		super(viewer);
		cellEditor = new ComboBoxViewerCellEditor((Composite) getViewer()
				.getControl(), SWT.READ_ONLY);
		cellEditor.setLabelProvider(new LabelProvider());
		cellEditor.setContenProvider(new ArrayContentProvider());
		cellEditor.setInput(VOUtil.getNames(FileIOUtil.getCodeListFromFile(code)));
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		return cellEditor;
	}

	@Override
	protected boolean canEdit(Object element) {
		return false;
	}

	@Override
	protected Object getValue(Object element) {
		return null;
	}

	@Override
	protected void setValue(Object element, Object value) {
	}

}
