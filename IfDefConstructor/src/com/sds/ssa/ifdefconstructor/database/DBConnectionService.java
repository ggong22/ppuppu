package com.sds.ssa.ifdefconstructor.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.eclipse.core.runtime.Plugin;

import com.sds.ssa.ifdefconstructor.IfDefConstructorPlugin;

public class DBConnectionService {
	private Connection con = null;
	
	public Connection connectDB(){
		try {
			if(null != con){
				return con;
			}
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
	        System.out.println("드라이버 로딩 성공...");
	        String url= //"jdbc:oracle:thin:@182.198.66.141:1621:cwwdbd1";
	       	IfDefConstructorPlugin.getDefault().getPluginPreferences().getString("DB"); 
	        System.out.println(url);
	        System.out.println(IfDefConstructorPlugin.getDefault().getPluginPreferences().getString("DB"));
	        String user="IPMDEV";
	        String pwd="dev";
	          
	        con= DriverManager.getConnection(url,user,pwd);
	        System.out.println("DB 연결 성공!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return con;
	}
	
	public void closeConnection(Connection con){
		try {
			con.close();
			System.out.println("DB close 성공!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
