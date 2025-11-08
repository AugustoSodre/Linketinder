package com.augusto.linketinder.dao

import com.augusto.linketinder.dao.connectionProvider.ConnectionProvider
import com.augusto.linketinder.dao.connectionProvider.ConnectionProviderFactory

import java.sql.Connection
import java.sql.SQLException

abstract class BaseDao {

    private final ConnectionProvider connectionProvider

    BaseDao() {
        this(ConnectionProviderFactory.getProvider())
    }

    BaseDao(ConnectionProvider connectionProvider) {
        if (connectionProvider != null) {
            this.connectionProvider = connectionProvider
        } else {
            throw new IllegalArgumentException("ConnectionProvider vazio!")
        }
    }

    protected Connection getConnection() throws SQLException {
        return connectionProvider.getConnection()
    }

    protected ConnectionProvider getConnectionProvider() {
        return connectionProvider
    }

    protected void close(AutoCloseable resource) {
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
