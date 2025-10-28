package com.augusto.linketinder.control;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UpdateControllerTest {

    private UpdateController controller;

    @BeforeEach
	//Arrange
    void setUp() {
        controller = new UpdateController();
    }

    @Test
    void camposCandidatoShouldExposeExpectedMappings() {
        // Act
        Map<String, String> campos = controller.camposCandidato();

        // Assert
        assertEquals("nome", campos.get("nome"));
        assertEquals("descricao", campos.get("descrição"));
        assertEquals("senha", campos.get("senha"));
        assertEquals(8, campos.size());
    }

    @Test
    void camposEmpresaShouldExposeExpectedMappings() {
        // Act
        Map<String, String> campos = controller.camposEmpresa();

        // Assert
        assertEquals("nome", campos.get("nome"));
        assertEquals("pais", campos.get("país"));
        assertEquals("cnpj", campos.get("CNPJ"));
        assertEquals(8, campos.size());
    }

    @Test
    void camposVagaShouldContainAllExpectedEntries() {
        // Act
        Map<String, String> campos = controller.camposVaga();

        // Assert
        assertEquals("id_empresa", campos.get("ID da empresa"));
        assertEquals("nome", campos.get("nome"));
        assertEquals("descricao", campos.get("descrição"));
        assertTrue(campos.containsKey("estado"));
        assertEquals(5, campos.size());
    }
}
