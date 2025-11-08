package com.augusto.linketinder.dao.connectionProvider

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class H2ConnectionProvider implements ConnectionProvider {

    private final String DB_URL
    private final String DB_USER
    private final String DB_PASSWORD

    H2ConnectionProvider() {
        this(System.getenv('H2_JDBC_URL') ?: 'jdbc:h2:mem:linketinder;MODE=PostgreSQL;DATABASE_TO_UPPER=false;DB_CLOSE_DELAY=-1',
                System.getenv('H2_JDBC_USER') ?: 'sa',
                System.getenv('H2_JDBC_PASSWORD') ?: '')
    }

    H2ConnectionProvider(String DB_URL, String DB_USER, String DB_PASSWORD) {
        this.DB_URL = DB_URL
        this.DB_USER = DB_USER
        this.DB_PASSWORD = DB_PASSWORD
    }

    @Override
    Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)
    }
}
