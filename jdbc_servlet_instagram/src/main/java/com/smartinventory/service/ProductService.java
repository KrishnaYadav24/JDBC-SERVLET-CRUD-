package com.smartinventory.service;

import com.smartinventory.dao.ProductDao;
import com.smartinventory.model.InventoryStats;
import com.smartinventory.model.Product;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ProductService {
    private static final Set<String> VALID_STATUSES = Set.of("ACTIVE", "INACTIVE", "DISCONTINUED");
    private final ProductDao productDao = new ProductDao();

    public List<Product> getProducts(String search, String status, boolean lowStockOnly) throws SQLException {
        return productDao.findAll(search, status, lowStockOnly);
    }

    public InventoryStats getStats() throws SQLException {
        return productDao.getStats();
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
        product.setName(requireText(product.getName(), "Name"));
        product.setSku(requireText(product.getSku(), "SKU").toUpperCase());
        product.setCategory(requireText(product.getCategory(), "Category"));
        product.setStatus(normalizeStatus(product.getStatus()));
        if (product.getQuantity() < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
        if (product.getReorderLevel() < 0) {
            throw new IllegalArgumentException("Reorder level cannot be negative.");
        }
        if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
    }

    private String requireText(String value, String field) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(field + " is required.");
        }
        return value.trim();
    }

    private String normalizeStatus(String status) {
        String normalized = status == null || status.isBlank() ? "ACTIVE" : status.trim().toUpperCase();
        if (!VALID_STATUSES.contains(normalized)) {
            throw new IllegalArgumentException("Status must be ACTIVE, INACTIVE or DISCONTINUED.");
        }
        return normalized;
    }
}
