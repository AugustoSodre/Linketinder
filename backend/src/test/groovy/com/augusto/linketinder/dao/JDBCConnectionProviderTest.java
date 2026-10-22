package com.augusto.linketinder.dao;

import com.augusto.linketinder.dao.connectionProvider.ConnectionProvider;
import com.augusto.linketinder.dao.connectionProvider.ConnectionProviderFactory;
import com.augusto.linketinder.dao.connectionProvider.H2ConnectionProvider;
import com.augusto.linketinder.dao.connectionProvider.JDBCConnectionProvider;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class JDBCConnectionProviderTest {

    @Test
    void shouldOpenConnectionsWithCustomCredentials() throws Exception {
        JDBCConnectionProvider provider = new JDBCConnectionProvider(
                "jdbc:h2:mem:datasourceTestdb;MODE=PostgreSQL;DATABASE_TO_UPPER=false;DB_CLOSE_DELAY=-1",
                "sa",
                ""
        );

        try (Connection connection = provider.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT 1")) {

            assertTrue(resultSet.next());
            assertEquals(1, resultSet.getInt(1));
        }
    }
    @Test
    void dataSourceFactoryDefaultReturnsJDBCProvider() {
        // A propriedade vence a variável de ambiente, então o teste passa mesmo com DB_PROVIDER=h2 exportado.
        // Só confere o tipo: abrir conexão exigiria um PostgreSQL rodando.
        System.setProperty("DB_PROVIDER", "default");
        try {
            ConnectionProvider provider = ConnectionProviderFactory.getProvider();
            assertNotNull(provider);
            assertTrue(provider instanceof JDBCConnectionProvider);
        } finally {
            System.clearProperty("DB_PROVIDER");
        }
    }
    @Test
    void dataSourceFactorySelectsH2WhenRequested() throws Exception {
        // set system property (ConnectionProviderFactory checks system property first)
        System.setProperty("DB_PROVIDER", "h2");
        try {
            ConnectionProvider provider = ConnectionProviderFactory.getProvider();
            assertNotNull(provider);
            // The provider should be H2 for this test
            assertTrue(provider instanceof H2ConnectionProvider);
            try (Connection c = provider.getConnection();
                 Statement s = c.createStatement();
                 ResultSet rs = s.executeQuery("SELECT 1")) {
                assertTrue(rs.next());
                assertEquals(1, rs.getInt(1));
            }
        } finally {
            System.clearProperty("DB_PROVIDER");
        }
    }
}
