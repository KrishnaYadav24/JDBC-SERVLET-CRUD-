package com.jspider.jdbc_servlet_crud_with_mysql.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.jspider.jdbc_servlet_crud_with_mysql.DTO.Employee;
import com.jspider.jdbc_servlet_crud_with_mysql.connection.JdbcEmployeeConnection;
import com.jspider.jdbc_servlet_crud_with_mysql.displayconnection.EmployeeData;

public class DisplayEmployeeController {
	public static void displayEmployee() {

		// Step - 1 import jdbc pakages
		Connection connection = null;
		Statement statement = null;
		ResultSet resultset = null;
		try {

			// Handled in jdbcEmployeeconnection.java which include step -2 Get database
			// connection and step -3 Create Statement
			connection = JdbcEmployeeConnection.getJdbcEmployeeConnection();

			// Step - 4 Create Statement
			statement = connection.createStatement();

			// Step - 5 Execute Query
			resultset = statement.executeQuery("select * from employee");

			System.out.println("Employee .......Details");

			// Create Object of EmployeeData Class
			EmployeeData data = new EmployeeData();

			while (resultset.next()) {

				System.out.println("\n\n");
				System.out.println("__________________________________________________________________________");
				System.out.println("\n\n");

				Employee employee = data.getEmployee(resultset);
				System.out.println(employee);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		finally {
			try {
				if (resultset != null)
					resultset.close();

				if (statement != null)
					statement.close();

				if (connection != null)
					connection.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
}
