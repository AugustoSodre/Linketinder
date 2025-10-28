package com.augusto.linketinder.control;

import com.augusto.linketinder.model.Competencia;
import com.augusto.linketinder.service.InputService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InputServiceTest {

    private InputService inputService;

    @BeforeEach
    void setUp() {
        inputService = new InputService();
    }

    private void mockBufferedReader(String input) {
        inputService.setBr(new BufferedReader(new StringReader(input)));
    }

    @Test
    void testEmptyGetStringInput() {
        mockBufferedReader(("o\n").repeat(6));
        assertThrows(RuntimeException.class, () -> inputService.getEstadoInput());
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
    void testRegularGetStringInput(String input) {
        mockBufferedReader(input + "\n");
        String result = inputService.getStringInput();
        assertFalse(result.isEmpty());
        assertEquals(input, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Augusto",
            "João",
            "Maria",
            "Calos Almeida",
            "Lucas Gonçalves",
            "Jose12Gameplays",
            "12345"
    })
    void testValidGetNomeInput(String input) {
        mockBufferedReader(input + "\n");
        String result = inputService.getNomeInput();
        assertEquals(input.trim(), result);
    }

    @Test
    void testValidGetEmailInput() {
        mockBufferedReader("jose@hotmail.com\n");
        String result = inputService.getEmailInput();
        assertEquals("jose@hotmail.com", result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"AC", "ba", "dF", "Sp"})
    void testValidGetEstadoInput(String input) {
        mockBufferedReader(input + "\n");
        String result = inputService.getEstadoInput();
        assertEquals(input.toUpperCase(), result);
    }

    @Test
    void testInvalidGetEstadoInput() {
        mockBufferedReader(("o\n").repeat(6));
        assertThrows(RuntimeException.class, () -> inputService.getEstadoInput());
    }

    @ParameterizedTest
    @ValueSource(strings = {"77777-777", "12345678"})
    void testValidGetCepInput(String input) {
        mockBufferedReader(input + "\n");
        String result = inputService.getCepInput();
        assertEquals(input, result);
    }

    @Test
    void testInvalidGetCepInput() {
        mockBufferedReader(("o\n").repeat(6));
        assertThrows(RuntimeException.class, () -> inputService.getCepInput());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Estudante", "Grande Estudante"})
    void testValidGetDescricaoInput(String input) {
        mockBufferedReader(input + "\n");
        String result = inputService.getDescricaoInput();
        assertEquals(input.trim(), result);
    }

    @Test
    void testGetCompetenciasInputWithValidSelections() {
        List<Competencia> competencias = Arrays.asList(
                new Competencia(1, "Java"),
                new Competencia(2, "Groovy"),
                new Competencia(3, "SQL")
        );

        mockBufferedReader("1\n2\n0\n");

        List<Competencia> result = inputService.getCompetenciasInput(competencias);

        assertEquals(2, result.size());
        assertEquals(Arrays.asList(competencias.get(0), competencias.get(1)), result);
    }

    @Test
    void testGetCompetenciasInputExitImmediately() {
        List<Competencia> competencias = Arrays.asList(
                new Competencia(1, "Java"),
                new Competencia(2, "Groovy")
        );

        mockBufferedReader("0\n");

        List<Competencia> result = inputService.getCompetenciasInput(competencias);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetCompetenciasInputDuplicateSelection() {
        List<Competencia> competencias = Arrays.asList(
                new Competencia(1, "Java"),
                new Competencia(2, "Groovy")
        );

        mockBufferedReader("1\n1\n0\n");

        List<Competencia> result = inputService.getCompetenciasInput(competencias);

        assertEquals(1, result.size());
        assertEquals(competencias.get(0), result.get(0));
    }

    @Test
    void testGetCompetenciasInputExceedsMaxTentativas() {
        List<Competencia> competencias = Arrays.asList(
                new Competencia(1, "Java"),
                new Competencia(2, "Groovy")
        );

        mockBufferedReader("5\n5\n5\n5\n5\n");

        assertThrows(RuntimeException.class, () -> inputService.getCompetenciasInput(competencias));
    }

    @ParameterizedTest
    @ValueSource(strings = {"123.456.789-10", "00011122233"})
    void testValidGetCpfInput(String input) {
        mockBufferedReader(input + "\n");
        String result = inputService.getCpfInput();
        assertEquals(input.trim(), result);
    }

    @Test
    void testInvalidGetCpfInput() {
        mockBufferedReader(("o\n").repeat(6));
        assertThrows(RuntimeException.class, () -> inputService.getCpfInput());
    }

    @ParameterizedTest
    @ValueSource(strings = {"12345678912345", "66.872.236/0001-26"})
    void testValidGetCnpjInput(String input) {
        mockBufferedReader(input + "\n");
        String result = inputService.getCnpjInput();
        assertEquals(input.trim(), result);
    }

    @Test
    void testInvalidGetCnpjInput() {
        mockBufferedReader(("1234567.89/12345\n").repeat(6));
        assertThrows(RuntimeException.class, () -> inputService.getCnpjInput());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Brasil", "BR"})
    void testGetPaisInput(String input) {
        mockBufferedReader(input + "\n");
        String result = inputService.getPaisInput();
        assertEquals(input.trim(), result);
    }

    @Test
    void testGetIntInput() {
        mockBufferedReader("notanumber\n42\n");
        int result = inputService.getIntInput();
        assertEquals(42, result);
    }

    @Test
    void testGetIdadeInput() {
        mockBufferedReader("25\n");
        int idade = inputService.getIdadeInput();
        assertEquals(25, idade);
    }
}