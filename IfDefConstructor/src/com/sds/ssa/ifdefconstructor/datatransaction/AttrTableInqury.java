package com.sds.ssa.ifdefconstructor.datatransaction;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sds.ssa.ifdefconstructor.VO.AttrVO;
import com.sds.ssa.ifdefconstructor.database.DBConnectionService;

public class AttrTableInqury {
	public List<AttrVO> selectAttrMt(String ifScCdParam){
		List<AttrVO> returnList = new ArrayList<AttrVO>();
		
		DBConnectionService dbConn = new DBConnectionService();
		Connection con = dbConn.connectDB();
		
		PreparedStatement st;
		try {
	        String sql="SELECT * FROM CCD_IF_ATTR_MT WHERE IF_SC_CD = ? ORDER BY TO_NUMBER(ATTR_CD)";
			st = con.prepareStatement(sql);
			st.setString(1, ifScCdParam);
	        ResultSet rs= st.executeQuery();
	        
	        
	        while(rs.next()){
	            String ifScCd =rs.getString(1);
	            String attrCd =rs.getString(2);
	            String attrFldId =rs.getString(3);
	            String attrFldNm =rs.getString(5);
	            String dataMndtScCd =rs.getString(6);
	            String dataAsnCd =rs.getString(7);
	            String dataTpCd =rs.getString(8);
	            String dataVal =rs.getString(9);
	            String dataLen =rs.getString(10);
	            String refrTableId =rs.getString(11);
	            String refrFldId =rs.getString(12);
	            String tableScId =rs.getString(14);
	            String useYn =rs.getString(15);

	        	AttrVO vo = new AttrVO();
	        	vo.setIfScCd(ifScCd);
	        	vo.setAttrCd(attrCd);
	        	vo.setAttrFldId(attrFldId);
	        	vo.setAttrFldNm(attrFldNm);
	        	vo.setDataMndtScCd(dataMndtScCd);
	        	vo.setDataAsnCd(dataAsnCd);
	        	vo.setDataTpCd(dataTpCd);
	        	vo.setDataVal(dataVal);
	        	vo.setDataLen(dataLen);
	        	vo.setRefrTableId(refrTableId);
	        	vo.setRefrFldId(refrFldId);
	        	vo.setTableScId(tableScId);
	        	vo.setUseYn(useYn);
	        	
	        	returnList.add(vo);
	    
	        }//while---------
	          
	        rs.close();
	        st.close();
	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
	        dbConn.closeConnection(con);
			e.printStackTrace();
		}
        dbConn.closeConnection(con);
		return returnList;
	}
}
