package com.augusto.linketinder.control.update

import com.augusto.linketinder.control.CadastroController

class UpdateController {

    private final UpdateControllerCandidato candidatoController
    private final UpdateControllerEmpresa empresaController
    private final UpdateControllerVaga vagaController
    private final UpdateControllerCompetencia competenciaController

    UpdateController() {
        this(new CadastroController())
    }

    UpdateController(CadastroController cadastroController) {
        this(
                new UpdateControllerCandidato(cadastroController),
                new UpdateControllerEmpresa(cadastroController),
                new UpdateControllerVaga(cadastroController),
                new UpdateControllerCompetencia(cadastroController)
        )
    }

    UpdateController(UpdateControllerCandidato candidatoController,
                     UpdateControllerEmpresa empresaController,
                     UpdateControllerVaga vagaController,
                     UpdateControllerCompetencia competenciaController) {
        this.candidatoController = candidatoController
        this.empresaController = empresaController
        this.vagaController = vagaController
        this.competenciaController = competenciaController
    }

    void updateCandidato() {
        candidatoController.updateCandidato()
    }

    void updateEmpresa() {
        empresaController.updateEmpresa()
    }

    void updateVaga() {
        vagaController.updateVaga()
    }

    void updateComp() {
        competenciaController.updateCompetencia()
    }
}
