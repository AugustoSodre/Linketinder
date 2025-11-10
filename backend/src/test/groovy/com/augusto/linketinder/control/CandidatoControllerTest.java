package com.augusto.linketinder.control;

import com.augusto.linketinder.service.CandidatoService;
import com.augusto.linketinder.dao.DaoFactory;
import com.augusto.linketinder.view.CandidatoView;
import com.augusto.linketinder.view.helper.ViewHelper;
import com.augusto.linketinder.service.InputService;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CandidatoControllerTest {

    @Test
    void atualizarCandidatoCampoShouldDelegateToService() {
        // Arrange - create a spy-like CandidatoService that records calls
        AtomicBoolean called = new AtomicBoolean(false);
        AtomicInteger capturedId = new AtomicInteger(-1);
        AtomicReference<String> capturedCol = new AtomicReference<>();
        AtomicReference<Object> capturedValue = new AtomicReference<>();

        CandidatoService spyService = new CandidatoService(DaoFactory.getCandidatoDAO()) {
            public void updateCandidato(int id, String coluna, Object novoValor) {
                called.set(true);
                capturedId.set(id);
                capturedCol.set(coluna);
                capturedValue.set(novoValor);
            }
        };

        ViewHelper viewHelper = new ViewHelper();
        CandidatoView candidatoView = new CandidatoView();
        InputService inputService = new InputService();

        CandidatoController controller = new CandidatoController(viewHelper, null, spyService, candidatoView, inputService);

        // Act
        controller.atualizarCandidatoCampo(42, "nome", "Novo Nome");

        // Assert
        assertTrue(called.get(), "Expected CandidatoService.updateCandidato to be called");
        assertEquals(42, capturedId.get());
        assertEquals("nome", capturedCol.get());
        assertEquals("Novo Nome", capturedValue.get());
    }
}
