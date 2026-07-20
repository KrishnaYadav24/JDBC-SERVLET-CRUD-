package com.smartinventory.dao;

import com.smartinventory.config.DatabaseConfig;
import com.smartinventory.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDao {
    private static final String FIND_ALL = "SELECT * FROM products ORDER BY id DESC";
    private static final String FIND_BY_ID = "SELECT * FROM products WHERE id = ?";
    private static final String INSERT = "INSERT INTO products(name, sku, category, quantity, price, supplier) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE products SET name = ?, sku = ?, category = ?, quantity = ?, price = ?, supplier = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM products WHERE id = ?";

    public List<Product> findAll() throws SQLException {
        List<Product> products = new ArrayList<>();
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                products.add(mapRow(resultSet));
            }
        }
        return products;
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
            statement.setInt(7, product.getId());
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

    private void fillStatement(PreparedStatement statement, Product product) throws SQLException {
        statement.setString(1, product.getName());
        statement.setString(2, product.getSku());
        statement.setString(3, product.getCategory());
        statement.setInt(4, product.getQuantity());
        statement.setBigDecimal(5, product.getPrice());
        statement.setString(6, product.getSupplier());
    }

    private Product mapRow(ResultSet resultSet) throws SQLException {
        Timestamp createdAt = resultSet.getTimestamp("created_at");
        return new Product(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("sku"),
                resultSet.getString("category"),
                resultSet.getInt("quantity"),
                resultSet.getBigDecimal("price"),
                resultSet.getString("supplier"),
                createdAt == null ? null : createdAt.toLocalDateTime());
    }
}
