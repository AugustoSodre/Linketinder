package com.augusto.linketinder.control;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

class CadastroControllerTest {

    CadastroController cadastroController = new CadastroController();

    public CadastroController getCadastroController() {
        return cadastroController;
    }

    void mockBufferedReader(String input){
        getCadastroController().setBr(new BufferedReader(new StringReader(input + "\n")));
    }


    @ParameterizedTest
    @ValueSource(strings = {
            ""
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
        mockBufferedReader(input);

        //Act
        String result = getCadastroController().getStringInput();

        //Assert
        assertTrue(!result.isEmpty() && result.equals(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Augusto",
            "João",
            "Maria",
            "Calos Almeida",
            "Lucas Gonçalves"
    })
    void getNomeInput(String input) {
        //Arrange
        mockBufferedReader(input);

        //Act
        String result = getCadastroController().getNomeInput();

        //Assert
        assertEquals(input, result);
    }

    @Test
    void getEmailInput() {
    }

    @Test
    void getEstadoInput() {
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

    @Test
    void getIntInput() {
    }

    @Test
    void getIdadeInput() {
    }
}