package com.ibm.jdbc_servlet_instagram.controller;

import java.util.Scanner;

import com.ibm.jdbc_servlet_instagram.dto.Product;
import com.ibm.jdbc_servlet_instagram.service.ProductService;

public class SearchProductController {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		System.out.println("Enter Product Id : ");
		int id = sc.nextInt();

		ProductService service = new ProductService();

		Product product = service.searchProduct(id);

		if (product != null) {
			System.out.println(product);
		} else {
			System.out.println("Product Not Found...");
		}

		sc.close();
	}
}