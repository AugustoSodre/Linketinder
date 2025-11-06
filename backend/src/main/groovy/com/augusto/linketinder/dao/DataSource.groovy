package com.augusto.linketinder.dao

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class DataSource{
    private static final instance = new DataSource()

    private String DB_URL = "jdbc:postgresql://localhost:5432/linketinder"
    private String DB_USER = "postgres"
    private String DB_PASSWORD = "postgres"

    DataSource(String db_url, String db_user, String db_password) {
        this.DB_URL = db_url
        this.DB_USER = db_user
        this.DB_PASSWORD = db_password
    }

    DataSource() {}

    static DataSource getInstance(){
        return instance
    }

    Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)
    }

    String getDB_URL() {
        return DB_URL
    }

    void setDB_URL(String DB_URL) {
        this.DB_URL = DB_URL
    }

    String getDB_USER() {
        return DB_USER
    }

    void setDB_USER(String DB_USER) {
        this.DB_USER = DB_USER
    }

    String getDB_PASSWORD() {
        return DB_PASSWORD
    }

    void setDB_PASSWORD(String DB_PASSWORD) {
        this.DB_PASSWORD = DB_PASSWORD
    }
}
