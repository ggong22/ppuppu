package com.sds.ssa.ifdefconstructor.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.FileLocator;
import org.osgi.framework.Bundle;

import com.sds.ssa.ifdefconstructor.IfDefConstructorPlugin;
import com.sds.ssa.ifdefconstructor.VO.CodeVO;

public final class FileIOUtil {
	
	public static final List<CodeVO> getCodeListFromFile(String code){
		FileReader fr = null;
		List<CodeVO> rtnList = new ArrayList<>();
	    Bundle bundle= IfDefConstructorPlugin.getDefault().getBundle();
	    URL resource = null;
		try {
			switch (code) {
				case "IF_SC_CD":
					resource = bundle.getEntry("res/IfScCd.txt");
					break;
				case "DATA_MNDT_SC_CD":
					resource = bundle.getEntry("res/DataMndtScCd.txt");
					break;
				case "DATA_ASN_CD":
					resource = bundle.getEntry("res/DataAsnCd.txt");
					break;
				case "DATA_TP_CD":
					resource = bundle.getEntry("res/DataTpCd.txt");
					break;
				case "USE_YN":
					CodeVO codeY = new CodeVO();
					codeY.setCode("Y");
					codeY.setName("Y");
					rtnList.add(codeY);
					
					CodeVO codeN = new CodeVO();
					codeN.setCode("N");
					codeN.setName("N");
					rtnList.add(codeN);
					
					return rtnList;
				default:
					break;
			}
			fr = new FileReader(new File(FileLocator.resolve(resource).toURI()));
			BufferedReader br = new BufferedReader(fr);
			
			String line;
			
			while((line = br.readLine()) != null){
				String temp = line;
				String[] tempList = temp.split(" ", 2);
				CodeVO codeVO = new CodeVO();
				codeVO.setCode(tempList[0]);
				codeVO.setName(tempList[1]);
				rtnList.add(codeVO);
			}
			
			br.close();
			fr.close();
			
		} catch (IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rtnList;
	}
	
	public static final String[] getCodeStringFromFile(String code){
		FileReader fr = null;
		List<String> rtnList = new ArrayList<>();
		String[] returnArr = null;
	    Bundle bundle= IfDefConstructorPlugin.getDefault().getBundle();
	    URL resource = null;
		try {
			switch (code) {
				case "IF_SC_CD":
					resource = bundle.getEntry("res/IfScCd.txt");
					break;
				case "DATA_MNDT_SC_CD":
					resource = bundle.getEntry("res/DataMndtScCd.txt");
					break;
				case "DATA_ASN_CD":
					resource = bundle.getEntry("res/DataAsnCd.txt");
					break;
				case "DATA_TP_CD":
					resource = bundle.getEntry("res/DataTpCd.txt");
					break;
				default:
					break;
			}
			fr = new FileReader(new File(FileLocator.resolve(resource).toURI()));
			BufferedReader br = new BufferedReader(fr);
			
			String line;
			
			while((line = br.readLine()) != null){
				String temp = line;
				rtnList.add(temp);
			}
			
			br.close();
			fr.close();
			
			returnArr = new String[rtnList.size()];
			for(int i = 0; i < rtnList.size() ; i++){
				returnArr[i] = rtnList.get(i);
			}
		} catch (IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnArr;
	}
	
	public static final void setCodeListToFile(String code, List<CodeVO> list){
		FileReader fr = null;
		FileWriter fw = null;
	    Bundle bundle= IfDefConstructorPlugin.getDefault().getBundle();
	    URL resource = null;
		try {
			switch (code) {
				case "IF_SC_CD":
					resource = bundle.getEntry("res/IfScCd.txt");
					break;
				default:
					break;
			}
			fr = new FileReader(new File(FileLocator.resolve(resource).toURI()));
			fw = new FileWriter(new File(FileLocator.resolve(resource).toURI()));
			
			BufferedReader br = new BufferedReader(fr);
			BufferedWriter bw = new BufferedWriter(fw);
			
			String line;
			
			while((line = br.readLine()) != null){
				bw.write("");
			}
			
			for(CodeVO codeVO : list){
				String newCode = codeVO.getCode();
				String newName = codeVO.getName();
				
				String writeString = newCode + " " + newName;
				bw.write(writeString);
				bw.newLine();
			}
			br.close();
			bw.close();
			fr.close();
			fw.close();
			
		} catch (IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
