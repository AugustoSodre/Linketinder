package com.augusto.linketinder.control.update

import com.augusto.linketinder.control.CadastroController

class UpdateController {

    private final UpdateControllerCandidato candidatoController = new UpdateControllerCandidato()
    private final UpdateControllerEmpresa empresaController = new UpdateControllerEmpresa()
    private final UpdateControllerVaga vagaController = new UpdateControllerVaga()
    private final UpdateControllerCompetencia competenciaController = new UpdateControllerCompetencia()

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
