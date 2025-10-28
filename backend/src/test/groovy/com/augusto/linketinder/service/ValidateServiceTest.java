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
    }

    @Test
    void isCNPJValid() {
    }

    @Test
    void isPaisValid() {
    }

    @Test
    void isIdadeValid() {
    }
}