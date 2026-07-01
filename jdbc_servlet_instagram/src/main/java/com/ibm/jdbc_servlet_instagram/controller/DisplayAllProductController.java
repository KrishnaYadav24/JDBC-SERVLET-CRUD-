package com.ibm.jdbc_servlet_instagram.controller;

import java.util.List;

import com.ibm.jdbc_servlet_instagram.dao.ProductDao;
import com.ibm.jdbc_servlet_instagram.dto.Product;

public class DisplayAllProductController {

	public static void main(String[] args) {
		ProductDao dao = new ProductDao();
		
		List<Product> products=dao.getAllProductDao();
		
		if(!(products.isEmpty())&&products!=null) {
			
			
			 for (Product product : products) { System.out.println(product); }
			 
			
			//lambda expression
			/*
			 * products.forEach(a->{ System.out.println(a); });
			 */
			
			//method reference/			products.forEach(System.out::println);
			
		}else {
			System.out.println("data is not vaialable or something went wrong");
		}
	}
}
