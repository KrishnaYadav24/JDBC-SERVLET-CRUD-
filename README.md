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
