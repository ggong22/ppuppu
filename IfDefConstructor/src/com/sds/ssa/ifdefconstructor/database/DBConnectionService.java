package com.sds.ssa.ifdefconstructor.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.jface.dialogs.MessageDialog;

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
	        String url= //"jdbc:oracle:thin:@182.198.67.141:1621:cwwdbd1";
	       	IfDefConstructorPlugin.getDefault().getPluginPreferences().getString("DB"); 
	        System.out.println(url);
	        if(null == url || "".equals(url)){
	        	String dialogMessage = "Window-Preferences-IfDefConstructor 에서 올바른DB 정보를 입력하세요. \n (ex. jdbc:oracle:thin:@1XX.XXX.XXX.XXX:1XX1:ABCD)";
				MessageDialog.openWarning(null, "Warning", dialogMessage);
				throw new Exception();
	        }
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
