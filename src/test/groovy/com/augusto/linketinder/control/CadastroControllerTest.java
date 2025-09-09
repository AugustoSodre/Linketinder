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
    @Test
    void testEmptyGetStringInput() {
        //Arrange
        String input = ("o\n").repeat(6);
        mockBufferedReader(input);

        //Act + Assert
        assertThrows(RuntimeException.class, () -> {
            cadastroController.getEstadoInput();
        });
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
    })
    void testValidGetNomeInput(String input) {
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
    void testValidGetEstadoInput(String input) {
        //Arrange
        mockBufferedReader(input + "\n");

        //Act
        String result = getCadastroController().getNomeInput();

        //Assert
        assertEquals(input, result);
    }

    @Test
    void testInvalidGetEstadoInput() {
        //Arrange
        String input = ("o\n").repeat(6);
        mockBufferedReader(input);

        //Act + Assert
        assertThrows(RuntimeException.class, () -> {
            cadastroController.getEstadoInput();
        });
    }


    // --- Testes para getCepInput()
    @ParameterizedTest
    @ValueSource(strings = {
            "77777-777",
            "12345678"
    })
    void testValidGetCepInput(String input) {
        //Arrange
        mockBufferedReader(input + "\n");

        //Act
        String result = getCadastroController().getCepInput();

        //Assert
        assertEquals(input, result);
    }

    @Test
    void testInvalidGetCepInput() {
        //Arrange
        String input = ("o\n").repeat(6);
        mockBufferedReader(input);

        //Act + Assert
        assertThrows(RuntimeException.class, () -> {
            cadastroController.getCepInput();
        });
    }

    // --- Testes para getDescricaoInput()
    @ParameterizedTest
    @ValueSource(strings = {
            "Estudante",
            "Grande Estudante"
    })
    void testValidGetDescricaoInput(String input) {
        //Arrange
        mockBufferedReader(input + "\n");

        //Act
        String result = getCadastroController().getNomeInput();

        //Assert
        assertEquals(input.trim(), result);
    }

    @Test
    void getCompetenciasInput() {
    }


    // --- Testes para getCpfInput()
    @ParameterizedTest
    @ValueSource(strings = {
            "123.456.789-10",
            "00011122233"
    })
    void testValidGetCpfInput(String input) {
        //Arrange
        mockBufferedReader(input + "\n");

        //Act
        String result = getCadastroController().getCpfInput();

        //Assert
        assertEquals(input.trim(), result);
    }

    @Test
    void testInvalidGetCpfInput() {
        //Arrange
        String input = ("o\n").repeat(6); // eh um formato invalido para CPF seguindo o REGEX
        mockBufferedReader(input);

        //Act + Assert
        assertThrows(RuntimeException.class, () -> {
            cadastroController.getCpfInput();
        });
    }


    // --- Testes para getCnpjInput()
    @ParameterizedTest
    @ValueSource(strings = {
            "12345678912345",
            "66.872.236/0001-26"
    })
    void testValidGetCnpjInput(String input) {
        //Arrange
        mockBufferedReader(input + "\n");

        //Act
        String result = getCadastroController().getCnpjInput();

        //Assert
        assertEquals(input.trim(), result);
    }

    @Test
    void testInvalidGetCnpjInput() {
        //Arrange
        String input = ("1234567.89/12345\n").repeat(6); // eh um formato invalido para CNPJ seguindo o REGEX
        mockBufferedReader(input);

        //Act + Assert
        assertThrows(RuntimeException.class, () -> {
            cadastroController.getCnpjInput();
        });
    }


    //--- Testes para getPaisInput()
    @ParameterizedTest
    @ValueSource(strings = {
            "Brasil",
            "BR"
    })
    void testGetPaisInput(String input) {
        //Arrange
        mockBufferedReader(input + "\n");

        //Act
        String result = getCadastroController().getPaisInput();

        //Assert
        assertEquals(input.trim(), result);
    }

    //--- Testes para inputs de números inteiros
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