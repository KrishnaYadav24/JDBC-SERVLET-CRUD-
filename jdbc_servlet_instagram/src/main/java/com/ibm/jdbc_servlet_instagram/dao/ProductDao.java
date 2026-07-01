package com.ibm.jdbc_servlet_instagram.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.ibm.jdbc_servlet_instagram.connection.ConnectionFactory;
import com.ibm.jdbc_servlet_instagram.dto.Product;
import com.ibm.jdbc_servlet_instagram.util.ConnectionClosedUtil;
import com.ibm.jdbc_servlet_instagram.util.ObjectUtil;

/**
 * @author asus this is java class as you know but you dont know about this so
 *         let me explain meet me somewhere in qspider
 * 
 */
public class ProductDao {

	final static String INSERTPRODUCTQUERY = "insert into product(id,name,color,price,mfd,quantity) values(?,?,?,?,?,?)";
	final static String DISPLAYALLPRODUCTQUERY = "select * from product";
	final static String SEARCHBYID = "select * from product where id = ?";
	final static String UPDATEPRODUCTNAME = "update product set name = ? where id = ?";
	final static String DELETEBYID = "delete from product where id = ?";

	Connection connection = null;
	PreparedStatement ps = null;
	ResultSet set = null;

	/**
	 * 
	 * @param product
	 * @return Product saavdhs cm sjgfsvjknmx.k vmh sahcvhd ,jvbn. assdvfjbnj,gf
	 *         shjavdfjbgnhkl
	 */
	public Product saveProductDao(Product product) {

		try {
			connection = ConnectionFactory.getConnectionFactory();

			ps = connection.prepareStatement(INSERTPRODUCTQUERY);

			ps.setInt(1, product.getId());
			ps.setString(2, product.getName());
			ps.setString(3, product.getColor());
			ps.setDouble(4, product.getPrice());
			ps.setObject(5, product.getMfd());
			ps.setInt(6, product.getQuantity());
			int a = ps.executeUpdate();
			if (a != 0) {
				System.out.println("data registered!!!!");
				return product;
			}
			System.out.println("something went wrong pls check your code");
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			ConnectionClosedUtil.connectionClosedUtil(ps, connection);
		}
	}

	public List<Product> getAllProductDao() {
		try {
			connection = ConnectionFactory.getConnectionFactory();
			ps = connection.prepareStatement(DISPLAYALLPRODUCTQUERY);
			ResultSet set = ps.executeQuery();

			connection.setAutoCommit(false);

			List<Product> products = new ArrayList<Product>();

			while (set.next()) {

				int id = set.getInt("id");
				String name = set.getString("name");
				String color = set.getString("color");
				double price = set.getDouble("price");
				LocalDate mfd = set.getDate("mfd").toLocalDate();
				int quantity = set.getInt("quantity");

				Product p = ObjectUtil.createProductObject(id, name, color, price, mfd, quantity);

				products.add(p);

			}

			return products;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			ConnectionClosedUtil.connectionClosedUtil(ps, connection);
		}
	}

	public void saveProductsInBatchDao(List<Product> products) {

		try {
			connection = ConnectionFactory.getConnectionFactory();

			connection.setAutoCommit(false);

			ps = connection.prepareStatement(INSERTPRODUCTQUERY);

			for (Product product : products) {

				ps.setInt(1, product.getId());
				ps.setString(2, product.getName());
				ps.setString(3, product.getColor());
				ps.setDouble(4, product.getPrice());
				ps.setObject(5, product.getMfd());
				ps.setInt(6, product.getQuantity());

				ps.addBatch();
			}

			int[] a = ps.executeBatch();

			if (a.length == products.size()) {

				connection.commit();
				System.out.println("All Products Inserted Successfully");

			} else {

				connection.rollback();
				System.out.println("Something Went Wrong");
			}

		} catch (SQLException e) {

			try {
				if (connection != null) {
					connection.rollback();
					System.out.println("Rollback Successfully");
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			e.printStackTrace();

		} finally {

			ConnectionClosedUtil.connectionClosedUtil(ps, connection);
		}
	}

	public boolean updatProductNameById(int id, String email) {
		try {
			connection = ConnectionFactory.getConnectionFactory();
			ps = connection.prepareStatement(UPDATEPRODUCTNAME);
			ps.setString(1, email);
			ps.setInt(2, id);

			int a = ps.executeUpdate();
			if (a != 0) {
				System.out.println("Product name updtaed successfully");
				return true;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			ConnectionClosedUtil.connectionClosedUtil(ps, connection);
		}

	}

	public boolean deleteById(int id) {
		try {
			connection = ConnectionFactory.getConnectionFactory();
			ps = connection.prepareStatement(DELETEBYID);

			ps.setInt(1, id);

			int a = ps.executeUpdate();

			if (a != 0) {
				System.out.println("Data has been deleted successfully");
				return true;
			}
			return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	public Product searchProduct(int id) {
		try {
			connection = ConnectionFactory.getConnectionFactory();
			ps = connection.prepareStatement(SEARCHBYID);

			ps.setInt(1, id);

			set = ps.executeQuery();

			if (set.next()) {
				return ObjectUtil.createProductObject(set);
			}
			return null;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}