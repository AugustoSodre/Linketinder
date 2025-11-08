package com.augusto.linketinder.dao.connectionProvider

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class JDBCConnectionProvider implements ConnectionProvider {

    private final String DB_URL
    private final String DB_USER
    private final String DB_PASSWORD

    JDBCConnectionProvider() {
        this(System.getenv('JDBC_URL') ?: "jdbc:postgresql://localhost:5432/linketinder",
                System.getenv('JDBC_USER') ?: "postgres",
                System.getenv('JDBC_PASSWORD') ?: "postgres")
    }

    JDBCConnectionProvider(String db_url, String db_user, String db_password) {
        this.DB_URL = db_url
        this.DB_USER = db_user
        this.DB_PASSWORD = db_password
    }

    @Override
    Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)
    }
}
