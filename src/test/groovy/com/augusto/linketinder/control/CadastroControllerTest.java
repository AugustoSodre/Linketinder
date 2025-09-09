package com.augusto.linketinder.control;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class CadastroControllerTest {

    CadastroController cadastroController;

    public CadastroController getCadastroController() {
        return cadastroController;
    }

    void mockBufferedReader(String input){
        getCadastroController().setBr(new BufferedReader(new StringReader(input)));
    }

    @BeforeEach
    void setUp() {
        cadastroController = new CadastroController();
    }


    // --- Testes para getStringInput()
    @ParameterizedTest
    @ValueSource(strings = {
            "\n",
            " \n",
             " \n "
    })
    void testEmptyGetStringInput(String input) {
        //Arrange
        mockBufferedReader(input);

        //Act
        String result = getCadastroController().getStringInput();

        //Assert
        assertTrue(result.isEmpty());
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
        //Arrange
        mockBufferedReader(input + "\n");

        //Act
        String result = getCadastroController().getStringInput();

        //Assert
        assertTrue(!result.isEmpty() && result.equals(input));
    }


    // --- Testes para getNomeInput()
    @ParameterizedTest
    @ValueSource(strings = {
            "Augusto",
            "João",
            "Maria",
            "Calos Almeida",
            "Lucas Gonçalves",
            "Jose12Gameplays",
            "12345",
            " ",
            "",
            "\n"
    })

    void testGetNomeInput(String input) {
        //Arrange
        mockBufferedReader(input + "\n");

        //Act
        String result = getCadastroController().getNomeInput();

        //Assert
        assertEquals(input.trim(), result);
    }


    // --- Testes para getEmailInput()
    @Test
    void testValidGetEmailInput() {
        //Arrange
        mockBufferedReader("jose@hotmail.com");

        //Act
        String result = getCadastroController().getEmailInput();

        //Assert
        assertEquals("jose@hotmail.com", result);
    }


    // --- Testes para getEstadoInput()
    @ParameterizedTest
    @ValueSource(strings = {
            "AC",
            "ba",
            "dF",
            "Sp"
})
    void testGetValidEstadoInput(String input) {
        //Arrange
        mockBufferedReader(input + "\n");

        //Act
        String result = getCadastroController().getNomeInput();

        //Assert
        assertEquals(input, result);
    }

    @Test
    void testInvalidEstadoInput() {
        //Arrange
        String input = ("o\n").repeat(6);
        mockBufferedReader(input);

        //Act + Assert
        assertThrows(RuntimeException.class, () -> {
            cadastroController.getEstadoInput();
        });
    }

    @Test
    void getCepInput() {
    }

    @Test
    void getDescricaoInput() {
    }

    @Test
    void getCompetenciasInput() {
    }

    @Test
    void getCpfInput() {
    }

    @Test
    void getCnpjInput() {
    }

    @Test
    void getPaisInput() {
    }

    //--- Testing int number inputs
    @Test
    void testGetIntInput() {
        // invalid first, then valid
        String input = "notanumber\n42\n";
        mockBufferedReader(input);

        int result = cadastroController.getIntInput();
        assertEquals(42, result);
    }

    @Test
    void testGetIdadeInput() {
        mockBufferedReader("25");
        int idade = cadastroController.getIdadeInput();
        assertEquals(25, idade);
    }

}