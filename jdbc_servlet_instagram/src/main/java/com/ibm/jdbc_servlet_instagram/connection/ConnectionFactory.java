package com.ibm.jdbc_servlet_instagram.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.cj.jdbc.Driver;

public class ConnectionFactory {
	
	public static Connection getConnectionFactory() throws SQLException {
		
		//step-1 load/Register Driver
		Driver driver = new Driver();
		DriverManager.registerDriver(driver);
		
		//step-2 create connection
		final String  URL = "jdbc:mysql://localhost:3306/jdbc-a15";
		final String USER = "root";
		final String PASS = "Tiger";
		
		
		return DriverManager.getConnection(URL, USER, PASS);
	}

}
