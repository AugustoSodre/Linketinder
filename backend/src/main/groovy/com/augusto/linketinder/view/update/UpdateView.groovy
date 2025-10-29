package com.augusto.linketinder.view.update

import com.augusto.linketinder.control.UpdateController
import com.augusto.linketinder.service.InputService

class UpdateView {

    private final UpdateCandidatoView candidatoView
    private final UpdateEmpresaView empresaView
    private final UpdateVagaView vagaView
    private final UpdateCompetenciaView competenciaView

    UpdateView() {
        this(*createDefaultViews())
    }

    UpdateView(UpdateCandidatoView candidatoView,
               UpdateEmpresaView empresaView,
               UpdateVagaView vagaView,
               UpdateCompetenciaView competenciaView) {
        this.candidatoView = candidatoView
        this.empresaView = empresaView
        this.vagaView = vagaView
        this.competenciaView = competenciaView
    }

    void showUpdateCandidato() {
        candidatoView.show()
    }

    void showUpdateEmpresa() {
        empresaView.show()
    }

    void showUpdateVaga() {
        vagaView.show()
    }

    void showUpdateCompetencia() {
        competenciaView.show()
    }

    private static List createDefaultViews() {
        UpdateController sharedController = new UpdateController()
        InputService sharedInputService = new InputService()

        return [
                new UpdateCandidatoView(sharedController, sharedInputService),
                new UpdateEmpresaView(sharedController, sharedInputService),
                new UpdateVagaView(sharedController, sharedInputService),
                new UpdateCompetenciaView(sharedController, sharedInputService)
        ]
    }
}
