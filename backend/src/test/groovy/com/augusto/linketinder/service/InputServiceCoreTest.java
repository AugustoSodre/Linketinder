package com.augusto.linketinder.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.BufferedReader;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

class InputServiceCoreTest {

    private ValidateService validateService;
    private InputServiceCore inputServiceCore;

    @BeforeEach
    void setUp() {
        validateService = new ValidateService();
    }

    private void mockBufferedReader(String input) {
        inputServiceCore = new InputServiceCore(new BufferedReader(new StringReader(input)), validateService);
    }

    @Test
    void testEmptyGetStringInput() {
        mockBufferedReader("");
        assertThrows(RuntimeException.class, () -> inputServiceCore.getStringInput());
    }

    @Test
    void testInvalidThenValidGetStringInput() {
        mockBufferedReader("\nCapital\n");
        assertEquals("Capital", inputServiceCore.getStringInput());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Capital",
            "UPPERCASE",
            "lowercase",
            "cAmEl",
            "Dot.",
            "One Compound word",
            ",",
            "?",
            "{"
    })
    void testValidGetStringInput(String input) {
        mockBufferedReader(input + "\n");
        String result = inputServiceCore.getStringInput();
        assertFalse(result.isEmpty());
        assertEquals(input, result);
    }

    @Test
    void testEmptyGetIntInput() {
        mockBufferedReader("");
        assertThrows(RuntimeException.class, () -> inputServiceCore.getIntInput());
    }

    @Test
    void testInvalidThenValidGetIntInput() {
        mockBufferedReader("notanumber\n42\n");
        assertEquals(42, inputServiceCore.getIntInput());
    }

    @Test
    void testValidGetIntInput() {
        mockBufferedReader("42\n");
        assertEquals(42, inputServiceCore.getIntInput());
    }

    @Test
    void testConstructorWithNullBufferedReader() {
        assertThrows(IllegalArgumentException.class, () -> new InputServiceCore(null, new ValidateService()));
    }

    @Test
    void testConstructorWithNullValidateService() {
        assertThrows(IllegalArgumentException.class, () -> new InputServiceCore(new BufferedReader(new StringReader("")), null));
    }
}