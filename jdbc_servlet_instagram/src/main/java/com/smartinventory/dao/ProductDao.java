package com.smartinventory.dao;

import com.smartinventory.config.DatabaseConfig;
import com.smartinventory.model.InventoryStats;
import com.smartinventory.model.Product;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDao {
    private static final String BASE_SELECT = "SELECT * FROM products";
    private static final String FIND_BY_ID = "SELECT * FROM products WHERE id = ?";
    private static final String INSERT = "INSERT INTO products(name, sku, category, quantity, reorder_level, price, supplier, location, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE products SET name = ?, sku = ?, category = ?, quantity = ?, reorder_level = ?, price = ?, supplier = ?, location = ?, status = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM products WHERE id = ?";
    private static final String STATS = "SELECT COUNT(*) total_products, COALESCE(SUM(CASE WHEN quantity <= reorder_level THEN 1 ELSE 0 END), 0) low_stock_products, COALESCE(SUM(quantity), 0) total_quantity, COALESCE(SUM(quantity * price), 0) total_value FROM products WHERE status = 'ACTIVE'";

    public List<Product> findAll(String search, String status, boolean lowStockOnly) throws SQLException {
        List<Object> parameters = new ArrayList<>();
        StringBuilder sql = new StringBuilder(BASE_SELECT).append(" WHERE 1 = 1");
        if (search != null && !search.isBlank()) {
            sql.append(" AND (LOWER(name) LIKE ? OR LOWER(sku) LIKE ? OR LOWER(category) LIKE ? OR LOWER(supplier) LIKE ? OR LOWER(location) LIKE ?)");
            String term = "%" + search.toLowerCase() + "%";
            for (int i = 0; i < 5; i++) {
                parameters.add(term);
            }
        }
        if (status != null && !status.isBlank() && !"ALL".equalsIgnoreCase(status)) {
            sql.append(" AND status = ?");
            parameters.add(status.toUpperCase());
        }
        if (lowStockOnly) {
            sql.append(" AND quantity <= reorder_level");
        }
        sql.append(" ORDER BY CASE WHEN quantity <= reorder_level THEN 0 ELSE 1 END, updated_at DESC, id DESC");

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql.toString())) {
            bindParameters(statement, parameters);
            try (ResultSet resultSet = statement.executeQuery()) {
                List<Product> products = new ArrayList<>();
                while (resultSet.next()) {
                    products.add(mapRow(resultSet));
                }
                return products;
            }
        }
    }

    public Optional<Product> findById(int id) throws SQLException {
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? Optional.of(mapRow(resultSet)) : Optional.empty();
            }
        }
    }

    public InventoryStats getStats() throws SQLException {
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(STATS);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return new InventoryStats(
                        resultSet.getInt("total_products"),
                        resultSet.getInt("low_stock_products"),
                        resultSet.getInt("total_quantity"),
                        resultSet.getBigDecimal("total_value"));
            }
            return new InventoryStats(0, 0, 0, BigDecimal.ZERO);
        }
    }

    public void save(Product product) throws SQLException {
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT)) {
            fillStatement(statement, product);
            statement.executeUpdate();
        }
    }

    public boolean update(Product product) throws SQLException {
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            fillStatement(statement, product);
            statement.setInt(10, product.getId());
            return statement.executeUpdate() > 0;
        }
    }

    public boolean delete(int id) throws SQLException {
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        }
    }

    private void bindParameters(PreparedStatement statement, List<Object> parameters) throws SQLException {
        for (int i = 0; i < parameters.size(); i++) {
            statement.setObject(i + 1, parameters.get(i));
        }
    }

    private void fillStatement(PreparedStatement statement, Product product) throws SQLException {
        statement.setString(1, product.getName());
        statement.setString(2, product.getSku());
        statement.setString(3, product.getCategory());
        statement.setInt(4, product.getQuantity());
        statement.setInt(5, product.getReorderLevel());
        statement.setBigDecimal(6, product.getPrice());
        statement.setString(7, product.getSupplier());
        statement.setString(8, product.getLocation());
        statement.setString(9, product.getStatus());
    }

    private Product mapRow(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getInt("id"));
        product.setName(resultSet.getString("name"));
        product.setSku(resultSet.getString("sku"));
        product.setCategory(resultSet.getString("category"));
        product.setQuantity(resultSet.getInt("quantity"));
        product.setReorderLevel(resultSet.getInt("reorder_level"));
        product.setPrice(resultSet.getBigDecimal("price"));
        product.setSupplier(resultSet.getString("supplier"));
        product.setLocation(resultSet.getString("location"));
        product.setStatus(resultSet.getString("status"));
        product.setCreatedAt(toLocalDateTime(resultSet.getTimestamp("created_at")));
        product.setUpdatedAt(toLocalDateTime(resultSet.getTimestamp("updated_at")));
        return product;
    }

    private java.time.LocalDateTime toLocalDateTime(Timestamp timestamp) {
        return timestamp == null ? null : timestamp.toLocalDateTime();
    }
}
