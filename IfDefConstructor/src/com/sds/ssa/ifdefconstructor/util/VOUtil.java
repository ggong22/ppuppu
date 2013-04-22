package com.sds.ssa.ifdefconstructor.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.TableItem;

import com.sds.ssa.ifdefconstructor.VO.AttrVO;
import com.sds.ssa.ifdefconstructor.VO.CodeVO;

public final class VOUtil {
	public static final AttrVO changeToAttrVO(TableItem tableItem){
		AttrVO attrVO = new AttrVO();
		attrVO.setIfScCd(tableItem.getText(0));
		attrVO.setAttrCd(tableItem.getText(1));
		attrVO.setAttrFldId(tableItem.getText(2));
		attrVO.setAttrFldNm(tableItem.getText(3));
		attrVO.setDataMndtScCd(tableItem.getText(4));
		attrVO.setDataAsnCd(tableItem.getText(5));
		attrVO.setDataTpCd(tableItem.getText(6));
		attrVO.setDataVal(tableItem.getText(7));
		attrVO.setDataLen(tableItem.getText(8));
		attrVO.setRefrTableId(tableItem.getText(9));
		attrVO.setRefrFldId(tableItem.getText(10));
		attrVO.setTableScId(tableItem.getText(11));
		attrVO.setUseYn(tableItem.getText(12));
		
		return attrVO;
	}
	
	public static final CodeVO changeToCodeVO(TableItem tableItem){
		CodeVO codeVO = new CodeVO();
		codeVO.setCode(tableItem.getText(0));
		codeVO.setName(tableItem.getText(1));
		
		return codeVO;
	}
	
	public static final List<String> getNames(List<CodeVO> list){
		List<String> rtnList = new ArrayList<>();
		for(CodeVO codeVO : list){
			String name = codeVO.getName();
			rtnList.add(name);
		}
		return rtnList;
	}
}
