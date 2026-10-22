package com.augusto.linketinder.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class ValidateServiceTest {

    private ValidateService validateService;

    @BeforeEach
    void setUp() {
        validateService = new ValidateService();
    }

    @Test
    void validarCamposShouldReportMissingAndMalformedFields() {
        java.util.Map<String, Object> valido = new java.util.HashMap<>(java.util.Map.of(
                "nome", "Jim", "email", "jim@office.com", "estado", "SP", "cep", "12345678",
                "idade", 30, "cpf", "12345678901", "descricao", "d", "senha", "123456"));
        java.util.List<String> obrigatorios = java.util.List.copyOf(valido.keySet());

        assertTrue(validateService.validarCampos(valido, obrigatorios).isEmpty());

        java.util.Map<String, Object> invalido = new java.util.HashMap<>(valido);
        invalido.put("cpf", "123.456.789-01");
        invalido.put("senha", "123");
        invalido.remove("nome");
        assertEquals(3, validateService.validarCampos(invalido, obrigatorios).size());

        assertTrue(validateService.validarCampos(java.util.Map.of("cep", "12345678"), java.util.List.of()).isEmpty());
        assertEquals(1, validateService.validarCampos(java.util.Map.of("idade", "abc"), java.util.List.of()).size());
    }

    @Test
    void erroDoCampoSegueAsRegrasCompartilhadasComOFrontend() throws Exception {
        java.util.Map<String, java.util.Map<String, Object>> regras = new com.fasterxml.jackson.databind.ObjectMapper()
                .readValue(java.nio.file.Path.of("../shared/regras.json").toFile(), java.util.Map.class);

        assertFalse(regras.isEmpty());
        regras.forEach((campo, regra) -> assertEquals(regra.get("mensagem"), validateService.erroDoCampo(campo, null), campo));

        assertNull(validateService.erroDoCampo("email", "jim.halpert@dunder-mifflin.com"));
        assertNotNull(validateService.erroDoCampo("estado", "XX"));
        assertNull(validateService.erroDoCampo("idade", 120));
        assertNotNull(validateService.erroDoCampo("idade", 121));
    }
}
