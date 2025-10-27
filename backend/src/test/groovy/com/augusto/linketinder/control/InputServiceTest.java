package com.augusto.linketinder.control;

import com.augusto.linketinder.service.InputService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.BufferedReader;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

class InputServiceTest {

    InputService inputService;

    public InputService getCadastroController() {
        return inputService;
    }

    void mockBufferedReader(String input){
        getCadastroController().setBr(new BufferedReader(new StringReader(input)));
    }

    @BeforeEach
    void setUp() {
        inputService = new InputService();
    }


    // --- Testes para getStringInput()
    @Test
    void testEmptyGetStringInput() {
        //Arrange
        String input = ("o\n").repeat(6);
        mockBufferedReader(input);

        //Act + Assert
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
        mockBufferedReader("jose@hotmail.com\n");

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
        String result = getCadastroController().getEstadoInput();

        //Assert
        assertEquals(input.toUpperCase(), result);
    }

    @Test
    void testInvalidGetEstadoInput() {
        //Arrange
        String input = ("o\n").repeat(6);
        mockBufferedReader(input);

        //Act + Assert
        assertThrows(RuntimeException.class, () -> inputService.getEstadoInput());
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
        assertThrows(RuntimeException.class, () -> inputService.getCepInput());
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
        String result = getCadastroController().getDescricaoInput();

        //Assert
        assertEquals(input.trim(), result);
    }


    // // --- Testes para getCompetenciasInput()
//    @Test
//    void testGetCompetenciasInputWithValidSelections() {
//        //Arrange
//        mockBufferedReader("1\n2\n0\n");
//
//        //Act
//        List<EnumCompetencias> result = inputService.getCompetenciasInput();
//
//        //Assert
//        assertNotNull(result);
//        assertFalse(result.isEmpty());
//    }

//    @Test
//    void testGetCompetenciasInputExitImmediately() {
//        //Arrange
//        mockBufferedReader("0\n");
//
//        //Act
//        List<EnumCompetencias> result = inputService.getCompetenciasInput();
//
//        //Assert
//        assertNotNull(result);
//        assertEquals(0, result.size());
//    }

//    @Test
//    void testGetCompetenciasInputDuplicateSelection() {
//        //Arrange
//        mockBufferedReader("1\n1\n0\n");
//
//        //Act
//        List<EnumCompetencias> result = inputService.getCompetenciasInput();
//
//        //Assert
//        assertNotNull(result);
//        assertTrue(result.size() == 1);
//    }

//    @Test
//    void testGetCompetenciasInputExceedsMaxTentativas() {
//        //Arrange
//        String invalidInput = "999\n".repeat(6);
//        mockBufferedReader(invalidInput);
//
//        //Act + Assert
//        assertThrows(RuntimeException.class, () -> inputService.getCompetenciasInput());
//    }


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
        assertThrows(RuntimeException.class, () -> inputService.getCpfInput());
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
        assertThrows(RuntimeException.class, () -> inputService.getCnpjInput());
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
        //Arrange
        String input = "notanumber\n42\n";
        mockBufferedReader(input);

        //Act
        int result = inputService.getIntInput();

        //Assert
        assertEquals(42, result);
    }

    @Test
    void testGetIdadeInput() {
        //Arrange
        mockBufferedReader("25");

        //Act
        int idade = inputService.getIdadeInput();

        //Assert
        assertEquals(25, idade);
    }

    //--- Testes para adicionar Pessoa a lista respectiva
//    @Test
//    void testInsertPessoaFisica() {
//        //Arrange
//        Candidato pessoaFisica = new Candidato();
//        inputService.insertPessoa(pessoaFisica);
//
//        //Act + Assert
//        assertEquals(ListaCandidatoEstatica.getLista().getLast(), pessoaFisica);
//    }
//
//    @Test
//    void testInsertPessoaJuridica() {
//        //Arrange
//        Empresa empresa = new Empresa();
//        inputService.insertPessoa(empresa);
//
//        //Act + Assert
//        assertEquals(ListaEmpresaEstatica.getLista().getLast(), empresa);
//    }


}