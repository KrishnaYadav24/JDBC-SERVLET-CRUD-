package com.smartinventory.model;

import java.math.BigDecimal;

public class InventoryStats {
    private final int totalProducts;
    private final int lowStockProducts;
    private final int totalQuantity;
    private final BigDecimal totalValue;

    public InventoryStats(int totalProducts, int lowStockProducts, int totalQuantity, BigDecimal totalValue) {
        this.totalProducts = totalProducts;
        this.lowStockProducts = lowStockProducts;
        this.totalQuantity = totalQuantity;
        this.totalValue = totalValue;
    }

    public int getTotalProducts() { return totalProducts; }
    public int getLowStockProducts() { return lowStockProducts; }
    public int getTotalQuantity() { return totalQuantity; }
    public BigDecimal getTotalValue() { return totalValue; }
}
