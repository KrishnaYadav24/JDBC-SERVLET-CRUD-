package com.ibm.jdbc_servlet_instagram.controller;

import java.util.Scanner;

import com.ibm.jdbc_servlet_instagram.service.ProductService;

public class DeleteProductController {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		System.out.println("Enter Product Id : ");
		int id = sc.nextInt();

		ProductService service = new ProductService();

		boolean result = service.deleteById(id);

		if (result) {
			System.out.println("Product Deleted Successfully...");
		} else {
			System.out.println("Product Not Found...");
		}

		sc.close();
	}
}