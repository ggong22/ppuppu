package com.sds.ssa.ifdefconstructor.provider;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.sds.ssa.ifdefconstructor.VO.CodeVO;

public class CodeProvider implements IStructuredContentProvider {

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object[] getElements(Object inputElement) {
		List<CodeVO> codeList = (List<CodeVO>) inputElement;
		return codeList.toArray();
	}


}
