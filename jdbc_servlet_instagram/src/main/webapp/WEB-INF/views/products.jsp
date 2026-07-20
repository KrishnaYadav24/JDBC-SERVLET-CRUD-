<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.smartinventory.model.InventoryStats" %>
<%@ page import="com.smartinventory.model.Product" %>
<%
    List<Product> products = (List<Product>) request.getAttribute("products");
    InventoryStats stats = (InventoryStats) request.getAttribute("stats");
    String search = (String) request.getAttribute("search");
    String status = (String) request.getAttribute("status");
    boolean lowStock = Boolean.TRUE.equals(request.getAttribute("lowStock"));
    if (stats == null) { stats = new InventoryStats(0, 0, 0, java.math.BigDecimal.ZERO); }
    if (search == null) { search = ""; }
    if (status == null) { status = "ALL"; }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Smart Inventory Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
</head>
<body>
<main class="container">
    <header class="hero">
        <div>
            <p class="eyebrow">Real-time stock control</p>
            <h1>Smart Inventory Dashboard</h1>
            <p>Monitor product value, low-stock alerts, warehouse locations, suppliers and lifecycle status in one place.</p>
        </div>
        <div class="hero-actions">
            <a class="button secondary" href="${pageContext.request.contextPath}/">Home</a>
            <a class="button" href="${pageContext.request.contextPath}/products/new">Add Product</a>
        </div>
    </header>

    <section class="stats-grid">
        <article class="stat-card"><span>Total Products</span><strong><%= stats.getTotalProducts() %></strong></article>
        <article class="stat-card warning"><span>Low Stock Alerts</span><strong><%= stats.getLowStockProducts() %></strong></article>
        <article class="stat-card"><span>Total Units</span><strong><%= stats.getTotalQuantity() %></strong></article>
        <article class="stat-card"><span>Inventory Value</span><strong>$<%= stats.getTotalValue() %></strong></article>
    </section>

    <section class="card toolbar">
        <form method="get" action="${pageContext.request.contextPath}/products" class="filters">
            <label>Search
                <input name="search" placeholder="Name, SKU, supplier, location" value="<%= search %>">
            </label>
            <label>Status
                <select name="status">
                    <option value="ALL" <%= "ALL".equals(status) ? "selected" : "" %>>All</option>
                    <option value="ACTIVE" <%= "ACTIVE".equals(status) ? "selected" : "" %>>Active</option>
                    <option value="INACTIVE" <%= "INACTIVE".equals(status) ? "selected" : "" %>>Inactive</option>
                    <option value="DISCONTINUED" <%= "DISCONTINUED".equals(status) ? "selected" : "" %>>Discontinued</option>
                </select>
            </label>
            <label class="check"><input type="checkbox" name="lowStock" value="true" <%= lowStock ? "checked" : "" %>> Low stock only</label>
            <button type="submit">Apply Filters</button>
            <a href="${pageContext.request.contextPath}/products">Reset</a>
            <a class="button secondary" href="${pageContext.request.contextPath}/products/export?search=<%= search %>&status=<%= status %>&lowStock=<%= lowStock %>">Export CSV</a>
        </form>
    </section>

    <section class="card">
        <% if (request.getAttribute("error") != null) { %>
            <p class="error"><%= request.getAttribute("error") %></p>
        <% } %>
        <table>
            <thead><tr><th>Health</th><th>Name</th><th>SKU</th><th>Category</th><th>Qty</th><th>Reorder</th><th>Value</th><th>Supplier</th><th>Location</th><th>Status</th><th>Actions</th></tr></thead>
            <tbody>
            <% if (products == null || products.isEmpty()) { %>
                <tr><td colspan="11" class="empty">No products match your filters. Add a product or reset filters.</td></tr>
            <% } else { for (Product product : products) { %>
                <tr class="<%= product.isLowStock() ? "low-row" : "" %>">
                    <td><span class="badge <%= product.isLowStock() ? "danger-badge" : "success-badge" %>"><%= product.isLowStock() ? "Reorder" : "Healthy" %></span></td>
                    <td><strong><%= product.getName() %></strong></td>
                    <td><%= product.getSku() %></td>
                    <td><%= product.getCategory() %></td>
                    <td><%= product.getQuantity() %></td>
                    <td><%= product.getReorderLevel() %></td>
                    <td>$<%= product.getInventoryValue() %></td>
                    <td><%= product.getSupplier() == null ? "" : product.getSupplier() %></td>
                    <td><%= product.getLocation() == null ? "" : product.getLocation() %></td>
                    <td><span class="badge"><%= product.getStatus() %></span></td>
                    <td class="actions">
                        <a href="${pageContext.request.contextPath}/products/edit?id=<%= product.getId() %>">Edit</a>
                        <a class="danger" href="${pageContext.request.contextPath}/products/delete?id=<%= product.getId() %>" onclick="return confirm('Delete this product?')">Delete</a>
                    </td>
                </tr>
            <% }} %>
            </tbody>
        </table>
    </section>
</main>
</body>
</html>
