package com.jspider.jdbc_servlet_crud_with_mysql.User;

import com.jspider.jdbc_servlet_crud_with_mysql.update.GmailUpdate;
import com.jspider.jdbc_servlet_crud_with_mysql.controller.InsertEmployeeController;
import com.jspider.jdbc_servlet_crud_with_mysql.controller.DisplayEmployeeController;
import com.jspider.jdbc_servlet_crud_with_mysql.delete.DeleteById;

import java.util.Scanner;

public class User {
	public static void main(String[] args) {
		System.out.println(
				"Press 1 to Insert the data .....\n\nPress 2 to Display the Table .....\n\nPress 3 to Update the Gmail the row .....\n\nPress 4 to delete the row .....\n\n");
		enterChoice();

	}
	static Scanner sc = new Scanner(System.in);
	public static void enterChoice() {
		
		System.out.println("Enter your choice :");
		int choice = sc.nextInt();

		if (choice == 1) {
			
			InsertEmployeeController.insertEmployee();
				System.out.print("Do you want to continue (Y/N)");
				char a  = sc.next().charAt(0);
				if(a=='Y' || a == 'y') {
					enterChoice();
				}
				else {
					System.out.println("Exit............");
					return;	
				}
				
		} else if (choice == 2) {
			
			DisplayEmployeeController.displayEmployee();
			System.out.print("Do you want to continue (Y/N)");
			char a  = sc.next().charAt(0);
			if(a=='Y' || a == 'y') {
				enterChoice();
			}
			else {
				System.out.println("Exit............");
				return;	
			}

		} else if (choice == 3) {
			GmailUpdate.update();
			System.out.print("Do you want to continue (Y/N)");
			char a  = sc.next().charAt(0);
			if(a=='Y' || a == 'y') {
				enterChoice();
			}
			else {
				System.out.println("Exit............");
				return;	
			}

		} else if (choice == 4) {
			DeleteById.deleteId();
			System.out.print("Do you want to continue (Y/N)");
			char a  = sc.next().charAt(0);
			if(a=='Y' || a == 'y') {
				enterChoice();
			}
			else {
				System.out.println("Exit............");
				return;	
			}

		} else {
			System.out.println("You have entered  wrong choice .......\n please enter correct option and try again");
			enterChoice();
		}
		sc.close();
	}
}
