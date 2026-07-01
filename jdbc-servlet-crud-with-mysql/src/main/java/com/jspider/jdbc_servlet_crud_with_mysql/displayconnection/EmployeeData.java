package com.jspider.jdbc_servlet_crud_with_mysql.displayconnection;

//import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Statement;
import java.time.LocalDate;

import com.jspider.jdbc_servlet_crud_with_mysql.DTO.Employee;
//import com.jspider.jdbc_servlet_crud_with_mysql.connection.JdbcEmployeeConnection;

public class EmployeeData {

	public Employee getEmployee(ResultSet resultset) {

		try {

			

				int id = resultset.getInt("id");
				String name = resultset.getString("name");
				String email = resultset.getString("email");
				String password = resultset.getString("password");
				long phone = resultset.getLong("phoneno");
				double salary = resultset.getDouble("salary");
				Date doj = resultset.getDate("doj");
				LocalDate doj1 = doj.toLocalDate();

				Employee employee = new Employee();

				employee.setId(id);
				employee.setName(name);
				employee.setEmail(email);
				employee.setPassword(password);
				employee.setPhoneno(phone);
				employee.setSalary(salary);
				employee.setDoj(doj1);

				return employee;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

}