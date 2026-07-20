package com.smartinventory.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConfig {
    private static final String URL = System.getenv().getOrDefault("SMART_INVENTORY_DB_URL", "jdbc:mysql://localhost:3306/smart_inventory");
    private static final String USER = System.getenv().getOrDefault("SMART_INVENTORY_DB_USER", "root");
    private static final String PASSWORD = System.getenv().getOrDefault("SMART_INVENTORY_DB_PASSWORD", "password");

    private DatabaseConfig() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
