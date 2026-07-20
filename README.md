# Smart Inventory

A complete Maven WAR project for a Smart Inventory CRUD application built with JDBC, Jakarta Servlets, JSP and MySQL.

## Project

The runnable application lives in `jdbc_servlet_instagram` and builds to `smart-inventory.war`.

## Database setup

1. Create the schema with `jdbc_servlet_instagram/src/main/resources/database.sql`.
2. Configure connection details with environment variables if the defaults do not match your MySQL installation:
   - `SMART_INVENTORY_DB_URL` (default: `jdbc:mysql://localhost:3306/smart_inventory`)
   - `SMART_INVENTORY_DB_USER` (default: `root`)
   - `SMART_INVENTORY_DB_PASSWORD` (default: `password`)

## Build

```bash
cd jdbc_servlet_instagram
mvn clean package
```

Deploy `target/smart-inventory.war` to a Jakarta EE 10 compatible servlet container such as Tomcat 10.1+.

## How to see the project in the browser

This repository is a Maven web application, so opening the JSP files directly from the file explorer will not run it. Build and deploy it to a servlet container:

1. Install and start MySQL.
2. Run the SQL in `jdbc_servlet_instagram/src/main/resources/database.sql`.
3. Build the WAR:
   ```bash
   cd jdbc_servlet_instagram
   mvn clean package
   ```
4. Copy `target/smart-inventory.war` into the `webapps` folder of Tomcat 10.1 or newer.
5. Start Tomcat and open `http://localhost:8080/smart-inventory/`.

If you see the landing page but the product dashboard shows a database warning, the web app is deployed correctly and only the MySQL connection settings need to be fixed.
