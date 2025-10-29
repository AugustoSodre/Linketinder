package com.augusto.linketinder.service;

import com.augusto.linketinder.model.Competencia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class ValidateServiceTest {

    private ValidateService validateService;

    @BeforeEach
    void setUp() {
        validateService = new ValidateService();
    }

    @Test
    void isEmailValid() {
        assertFalse(validateService.isEmailValid(""));
        assertFalse(validateService.isEmailValid("@"));
        assertTrue(validateService.isEmailValid("jose12@gmail.com"));
    }

    @Test
    void isEstadoValid() {
        assertFalse(validateService.isEstadoValid(""));
        assertFalse(validateService.isEstadoValid("@"));
        assertFalse(validateService.isEstadoValid("XX"));
        assertFalse(validateService.isEstadoValid("bA"));
        assertFalse(validateService.isEstadoValid("Ro"));
        assertFalse(validateService.isEstadoValid("am"));
        assertTrue(validateService.isEstadoValid("DF"));
    }

    @Test
    void isCEPValid() {
        assertFalse(validateService.isCEPValid(""));
        assertFalse(validateService.isCEPValid("123"));
        assertFalse(validateService.isCEPValid("abc"));
        assertFalse(validateService.isCEPValid("abc123"));
        assertTrue(validateService.isCEPValid("70670444"));
        assertTrue(validateService.isCEPValid("70670-444"));
    }

    @Test
    void isListCompetenciaValid() {
        assertFalse(validateService.isListCompetenciaValid(null));
        assertFalse(validateService.isListCompetenciaValid(Arrays.asList(new Competencia(1, "Java"), null)));
        assertTrue(validateService.isListCompetenciaValid(Collections.emptyList()));
        assertTrue(validateService.isListCompetenciaValid(Arrays.asList(new Competencia(1, "Java"), new Competencia(2, "Groovy"))));
    }

    @Test
    void isCompetenciaOpcaoValida() {
        assertFalse(validateService.isCompetenciaOpcaoValida(0, 3));
        assertFalse(validateService.isCompetenciaOpcaoValida(4, 3));
        assertTrue(validateService.isCompetenciaOpcaoValida(1, 3));
        assertTrue(validateService.isCompetenciaOpcaoValida(3, 3));
    }

    @Test
    void isCompetenciaSelecionavel() {
        Competencia java = new Competencia(1, "Java");
        Competencia groovy = new Competencia(2, "Groovy");

        assertFalse(validateService.isCompetenciaSelecionavel(null, java));
        assertFalse(validateService.isCompetenciaSelecionavel(Collections.singletonList(java), null));
        assertFalse(validateService.isCompetenciaSelecionavel(Collections.singletonList(java), java));
        assertTrue(validateService.isCompetenciaSelecionavel(Collections.singletonList(java), groovy));
    }

    @Test
    void isCPFValid() {
        assertFalse(validateService.isCPFValid(""));
        assertFalse(validateService.isCPFValid("123"));
        assertFalse(validateService.isCPFValid("abc"));
        assertFalse(validateService.isCPFValid("abc123"));
        assertTrue(validateService.isCPFValid("11122233344"));
        assertTrue(validateService.isCPFValid("111.222.333-44"));
        assertTrue(validateService.isCPFValid("111222.333-44"));
        assertTrue(validateService.isCPFValid("111.222333-44"));
        assertTrue(validateService.isCPFValid("111.222.33344"));
    }

    @Test
    void isCNPJValid() {
        assertFalse(validateService.isCNPJValid(""));
        assertFalse(validateService.isCNPJValid("123"));
        assertFalse(validateService.isCNPJValid("abc"));
        assertFalse(validateService.isCNPJValid("abc123"));
        assertTrue(validateService.isCNPJValid("85918854000104"));
        assertTrue(validateService.isCNPJValid("85.918.854/0001-04"));
        assertTrue(validateService.isCNPJValid("85918.854/0001-04"));
        assertTrue(validateService.isCNPJValid("85.918854/0001-04"));
        assertTrue(validateService.isCNPJValid("85.918.8540001-04"));
        assertTrue(validateService.isCNPJValid("85.918.854/000104"));

    }

    @Test
    void isPaisValid() {
        assertFalse(validateService.isPaisValid(""));
        assertFalse(validateService.isPaisValid("123"));
        assertTrue(validateService.isPaisValid("Canada"));
        assertTrue(validateService.isPaisValid("CA"));
    }

    @Test
    void isIdadeValid() {
        assertFalse(validateService.isIdadeValid(-1));
        assertFalse(validateService.isIdadeValid(123));
        assertTrue(validateService.isIdadeValid(21));
        assertTrue(validateService.isIdadeValid(32));
    }

    @Test
    void isNomeValid() {
        assertFalse(validateService.isPaisValid(""));
        assertTrue(validateService.isPaisValid("Babidi"));
        assertTrue(validateService.isPaisValid("aLaN"));
        assertTrue(validateService.isPaisValid("LAra"));
    }

    @Test
    void isDescricaoValid() {
        assertFalse(validateService.isPaisValid(""));
        assertTrue(validateService.isPaisValid("Babidi"));
        assertTrue(validateService.isPaisValid("aLaN"));
        assertTrue(validateService.isPaisValid("LAra"));
    }

    @Test
    void isSenhaValid() {
        assertFalse(validateService.isPaisValid(""));
        assertTrue(validateService.isPaisValid("Babidi"));
        assertTrue(validateService.isPaisValid("aLaN"));
        assertTrue(validateService.isPaisValid("LAra"));
    }




}