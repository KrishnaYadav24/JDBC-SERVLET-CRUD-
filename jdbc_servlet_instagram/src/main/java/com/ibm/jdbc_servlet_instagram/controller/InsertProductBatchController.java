package com.ibm.jdbc_servlet_instagram.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.ibm.jdbc_servlet_instagram.dao.ProductDao;
import com.ibm.jdbc_servlet_instagram.dto.Product;
import com.ibm.jdbc_servlet_instagram.util.ObjectUtil;

public class InsertProductBatchController {

	public static void main(String[] args) {
		
		Product product1=ObjectUtil.createProductObject(300000000, "data-cable", "black", 3000,LocalDate.parse("2024-09-13") , 10);
		
		Product product2=ObjectUtil.createProductObject(100000000, "cold-drink", "see-green", 3910,LocalDate.parse("2024-09-13") , 10);
		
		Product product3=ObjectUtil.createProductObject(80000, "laptop", "white", 5000,LocalDate.parse("2024-09-13") , 10);
		
		Product product4=ObjectUtil.createProductObject(400000, "aloo-chips", "cyan", 3000,LocalDate.parse("2024-09-13") , 10);
		
		
		List<Product> products = new ArrayList<Product>();
		
		products.add(product1);
		products.add(product2);
		products.add(product3);
		products.add(product4);
		
		ProductDao productDao = new ProductDao();
		
		productDao.saveProductsInBatchDao(products);
	}
}
