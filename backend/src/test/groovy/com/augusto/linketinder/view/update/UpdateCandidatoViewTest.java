package com.augusto.linketinder.view.update;

import com.augusto.linketinder.control.UpdateController;
import com.augusto.linketinder.model.Competencia;
import com.augusto.linketinder.model.pessoa.Candidato;
import com.augusto.linketinder.service.InputService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UpdateCandidatoViewTest {

    private RecordingUpdateController updateController;
    private InputService inputService;
    private UpdateCandidatoView view;

    @BeforeEach
    void setUp() {
        updateController = new RecordingUpdateController();
        inputService = new InputService();
        view = new UpdateCandidatoView(updateController, inputService);
    }

    @Test
    void showWhenNoCandidatesShouldExitGracefully() {
        // Arrange
        updateController.candidatos = Collections.emptyList();
        inputService.setBr(new BufferedReader(new StringReader("")));

        // Act
        view.show();

        // Assert
        assertEquals(1, updateController.listarCandidatosChamadas);
        assertEquals(0, updateController.camposCandidatoChamadas);
        assertEquals(0, updateController.atualizacoes.size());
    }

    @Test
    void showWhenCandidateSelectedShouldUpdateField() {
        // Arrange
        Candidato candidato = new Candidato(
                1,
                "Alice",
                "alice@email.com",
                "SP",
                "01000-000",
                "12345678900",
                25,
                "Descrição",
                Collections.<Competencia>emptyList(),
                "senha"
        );
        updateController.candidatos = Collections.singletonList(candidato);

        Map<String, String> campos = new LinkedHashMap<>();
        campos.put("nome", "nome");
        updateController.campos = campos;

        inputService.setBr(new BufferedReader(new StringReader("1\n1\nNovo Nome\n")));

        // Act
        view.show();

        // Assert
        assertEquals(1, updateController.listarCandidatosChamadas);
        assertEquals(1, updateController.camposCandidatoChamadas);
        assertEquals(1, updateController.atualizacoes.size());

        RecordingUpdateController.Atualizacao atualizacao = updateController.atualizacoes.get(0);
        assertEquals(1, atualizacao.id);
        assertEquals("nome", atualizacao.campo);
        assertEquals("Novo Nome", atualizacao.valor);
    }

    private static class RecordingUpdateController extends UpdateController {
        private List<Candidato> candidatos = Collections.emptyList();
        private Map<String, String> campos = Collections.emptyMap();
        private int listarCandidatosChamadas = 0;
        private int camposCandidatoChamadas = 0;
        private final List<Atualizacao> atualizacoes = new ArrayList<>();

        @Override
        public List<Candidato> listarCandidatos() {
            listarCandidatosChamadas++;
            return candidatos;
        }

        @Override
        public Map<String, String> camposCandidato() {
            camposCandidatoChamadas++;
            return campos;
        }

        @Override
        public void atualizarCandidatoCampo(int id, String coluna, Object novoValor) {
            atualizacoes.add(new Atualizacao(id, coluna, String.valueOf(novoValor)));
        }

        private static class Atualizacao {
            final int id;
            final String campo;
            final String valor;

            Atualizacao(int id, String campo, String valor) {
                this.id = id;
                this.campo = campo;
                this.valor = valor;
            }
        }
    }
}
