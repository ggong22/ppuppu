package com.sds.ssa.ifdefconstructor.labelprovider;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.sds.ssa.ifdefconstructor.VO.AttrVO;

public class AttrLabelProvider extends LabelProvider implements ITableLabelProvider{

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		AttrVO attr = (AttrVO) element;
		switch (columnIndex) {
			case 0:
				return attr.getIfScCd();
			case 1:
				return attr.getAttrCd();
			case 2:
				return attr.getAttrFldId();
			case 3:
				return attr.getAttrFldNm();
			case 4:
				return attr.getDataMndtScCd();
			case 5:
				return attr.getDataAsnCd();
			case 6:
				return attr.getDataTpCd();
			case 7:
				return attr.getDataVal();
			case 8:
				return attr.getDataLen();
			case 9:
				return attr.getRefrTableId();
			case 10:
				return attr.getRefrFldId();
			case 11:
				return attr.getTableScId();
			case 12:
				return attr.getUseYn();
			default:
				throw new RuntimeException("Oh! No!");
		}
	}

}
