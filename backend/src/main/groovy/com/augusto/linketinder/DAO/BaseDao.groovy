package com.augusto.linketinder.DAO

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

abstract class BaseDao {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/linketinder"
    private static final String DB_USER = "postgres"
    private static final String DB_PASSWORD = "postgres"

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)
    }

    protected void closeQuietly(AutoCloseable resource) {
        if (resource == null) {
            return
        }
        try {
            resource.close()
        } catch (Exception ignored) {
            // Ignorado de propósito
        }
    }
}
