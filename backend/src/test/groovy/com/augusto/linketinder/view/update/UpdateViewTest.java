package com.augusto.linketinder.view.update;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UpdateViewTest {

    private RecordingCandidatoView candidatoView;
    private RecordingEmpresaView empresaView;
    private RecordingVagaView vagaView;
    private RecordingCompetenciaView competenciaView;
    private UpdateView updateView;

    @BeforeEach
    void setUp() {
        candidatoView = new RecordingCandidatoView();
        empresaView = new RecordingEmpresaView();
        vagaView = new RecordingVagaView();
        competenciaView = new RecordingCompetenciaView();
        updateView = new UpdateView(candidatoView, empresaView, vagaView, competenciaView);
    }

    @Test
    void showUpdateCandidatoShouldDelegateToCandidatoView() {
        // Act
        updateView.showUpdateCandidato();

        // Assert
        assertEquals(1, candidatoView.invocations);
        assertEquals(0, empresaView.invocations);
        assertEquals(0, vagaView.invocations);
        assertEquals(0, competenciaView.invocations);
    }

    @Test
    void showUpdateEmpresaShouldDelegateToEmpresaView() {
        // Act
        updateView.showUpdateEmpresa();

        // Assert
        assertEquals(0, candidatoView.invocations);
        assertEquals(1, empresaView.invocations);
        assertEquals(0, vagaView.invocations);
        assertEquals(0, competenciaView.invocations);
    }

    @Test
    void showUpdateVagaShouldDelegateToVagaView() {
        // Act
        updateView.showUpdateVaga();

        // Assert
        assertEquals(0, candidatoView.invocations);
        assertEquals(0, empresaView.invocations);
        assertEquals(1, vagaView.invocations);
        assertEquals(0, competenciaView.invocations);
    }

    @Test
    void showUpdateCompetenciaShouldDelegateToCompetenciaView() {
        // Act
        updateView.showUpdateCompetencia();

        // Assert
        assertEquals(0, candidatoView.invocations);
        assertEquals(0, empresaView.invocations);
        assertEquals(0, vagaView.invocations);
        assertEquals(1, competenciaView.invocations);
    }

    private static class RecordingCandidatoView extends UpdateCandidatoView {
        int invocations = 0;

        @Override
        public void show() {
            invocations++;
        }
    }

    private static class RecordingEmpresaView extends UpdateEmpresaView {
        int invocations = 0;

        @Override
        public void show() {
            invocations++;
        }
    }

    private static class RecordingVagaView extends UpdateVagaView {
        int invocations = 0;

        @Override
        public void show() {
            invocations++;
        }
    }

    private static class RecordingCompetenciaView extends UpdateCompetenciaView {
        int invocations = 0;

        @Override
        public void show() {
            invocations++;
        }
    }
}
