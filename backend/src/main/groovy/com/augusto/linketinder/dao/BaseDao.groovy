package com.augusto.linketinder.dao

import java.sql.Connection
import java.sql.SQLException
import java.util.Objects

abstract class BaseDao {

    private final DataSource dataSource

    BaseDao() {
        this(new DataSource())
    }

    BaseDao(DataSource dataSource) {
        if(dataSource != null){
            this.dataSource = dataSource
        } else{
            throw new IllegalArgumentException("DataSource vazio!")
        }

    }

    protected Connection getConnection() throws SQLException {
        return dataSource.getConnection()
    }

    protected DataSource getDataSource() {
        return dataSource
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
