package com.ibm.jdbc_servlet_instagram.controller;

import java.util.Scanner;

import com.ibm.jdbc_servlet_instagram.service.ProductService;

public class UpdateProductNameController {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		System.out.println("Enter Product Id : ");
		int id = sc.nextInt();

		System.out.println("Enter New Product Name : ");
		String name = sc.nextLine();

		ProductService service = new ProductService();
		service.updateProductNameById(id, name);

		sc.close();
	}
}