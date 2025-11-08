package com.augusto.linketinder.dao.connectionProvider

class ConnectionProviderFactory {
    static ConnectionProvider getProvider() {
        String provider = System.getProperty('DB_PROVIDER') ?: System.getenv('DB_PROVIDER') ?: 'default'
        switch (provider) {
            case 'h2':
                return new H2ConnectionProvider()

            default:
                return new JDBCConnectionProvider()
        }
    }
}
