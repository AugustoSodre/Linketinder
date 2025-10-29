package com.augusto.linketinder.service;

import com.augusto.linketinder.model.Competencia;
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

    @Test
    void testInvalidThenValidGetEmailInput() {
        String invalidEstado = "blabla";
        String validEstado = "blabla@gmail.com";
        String input = invalidEstado + "\n" + validEstado + "\n";
        mockBufferedReader(input);
        assertEquals(validEstado, inputService.getEmailInput());
    }

    @ParameterizedTest
    @ValueSource(strings = {"AC", "ba", "dF", "Sp"})
    void testValidGetEstadoInput(String input) {
        mockBufferedReader(input + "\n");
        String result = inputService.getEstadoInput();
        assertEquals(input.toUpperCase(), result);
    }

    @Test
    void testInvalidThenValidGetEstadoInput() {
        String invalidEstado = "PL";
        String validEstado = "BA";
        String input = invalidEstado + "\n" + validEstado + "\n";
        mockBufferedReader(input);
        assertEquals(validEstado, inputService.getEstadoInput());
    }

    @ParameterizedTest
    @ValueSource(strings = {"77777-777", "12345678"})
    void testValidGetCepInput(String input) {
        mockBufferedReader(input + "\n");
        String result = inputService.getCepInput();
        assertEquals(input, result);
    }

    @Test
    void testInvalidThenValidGetCepInput() {
        String invalidCEP = "123";
        String validCEP = "66666-444";
        String input = invalidCEP + "\n" + validCEP + "\n";
        mockBufferedReader(input);
        assertEquals(validCEP, inputService.getCepInput());
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
    void testGetCompetenciasInputWithNullList() {
        assertThrows(IllegalArgumentException.class, () -> inputService.getCompetenciasInput(null));
    }

    @Test
    void testGetCompetenciasInputWithNullEntry() {
        List<Competencia> competencias = Arrays.asList(new Competencia(1, "Java"), null);

        assertThrows(IllegalArgumentException.class, () -> inputService.getCompetenciasInput(competencias));
    }

    @Test
    void testGetCompetenciasInputInvalidSelectionThenValid() {
        List<Competencia> competencias = Arrays.asList(
                new Competencia(1, "Java"),
                new Competencia(2, "Groovy")
        );

        mockBufferedReader("5\n1\n0\n");

        List<Competencia> result = inputService.getCompetenciasInput(competencias);

        assertEquals(1, result.size());
        assertEquals(competencias.get(0), result.get(0));
    }

    @ParameterizedTest
    @ValueSource(strings = {"123.456.789-10", "00011122233"})
    void testValidGetCpfInput(String input) {
        mockBufferedReader(input + "\n");
        String result = inputService.getCpfInput();
        assertEquals(input.trim(), result);
    }

    @Test
    void testInvalidThenValidGetCpfInput() {
        String invalidCPF = "123";
        String validCPF = "123.456.789-10";
        String input = invalidCPF + "\n" + validCPF + "\n";
        mockBufferedReader(input);
        assertEquals(validCPF, inputService.getCpfInput());
    }

    @ParameterizedTest
    @ValueSource(strings = {"12345678912345", "66.872.236/0001-26"})
    void testValidGetCnpjInput(String input) {
        mockBufferedReader(input + "\n");
        String result = inputService.getCnpjInput();
        assertEquals(input.trim(), result);
    }

    @Test
    void testInvalidThenValidGetCnpjInput() {
        String invalidCNPJ = "1234567.89/12345";
        String validCNPJ = "66.872.236/0001-26";
        String input = invalidCNPJ + "\n" + validCNPJ + "\n";
        mockBufferedReader(input);
        assertEquals(validCNPJ, inputService.getCnpjInput());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Brasil", "BR"})
    void testGetPaisInput(String input) {
        mockBufferedReader(input + "\n");
        String result = inputService.getPaisInput();
        assertEquals(input.trim(), result);
    }

    @Test
    void testGetIdadeInput() {
        mockBufferedReader("25\n");
        int idade = inputService.getIdadeInput();
        assertEquals(25, idade);
    }
}