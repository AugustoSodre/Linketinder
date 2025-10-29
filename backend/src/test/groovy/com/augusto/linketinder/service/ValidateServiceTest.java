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