package com.smartinventory.service;

import com.smartinventory.dao.ProductDao;
import com.smartinventory.model.Product;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ProductService {
    private final ProductDao productDao = new ProductDao();

    public List<Product> getProducts() throws SQLException {
        return productDao.findAll();
    }

    public Optional<Product> getProduct(int id) throws SQLException {
        return productDao.findById(id);
    }

    public void createProduct(Product product) throws SQLException {
        validate(product);
        productDao.save(product);
    }

    public boolean updateProduct(Product product) throws SQLException {
        validate(product);
        return productDao.update(product);
    }

    public boolean deleteProduct(int id) throws SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("Product id must be greater than zero.");
        }
        return productDao.delete(id);
    }

    private void validate(Product product) {
        if (isBlank(product.getName()) || isBlank(product.getSku()) || isBlank(product.getCategory())) {
            throw new IllegalArgumentException("Name, SKU and category are required.");
        }
        if (product.getQuantity() < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
        if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
