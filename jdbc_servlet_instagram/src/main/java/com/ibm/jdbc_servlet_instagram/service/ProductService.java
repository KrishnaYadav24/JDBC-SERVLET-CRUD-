
package com.ibm.jdbc_servlet_instagram.service;

import com.ibm.jdbc_servlet_instagram.dao.ProductDao;
import com.ibm.jdbc_servlet_instagram.dto.Product;

/**
 * 
 */
public class ProductService {
	
	ProductDao dao=new ProductDao();
	
	/**
	 * this method is called by controller layer and inside this method 
	 * we have to call ProductDao class method saveProductDao(Product product)
	 * @param product
	 * @return
	 */
	public Product saveProductService(Product product) {
		
		if(product!=null) {
			
			double price=product.getPrice();
			
			if(price>=3000&&price<=5000) {
				
				Product p=dao.saveProductDao(product);
				
				return p;
				
			}else {
				System.out.println("please pass valid product price it should be less than 5000 and greater than 3000");
				
				throw new RuntimeException("failed due to price");
			}
		}else {
			return null;
		}
	}
	
	public Product searchProduct(int id) {

		if (id > 0) {
			return dao.searchProduct(id);
		}

		System.out.println("Invalid Product Id...");
		return null;
	}
	
	
	public boolean updateProductNameById(int id, String productName) {

		if (id > 0 && productName != null && !productName.isEmpty()) {
			return dao.updatProductNameById(id, productName);
		}

		System.out.println("Invalid Input...");
		return false;
	}
	
	public boolean deleteById(int id) {

		if (id > 0) {
			return dao.deleteById(id);
		}

		System.out.println("Invalid Product Id...");
		return false;
	}	
}
