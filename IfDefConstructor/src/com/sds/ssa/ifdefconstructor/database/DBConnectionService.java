package com.sds.ssa.ifdefconstructor.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionService {
	private Connection con = null;
	
	public Connection connectDB(){
		try {
			if(null != con){
				return con;
			}
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
	        System.out.println("����̹� �ε� ����...");
	        String url="jdbc:oracle:thin:@182.198.66.141:1621:cwwdbd1";
	          
	        String user="IPMDEV";
	        String pwd="dev";
	          
	        con= DriverManager.getConnection(url,user,pwd);
	        System.out.println("DB ���� ����!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return con;
	}
	
	public void closeConnection(Connection con){
		try {
			con.close();
			System.out.println("DB close ����!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}