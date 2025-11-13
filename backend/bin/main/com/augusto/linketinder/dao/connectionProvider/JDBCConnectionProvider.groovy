package com.augusto.linketinder.dao.connectionProvider

import groovy.transform.CompileStatic

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

@CompileStatic
class JDBCConnectionProvider implements ConnectionProvider {

    private static final String DEFAULT_URL = "jdbc:postgresql://localhost:5432/linketinder"
    private static final String DEFAULT_USER = "postgres"
    private static final String DEFAULT_PASSWORD = "postgres"

    private final String dbUrl
    private final String dbUser
    private final String dbPassword

    static {
        Class.forName("org.postgresql.Driver")
    }

    JDBCConnectionProvider() {
        this(
            System.getenv('JDBC_URL') ?: DEFAULT_URL,
            System.getenv('JDBC_USER') ?: DEFAULT_USER,
            System.getenv('JDBC_PASSWORD') ?: DEFAULT_PASSWORD
        )
    }

    JDBCConnectionProvider(String dbUrl, String dbUser, String dbPassword) {
        this.dbUrl = dbUrl
        this.dbUser = dbUser
        this.dbPassword = dbPassword
    }

    @Override
    Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUser, dbPassword)
    }
}
