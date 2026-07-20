package com.smartinventory.controller;

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

@WebServlet(urlPatterns = {"/products", "/products/new", "/products/edit", "/products/delete"})
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
            } else {
                showProducts(request, response);
            }
        } catch (SQLException ex) {
            request.setAttribute("error", "Database connection failed. Create the schema, start MySQL, and verify SMART_INVENTORY_DB_* settings.");
            request.setAttribute("products", java.util.Collections.emptyList());
            request.getRequestDispatcher("/WEB-INF/views/products.jsp").forward(request, response);
        } catch (RuntimeException ex) {
            throw new ServletException("Unable to process product request", ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Product product = readProduct(request);
            String id = request.getParameter("id");
            if (id == null || id.isBlank()) {
                productService.createProduct(product);
            } else {
                product.setId(Integer.parseInt(id));
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
        request.setAttribute("products", productService.getProducts());
        request.getRequestDispatcher("/WEB-INF/views/products.jsp").forward(request, response);
    }

    private Product readProduct(HttpServletRequest request) {
        Product product = new Product();
        String id = request.getParameter("id");
        if (id != null && !id.isBlank()) {
            product.setId(Integer.parseInt(id));
        }
        product.setName(request.getParameter("name"));
        product.setSku(request.getParameter("sku"));
        product.setCategory(request.getParameter("category"));
        product.setSupplier(request.getParameter("supplier"));
        product.setQuantity(parseInteger(request.getParameter("quantity")));
        product.setPrice(parseBigDecimal(request.getParameter("price")));
        return product;
    }

    private int parseInteger(String value) {
        return value == null || value.isBlank() ? 0 : Integer.parseInt(value);
    }

    private BigDecimal parseBigDecimal(String value) {
        return value == null || value.isBlank() ? BigDecimal.ZERO : new BigDecimal(value);
    }
}
