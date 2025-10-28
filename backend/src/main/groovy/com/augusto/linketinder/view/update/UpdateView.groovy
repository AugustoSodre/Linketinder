package com.augusto.linketinder.view.update

class UpdateView {

    private final UpdateCandidatoView candidatoView
    private final UpdateEmpresaView empresaView
    private final UpdateVagaView vagaView
    private final UpdateCompetenciaView competenciaView

    UpdateView() {
        this(new UpdateCandidatoView(), new UpdateEmpresaView(), new UpdateVagaView(), new UpdateCompetenciaView())
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

    void showUpdateComp() {
        competenciaView.show()
    }
}
