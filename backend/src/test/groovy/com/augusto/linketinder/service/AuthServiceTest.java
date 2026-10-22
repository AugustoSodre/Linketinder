package com.augusto.linketinder.service;

import com.augusto.linketinder.API.CandidatoControllerAPI;
import com.augusto.linketinder.dao.CandidatoDAO;
import com.augusto.linketinder.dao.EmpresaDAO;
import com.augusto.linketinder.dao.VagaDAO;
import com.augusto.linketinder.dao.connectionProvider.ConnectionProvider;
import com.augusto.linketinder.dao.connectionProvider.H2ConnectionProvider;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    private AuthService authService;

    @BeforeEach
    void setUp() throws SQLException {
        ConnectionProvider dataSource = new H2ConnectionProvider(
                "jdbc:h2:mem:auth;MODE=PostgreSQL;DATABASE_TO_UPPER=false;DB_CLOSE_DELAY=-1", "sa", "");

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS candidato");
            statement.execute("DROP TABLE IF EXISTS empresa");
            statement.execute("CREATE TABLE candidato (id SERIAL PRIMARY KEY, cpf VARCHAR(11), senha VARCHAR(255))");
            statement.execute("CREATE TABLE empresa (id SERIAL PRIMARY KEY, cnpj VARCHAR(14), senha VARCHAR(255))");
            statement.execute("INSERT INTO candidato (cpf, senha) VALUES ('12345678901', '" + AuthService.hash("123456") + "')");
            statement.execute("INSERT INTO empresa (cnpj, senha) VALUES ('12345678000101', '" + AuthService.hash("123456") + "')");
            statement.execute("INSERT INTO empresa (cnpj, senha) VALUES ('22222222000122', '" + AuthService.hash("123456") + "')");
        }

        authService = new AuthService(new CandidatoDAO(dataSource), new EmpresaDAO(dataSource));
    }

    @Test
    void hashShouldVerifyOnlyTheRightPassword() {
        String hash = AuthService.hash("123456");

        assertTrue(AuthService.verify("123456", hash));
        assertFalse(AuthService.verify("654321", hash));
        assertFalse(AuthService.verify("123456", "lixo"));
        assertNotEquals(hash, AuthService.hash("123456"), "cada hash deve ter um salt diferente");
    }

    @Test
    void loginShouldReturnTokenForValidCandidatoAndEmpresa() {
        Map<?, ?> candidato = authService.login("12345678901", null, "123456");
        Map<?, ?> empresa = authService.login(null, "12345678000101", "123456");

        assertNotNull(candidato.get("token"));
        assertEquals("candidato", candidato.get("tipo"));
        assertEquals("empresa", empresa.get("tipo"));
    }

    @Test
    void loginShouldFailForWrongPasswordOrUnknownUser() {
        assertNull(authService.login("12345678901", null, "errada"));
        assertNull(authService.login("00000000000", null, "123456"));
        assertNull(authService.login(null, null, "123456"));
    }

    @Test
    void loginShouldBlockAfterTooManyWrongPasswords() {
        for (int i = 0; i < AuthService.MAX_FALHAS; i++) {
            assertNull(authService.login(null, "22222222000122", "errada"));
        }
        assertEquals(Boolean.TRUE, authService.login(null, "22222222000122", "123456").get("bloqueado"));
        assertNotNull(authService.login("12345678901", null, "123456").get("token"));
    }

    @Test
    void updateShouldRejectUnknownColumn() {
        CandidatoService service = new CandidatoService((CandidatoDAO) null);

        assertThrows(IllegalArgumentException.class,
                () -> service.updateCandidato(1, "senha = 'x', nome", "x"));
        assertThrows(IllegalArgumentException.class,
                () -> new VagaService((VagaDAO) null).updateVaga(1, "nome = 'x', descricao", "x"));
    }

    @Test
    void checkAccessShouldOnlyAllowTheOwner() {
        Map<?, ?> sessao = authService.login("12345678901", null, "123456");
        int id = (Integer) sessao.get("id");
        Headers headers = new Headers();
        headers.add("Authorization", "Bearer " + sessao.get("token"));
        HttpExchange exchange = mock(HttpExchange.class);
        when(exchange.getRequestHeaders()).thenReturn(headers);

        assertEquals(0, AuthService.checkAccess(exchange, "candidato", id));
        assertEquals(403, AuthService.checkAccess(exchange, "candidato", id + 1));
        assertEquals(403, AuthService.checkAccess(exchange, "empresa", null));

        HttpExchange semToken = mock(HttpExchange.class);
        when(semToken.getRequestHeaders()).thenReturn(new Headers());
        assertEquals(401, AuthService.checkAccess(semToken, "candidato", id));
    }

    @Test
    void expiredTokenShouldBeRejected() {
        long validadeOriginal = AuthService.getValidadeTokenMs();
        AuthService.setValidadeTokenMs(-1);
        try {
            Map<?, ?> sessao = authService.login("12345678901", null, "123456");
            Headers headers = new Headers();
            headers.add("Authorization", "Bearer " + sessao.get("token"));
            HttpExchange exchange = mock(HttpExchange.class);
            when(exchange.getRequestHeaders()).thenReturn(headers);

            assertNull(AuthService.getSession(exchange));
        } finally {
            AuthService.setValidadeTokenMs(validadeOriginal);
        }
    }

    @Test
    void deleteWithoutTokenShouldReturn401() throws Exception {
        HttpExchange exchange = mock(HttpExchange.class);
        when(exchange.getRequestMethod()).thenReturn("DELETE");
        when(exchange.getRequestURI()).thenReturn(new URI("/candidatos/1"));
        when(exchange.getRequestHeaders()).thenReturn(new Headers());
        when(exchange.getResponseHeaders()).thenReturn(new Headers());
        when(exchange.getResponseBody()).thenReturn(new ByteArrayOutputStream());

        new CandidatoControllerAPI().handle(exchange);

        verify(exchange).sendResponseHeaders(eq(401), anyLong());
    }
}
