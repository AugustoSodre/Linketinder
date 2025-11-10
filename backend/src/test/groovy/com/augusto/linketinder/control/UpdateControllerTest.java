package com.augusto.linketinder.control;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UpdateControllerTest {

    private CandidatoController candidatoController;
    private EmpresaController empresaController;
    private VagaController vagaController;

    @BeforeEach
    void setUp() {
        candidatoController = new CandidatoController();
        empresaController = new EmpresaController();
        vagaController = new VagaController();
    }

    @Test
    void camposCandidatoShouldExposeExpectedMappings() {
        Map<String, String> campos = candidatoController.camposCandidato();

        assertEquals("nome", campos.get("nome"));
        assertEquals("descricao", campos.get("descrição"));
        assertEquals("senha", campos.get("senha"));
        assertEquals(8, campos.size());
    }

    @Test
    void camposEmpresaShouldExposeExpectedMappings() {
        Map<String, String> campos = empresaController.camposEmpresa();

        assertEquals("nome", campos.get("nome"));
        assertEquals("pais", campos.get("país"));
        assertEquals("cnpj", campos.get("CNPJ"));
        assertEquals(8, campos.size());
    }

    @Test
    void camposVagaShouldContainAllExpectedEntries() {
        Map<String, String> campos = vagaController.camposVaga();

        assertEquals("id_empresa", campos.get("ID da empresa"));
        assertEquals("nome", campos.get("nome"));
        assertEquals("descricao", campos.get("descrição"));
        assertTrue(campos.containsKey("estado"));
        assertEquals(5, campos.size());
    }
}
