package com.ibm.jdbc_servlet_instagram.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.ibm.jdbc_servlet_instagram.dto.Product;

public class ObjectUtil {
	
	public static Product createProductObject(int id,String name,String color,double price,LocalDate mfd,int quantity) {
		
		return new Product(id, name, color, price, mfd, quantity);
		
	}
	
	public static Product createProductObject(ResultSet set) throws SQLException {

		  return new Product(
		            set.getInt("id"),
		            set.getString("name"),
		            set.getString("color"),
		            set.getDouble("price"),
		            set.getDate("mfd").toLocalDate(),
		            set.getInt("quantity"));
	}

}
