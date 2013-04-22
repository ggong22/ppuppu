package com.sds.ssa.ifdefconstructor.datatransaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.sds.ssa.ifdefconstructor.VO.AttrVO;
import com.sds.ssa.ifdefconstructor.database.DBConnectionService;

public class AttrTableDelete {
	public void deleteAttrMt(String ifScCd, int delCnt) {

		DBConnectionService dbConn = new DBConnectionService();
		Connection con = dbConn.connectDB();
		String sql = "DELETE FROM CCD_IF_ATTR_MT" +
				  	 " WHERE IF_SC_CD = ?" +
				  	 "   AND ATTR_CD = ( SELECT MAX(ATTR_CD)" +
				  	 "					   FROM CCD_IF_ATTR_MT" +
				  	 "					  WHERE IF_SC_CD = ? )";
		

		try {
			PreparedStatement st;
			for (int i = 0 ; i < delCnt ; i++) {

				st = con.prepareStatement(sql);
				st.setString(1, ifScCd);
				st.setString(2, ifScCd);

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

}
