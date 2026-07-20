<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
            <p class="eyebrow">Smart Inventory</p>
            <h1>Your inventory project is ready to view</h1>
            <p>This landing page loads without a database connection. Use it to confirm the WAR is deployed, then open the products dashboard after MySQL is configured.</p>
        </div>
        <a class="button" href="${pageContext.request.contextPath}/products">Open Dashboard</a>
    </header>

    <section class="grid">
        <article class="card">
            <h2>1. Build</h2>
            <p>Run <code>mvn clean package</code> inside <code>jdbc_servlet_instagram</code>.</p>
        </article>
        <article class="card">
            <h2>2. Deploy</h2>
            <p>Copy <code>target/smart-inventory.war</code> to Tomcat 10.1+ and start the server.</p>
        </article>
        <article class="card">
            <h2>3. Visit</h2>
            <p>Open <code>http://localhost:8080/smart-inventory/</code> in your browser.</p>
        </article>
    </section>
</main>
</body>
</html>
