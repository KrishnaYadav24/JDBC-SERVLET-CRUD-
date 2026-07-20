<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Smart Inventory</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
</head>
<body>
<main class="container">
    <header class="hero splash">
        <div>
            <p class="eyebrow">Smart Inventory System</p>
            <h1>Control stock, value, suppliers and reorder alerts</h1>
            <p>A feature-rich JDBC + Servlet + JSP inventory system with dashboard metrics, search, filters, CSV export, product lifecycle status and low-stock intelligence.</p>
        </div>
        <a class="button" href="${pageContext.request.contextPath}/products">Launch Dashboard</a>
    </header>

    <section class="stats-grid">
        <article class="stat-card"><span>Dashboard</span><strong>KPIs</strong><p>Total products, stock units, low-stock count and total inventory value.</p></article>
        <article class="stat-card warning"><span>Alerts</span><strong>Low Stock</strong><p>Reorder badges highlight products at or below their reorder level.</p></article>
        <article class="stat-card"><span>Operations</span><strong>CRUD + CSV</strong><p>Create, edit, delete, filter, search and export inventory records.</p></article>
    </section>

    <section class="card">
        <h2>How to view this project</h2>
        <ol class="steps">
            <li>Run the SQL file in MySQL: <code>src/main/resources/database.sql</code>.</li>
            <li>Build the web archive: <code>mvn clean package</code>.</li>
            <li>Deploy <code>target/smart-inventory.war</code> to Tomcat 10.1+.</li>
            <li>Open <code>http://localhost:8080/smart-inventory/</code>.</li>
        </ol>
    </section>
</main>
</body>
</html>
