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
			
	        System.out.println("����̹� �ε� ����...");
	        String url= //"jdbc:oracle:thin:@182.198.67.141:1621:cwwdbd1";
	       	IfDefConstructorPlugin.getDefault().getPluginPreferences().getString("DB"); 
	        System.out.println(url);
	        if(null == url || "".equals(url)){
	        	String dialogMessage = "Window-Preferences-IfDefConstructor ���� �ùٸ�DB ������ �Է��ϼ���. \n (ex. jdbc:oracle:thin:@1XX.XXX.XXX.XXX:1XX1:ABCD)";
				MessageDialog.openWarning(null, "Warning", dialogMessage);
				throw new Exception();
	        }
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
