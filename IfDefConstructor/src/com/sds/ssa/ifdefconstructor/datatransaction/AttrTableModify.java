package com.sds.ssa.ifdefconstructor.datatransaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.sds.ssa.ifdefconstructor.VO.AttrVO;
import com.sds.ssa.ifdefconstructor.database.DBConnectionService;

public class AttrTableModify {
	public void updateAttrMt(List<AttrVO> attrVOList) {

		DBConnectionService dbConn = new DBConnectionService();
		Connection con = dbConn.connectDB();
		String sql = "MERGE INTO CCD_IF_ATTR_MT A " +
				"USING DUAL " +
				"ON (    A.IF_SC_CD = ?" +
				" AND A.ATTR_CD = ? )" +
				" WHEN MATCHED THEN" +
				" UPDATE SET" +
				" A.ATTR_FLD_ID = ?" +
				" , A.ATTR_FLD_CAML_ID = ?" +
				" , A.ATTR_FLD_NM = ?" +
				" , A.DATA_MNDT_SC_CD = ?" +
				" , A.DATA_ASN_CD = ?" +
				" , A.DATA_TP_CD = ?" +
				" , A.DATA_VAL = ?" +
				" , A.DATA_LEN = ?" +
				" , A.REFR_TABLE_ID = ?" +
				" , A.REFR_FLD_ID = ?" +
				" , A.CAML_ID = ?" +
				" , A.TABLE_SC_ID = ?" +
				" , A.USE_YN = ?" +
				" , A.SYS_MOD_DTM = TO_CHAR(SYSDATE,'yyyymmddhh24miss')" +
				" , A.SYS_MOD_EMPNO = 'ADMIN'" +
				" WHEN NOT MATCHED THEN" +
				" INSERT VALUES(" +
				"   ?" +
				" , ?" +
				" , ?  " +
				" , ?" +
				" , ?" +
				" , ?" +
				" , ?" +
				" , ?" +
				" , ?" +
				" , ?" +
				" , ?" +
				" , ?" +
				" , ?" +
				" , ?" +
				" , ?" +
				" , TO_CHAR(SYSDATE,'yyyymmddhh24miss') " +
				" , 'ADMIN'" +
				" , TO_CHAR(SYSDATE,'yyyymmddhh24miss')" +
				" , 'ADMIN'  )";
		

		try {
			PreparedStatement st;
			for (AttrVO attrVO : attrVOList) {
				String attrFldCamlId = this.getCamlStyle(attrVO.getAttrFldId());
				String camlId = this.getCamlStyle(attrVO.getRefrFldId());

				st = con.prepareStatement(sql);
				st.setString(1, attrVO.getIfScCd());
				st.setString(2, attrVO.getAttrCd());
				st.setString(3, attrVO.getAttrFldId());
				st.setString(4, attrFldCamlId);
				st.setString(5, attrVO.getAttrFldNm());
				st.setString(6, attrVO.getDataMndtScCd());
				st.setString(7, attrVO.getDataAsnCd());
				st.setString(8, attrVO.getDataTpCd());
				st.setString(9, attrVO.getDataVal());
				st.setString(10, attrVO.getDataLen());
				st.setString(11, attrVO.getRefrTableId());
				st.setString(12, attrVO.getRefrFldId());
				st.setString(13, camlId);
				st.setString(14, attrVO.getTableScId());
				st.setString(15, attrVO.getUseYn());
				st.setString(16, attrVO.getIfScCd());
				st.setString(17, attrVO.getAttrCd());
				st.setString(18, attrVO.getAttrFldId());
				st.setString(19, attrFldCamlId);
				st.setString(20, attrVO.getAttrFldNm());
				st.setString(21, attrVO.getDataMndtScCd());
				st.setString(22, attrVO.getDataAsnCd());
				st.setString(23, attrVO.getDataTpCd());
				st.setString(24, attrVO.getDataVal());
				st.setString(25, attrVO.getDataLen());
				st.setString(26, attrVO.getRefrTableId());
				st.setString(27, attrVO.getRefrFldId());
				st.setString(28, camlId);
				st.setString(29, attrVO.getTableScId());
				st.setString(30, attrVO.getUseYn());

				st.executeQuery();

				st.close();

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			dbConn.closeConnection(con);
			e.printStackTrace();
		}
		try {
			con.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbConn.closeConnection(con);

	}

	private String getCamlStyle(String attrFldId) {
		int length = attrFldId.length();
		String rtn = "";
		String temp = "";
		for (int i = 0; i < length; i++) {
			char letter = attrFldId.charAt(i);
			if ('_' == letter) {
				letter = attrFldId.charAt(i + 1);
				temp = "" + letter;
				i++;
			} else if( i > 0 ){
				temp = "" + letter;
				temp = temp.toLowerCase();
			}else{
				temp = "" + letter;
			}
			rtn += temp;
		}
		return rtn;
	}
	
}
