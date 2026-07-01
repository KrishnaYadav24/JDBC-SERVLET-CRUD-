package com.jspider.jdbc_servlet_crud_with_mysql.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.jspider.jdbc_servlet_crud_with_mysql.connection.JdbcEmployeeConnection;

public class InsertEmployeeController {

	public static void insertEmployee() {
		// Step - 1 import jdbc pakages
		Connection connection = null;
		Statement statement = null;
		Scanner sc = new Scanner(System.in);

		try {

			// Step - 4 Create Statement
			// statement = connection.createStatement();

			// Step - 5 Execute Query

			System.out.print("Enter Id : ");
			int id = sc.nextInt();

			System.out.print("Enter Name : ");
			String name = sc.next();

			System.out.print("Enter Email : ");
			String email = sc.next();

			System.out.print("Enter Password : ");
			String password = sc.next();

			System.out.print("Enter Phone Number : ");
			long phone = sc.nextLong();

			System.out.print("Enter Salary : ");
			double salary = sc.nextDouble();

			System.out.print("Enter DOJ (yyyy-mm-dd) : ");
			String doj = sc.next();

			connection = JdbcEmployeeConnection.getJdbcEmployeeConnection();
			statement = connection.createStatement();

			String insertemployeeQuery = "insert into employee(id,name,email,password,phoneno,salary,doj) values(" + id
					+ ",'" + name + "','" + email + "','" + password + "'," + phone + "," + salary + ",'" + doj + "')";

			int a = statement.executeUpdate(insertemployeeQuery);

			if (a == 0) {
				System.out.println("Something went wrong");
			} else {
				System.out.println("Hurrah finally Data inserted............kaam pura hogaya");
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
