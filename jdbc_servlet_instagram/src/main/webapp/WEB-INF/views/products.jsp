<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.smartinventory.model.Product" %>
<!DOCTYPE html>
<html>
<head>
    <title>Smart Inventory</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
</head>
<body>
<main class="container">
    <header class="hero">
        <div>
            <p class="eyebrow">JDBC • Servlet • JSP</p>
            <h1>Smart Inventory</h1>
            <p>Track products, stock levels, suppliers and prices from a clean inventory dashboard.</p>
        </div>
        <a class="button" href="${pageContext.request.contextPath}/products/new">Add Product</a>
    </header>
    <section class="card">
        <% if (request.getAttribute("error") != null) { %>
            <p class="error"><%= request.getAttribute("error") %></p>
        <% } %>
        <table>
            <thead><tr><th>ID</th><th>Name</th><th>SKU</th><th>Category</th><th>Qty</th><th>Price</th><th>Supplier</th><th>Actions</th></tr></thead>
            <tbody>
            <%
                List<Product> products = (List<Product>) request.getAttribute("products");
                if (products == null || products.isEmpty()) {
            %>
                <tr><td colspan="8" class="empty">No products yet. Add your first inventory item.</td></tr>
            <% } else { for (Product product : products) { %>
                <tr>
                    <td><%= product.getId() %></td>
                    <td><%= product.getName() %></td>
                    <td><%= product.getSku() %></td>
                    <td><%= product.getCategory() %></td>
                    <td><%= product.getQuantity() %></td>
                    <td>$<%= product.getPrice() %></td>
                    <td><%= product.getSupplier() == null ? "" : product.getSupplier() %></td>
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
