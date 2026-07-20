CREATE DATABASE IF NOT EXISTS smart_inventory;
USE smart_inventory;

CREATE TABLE IF NOT EXISTS products (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(120) NOT NULL,
    sku VARCHAR(60) NOT NULL UNIQUE,
    category VARCHAR(80) NOT NULL,
    quantity INT NOT NULL DEFAULT 0,
    reorder_level INT NOT NULL DEFAULT 5,
    price DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    supplier VARCHAR(120),
    location VARCHAR(120),
    status ENUM('ACTIVE', 'INACTIVE', 'DISCONTINUED') NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_products_category (category),
    INDEX idx_products_status (status),
    INDEX idx_products_low_stock (quantity, reorder_level)
);

INSERT INTO products (name, sku, category, quantity, reorder_level, price, supplier, location, status)
VALUES
('Wireless Barcode Scanner', 'INV-SCN-001', 'Electronics', 8, 5, 89.99, 'Apex Supply Co.', 'Aisle A-01', 'ACTIVE'),
('Thermal Label Roll', 'INV-LBL-100', 'Packaging', 2, 10, 12.50, 'PrintWorks', 'Aisle B-03', 'ACTIVE'),
('Inventory Tablet', 'INV-TAB-010', 'Electronics', 4, 3, 249.00, 'Northwind Devices', 'Secure Cage', 'ACTIVE')
ON DUPLICATE KEY UPDATE sku = VALUES(sku);
