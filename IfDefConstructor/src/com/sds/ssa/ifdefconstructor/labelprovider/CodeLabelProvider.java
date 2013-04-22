package com.sds.ssa.ifdefconstructor.labelprovider;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.sds.ssa.ifdefconstructor.VO.CodeVO;

public class CodeLabelProvider extends LabelProvider implements ITableLabelProvider{

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		CodeVO code = (CodeVO) element;
		switch (columnIndex) {
			case 0:
				return code.getCode();
			case 1:
				return code.getName();
			default:
				throw new RuntimeException("Oh! No!");
		}
	}

}
