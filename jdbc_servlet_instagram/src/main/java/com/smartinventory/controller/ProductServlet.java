package com.smartinventory.controller;

import com.smartinventory.model.InventoryStats;
import com.smartinventory.model.Product;
import com.smartinventory.service.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@WebServlet(urlPatterns = {"/products", "/products/new", "/products/edit", "/products/delete", "/products/export"})
public class ProductServlet extends HttpServlet {
    private final ProductService productService = new ProductService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String path = request.getServletPath();
            if ("/products/new".equals(path)) {
                request.setAttribute("product", new Product());
                request.getRequestDispatcher("/WEB-INF/views/product-form.jsp").forward(request, response);
            } else if ("/products/edit".equals(path)) {
                int id = Integer.parseInt(request.getParameter("id"));
                request.setAttribute("product", productService.getProduct(id).orElseThrow(() -> new IllegalArgumentException("Product not found.")));
                request.getRequestDispatcher("/WEB-INF/views/product-form.jsp").forward(request, response);
            } else if ("/products/delete".equals(path)) {
                productService.deleteProduct(Integer.parseInt(request.getParameter("id")));
                response.sendRedirect(request.getContextPath() + "/products");
            } else if ("/products/export".equals(path)) {
                exportProducts(request, response);
            } else {
                showProducts(request, response);
            }
        } catch (SQLException ex) {
            showDatabaseWarning(request, response);
        } catch (RuntimeException ex) {
            throw new ServletException("Unable to process product request", ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Product product = readProduct(request);
            if (product.getId() == 0) {
                productService.createProduct(product);
            } else {
                productService.updateProduct(product);
            }
            response.sendRedirect(request.getContextPath() + "/products");
        } catch (SQLException | RuntimeException ex) {
            request.setAttribute("error", ex.getMessage());
            request.setAttribute("product", readProduct(request));
            request.getRequestDispatcher("/WEB-INF/views/product-form.jsp").forward(request, response);
        }
    }

    private void showProducts(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String search = request.getParameter("search");
        String status = request.getParameter("status");
        boolean lowStockOnly = "true".equalsIgnoreCase(request.getParameter("lowStock"));
        request.setAttribute("products", productService.getProducts(search, status, lowStockOnly));
        request.setAttribute("stats", productService.getStats());
        request.setAttribute("search", search == null ? "" : search);
        request.setAttribute("status", status == null ? "ALL" : status);
        request.setAttribute("lowStock", lowStockOnly);
        request.getRequestDispatcher("/WEB-INF/views/products.jsp").forward(request, response);
    }

    private void exportProducts(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String search = request.getParameter("search");
        String status = request.getParameter("status");
        boolean lowStockOnly = "true".equalsIgnoreCase(request.getParameter("lowStock"));
        List<Product> products = productService.getProducts(search, status, lowStockOnly);
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=smart-inventory-products.csv");
        response.getWriter().println("ID,Name,SKU,Category,Quantity,Reorder Level,Price,Supplier,Location,Status,Inventory Value");
        for (Product product : products) {
            response.getWriter().printf("%d,%s,%s,%s,%d,%d,%s,%s,%s,%s,%s%n",
                    product.getId(), csv(product.getName()), csv(product.getSku()), csv(product.getCategory()),
                    product.getQuantity(), product.getReorderLevel(), product.getPrice(), csv(product.getSupplier()),
                    csv(product.getLocation()), product.getStatus(), product.getInventoryValue());
        }
    }

    private void showDatabaseWarning(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("error", "Database connection failed. Create the schema, start MySQL, and verify SMART_INVENTORY_DB_* settings.");
        request.setAttribute("products", Collections.emptyList());
        request.setAttribute("stats", new InventoryStats(0, 0, 0, BigDecimal.ZERO));
        request.setAttribute("search", "");
        request.setAttribute("status", "ALL");
        request.setAttribute("lowStock", false);
        request.getRequestDispatcher("/WEB-INF/views/products.jsp").forward(request, response);
    }

    private Product readProduct(HttpServletRequest request) {
        Product product = new Product();
        product.setId(parseInteger(request.getParameter("id")));
        product.setName(request.getParameter("name"));
        product.setSku(request.getParameter("sku"));
        product.setCategory(request.getParameter("category"));
        product.setSupplier(request.getParameter("supplier"));
        product.setLocation(request.getParameter("location"));
        product.setStatus(request.getParameter("status"));
        product.setQuantity(parseInteger(request.getParameter("quantity")));
        product.setReorderLevel(parseInteger(request.getParameter("reorderLevel")));
        product.setPrice(parseBigDecimal(request.getParameter("price")));
        return product;
    }

    private int parseInteger(String value) {
        return value == null || value.isBlank() ? 0 : Integer.parseInt(value);
    }

    private BigDecimal parseBigDecimal(String value) {
        return value == null || value.isBlank() ? BigDecimal.ZERO : new BigDecimal(value);
    }

    private String csv(String value) {
        if (value == null) {
            return "";
        }
        return '"' + value.replace("\"", "\"\"") + '"';
    }
}
