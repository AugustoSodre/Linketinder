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
        assertTrue(validateService.isEstadoValid("DF"));
        assertTrue(validateService.isEstadoValid("bA"));
        assertTrue(validateService.isEstadoValid("Ro"));
    }

    @Test
    void isCEPValid() {
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