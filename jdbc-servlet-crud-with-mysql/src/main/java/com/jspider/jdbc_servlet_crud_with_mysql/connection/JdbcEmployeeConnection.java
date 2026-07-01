package com.jspider.jdbc_servlet_crud_with_mysql.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//import java.sql.Statement;

public class JdbcEmployeeConnection {
	
	public static Connection getJdbcEmployeeConnection() {
		
		//Step - 1 import jdbc pakages
		Connection connection = null;
		//Statement statement = null;
		try {
			//STEP -2 LOAD REGISTER DRIVERSOFTWARE
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			String url = "jdbc:mysql://localhost:3306/jdbc-a15";
			String user = "root";
			String pass = "Tiger";
			connection = DriverManager.getConnection(url, user, pass);
			
			return connection;
		}catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
		

	}
}
