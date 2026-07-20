<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.smartinventory.model.Product" %>
<%
    Product product = (Product) request.getAttribute("product");
    if (product == null) { product = new Product(); }
    boolean editing = product.getId() > 0;
    String status = product.getStatus() == null ? "ACTIVE" : product.getStatus();
%>
<!DOCTYPE html>
<html>
<head>
    <title><%= editing ? "Edit" : "Add" %> Inventory Product</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
</head>
<body>
<main class="container narrow">
    <section class="card">
        <p class="eyebrow">Product master data</p>
        <h1><%= editing ? "Edit" : "Add" %> Product</h1>
        <% if (request.getAttribute("error") != null) { %><p class="error"><%= request.getAttribute("error") %></p><% } %>
        <form method="post" action="${pageContext.request.contextPath}/products" class="product-form">
            <% if (editing) { %><input type="hidden" name="id" value="<%= product.getId() %>"><% } %>
            <label>Name<input name="name" value="<%= product.getName() == null ? "" : product.getName() %>" required></label>
            <label>SKU<input name="sku" value="<%= product.getSku() == null ? "" : product.getSku() %>" required></label>
            <label>Category<input name="category" value="<%= product.getCategory() == null ? "" : product.getCategory() %>" required></label>
            <label>Quantity<input type="number" min="0" name="quantity" value="<%= product.getQuantity() %>" required></label>
            <label>Reorder Level<input type="number" min="0" name="reorderLevel" value="<%= product.getReorderLevel() %>" required></label>
            <label>Price<input type="number" min="0" step="0.01" name="price" value="<%= product.getPrice() == null ? "0.00" : product.getPrice() %>" required></label>
            <label>Supplier<input name="supplier" value="<%= product.getSupplier() == null ? "" : product.getSupplier() %>"></label>
            <label>Warehouse Location<input name="location" value="<%= product.getLocation() == null ? "" : product.getLocation() %>"></label>
            <label>Status
                <select name="status">
                    <option value="ACTIVE" <%= "ACTIVE".equals(status) ? "selected" : "" %>>Active</option>
                    <option value="INACTIVE" <%= "INACTIVE".equals(status) ? "selected" : "" %>>Inactive</option>
                    <option value="DISCONTINUED" <%= "DISCONTINUED".equals(status) ? "selected" : "" %>>Discontinued</option>
                </select>
            </label>
            <div class="form-actions"><a href="${pageContext.request.contextPath}/products">Cancel</a><button class="button" type="submit">Save Product</button></div>
        </form>
    </section>
</main>
</body>
</html>
