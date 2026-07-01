package com.ibm.jdbc_servlet_instagram.controller;

import java.time.LocalDate;

import com.ibm.jdbc_servlet_instagram.dto.Product;
import com.ibm.jdbc_servlet_instagram.service.ProductService;
import com.ibm.jdbc_servlet_instagram.util.ObjectUtil;

public class InsertProductController {

	public static void main(String[] args) {
		
		Product product=ObjectUtil.createProductObject(909090, "mobile-phone", "white", 5000,LocalDate.parse("2024-09-13") , 10);
		
		ProductService productService = new ProductService();
		
		Product p=productService.saveProductService(product);
		
		String msg=p!=null?"data stored":"kuch to problem hai";
		
		System.out.println(msg);
	}
}
