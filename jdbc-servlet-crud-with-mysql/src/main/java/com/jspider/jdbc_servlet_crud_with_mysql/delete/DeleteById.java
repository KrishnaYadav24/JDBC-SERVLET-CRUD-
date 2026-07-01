package com.jspider.jdbc_servlet_crud_with_mysql.delete;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.jspider.jdbc_servlet_crud_with_mysql.connection.JdbcEmployeeConnection;

public class DeleteById {

	public static void deleteId() {
		Scanner sc = new Scanner(System.in);
		// Step - 1 import jdbc pakages
		Connection connection = null;
		Statement statement = null;
		// Scanner sc = new Scanner(System.in);

		try {

			// Step - 4 Create Statement

			connection = JdbcEmployeeConnection.getJdbcEmployeeConnection();
			statement = connection.createStatement();
			
			
			//user input 
			
			System.out.print("Enter the ID which you want to delete : ");
			int id = sc.nextInt();
			
			
			// Step - 5 Execute Query
			String deleteemployeeQuery = "DELETE FROM employee where id = " + id;

			int a = statement.executeUpdate(deleteemployeeQuery);

			if (a == 0) {
				System.out.println("ID  not Found .......... pleease check and enter correct id ......");
			} else {
				System.out.println("Hurrah finally Data ............kaam pura hogaya");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		finally {
			try {

				if (statement != null)
					statement.close();

				if (connection != null)
					connection.close();

				sc.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
}
