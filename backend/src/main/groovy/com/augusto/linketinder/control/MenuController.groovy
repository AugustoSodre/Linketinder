package com.augusto.linketinder.control

import com.augusto.linketinder.service.InputService

class MenuController {

    InputService inputService
    EmpresaController empresaController
    CandidatoController candidatoController
    VagaController vagaController
    CompetenciaController competenciaController

    MenuController(InputService inputService,
                   EmpresaController empresaController,
                   CandidatoController candidatoController,
                   VagaController vagaController,
                   CompetenciaController competenciaController) {
        this.inputService = inputService
        this.empresaController = empresaController
        this.candidatoController = candidatoController
        this.vagaController = vagaController
        this.competenciaController = competenciaController
    }

    MenuController() {
        this(new InputService(), new EmpresaController(), new CandidatoController(), new VagaController(), new CompetenciaController())
    }

    void handleEmpresaOption(int option) {
        switch (option) {
            case 1:
                empresaController.createEmpresa()
                break
            case 2:
                empresaController.readEmpresa()
                break
            case 3:
                empresaController.updateEmpresa()
                break
            case 4:
                empresaController.deleteEmpresa()
                break
            case 0:
                break
            default:
                println "Input inválido"
        }
    }

    void handleCandidatoOption(int option) {
        switch (option) {
            case 1:
                candidatoController.createCandidato()
                break
            case 2:
                candidatoController.readCandidato()
                break
            case 3:
                candidatoController.updateCandidato()
                break
            case 4:
                candidatoController.deleteCandidato()
                break
            case 0:
                break
            default:
                println "Input inválido"
        }
    }

    void handleVagaOption(int option) {
        switch (option) {
            case 1:
                vagaController.createVaga()
                break
            case 2:
                vagaController.readVaga()
                break
            case 3:
                vagaController.updateVaga()
                break
            case 4:
                vagaController.deleteVaga()
                break
            case 0:
                break
            default:
                println "Input inválido"
        }
    }

    void handleCompetenciaOption(int option) {
        switch (option) {
            case 1:
                competenciaController.createCompetencia()
                break
            case 2:
                competenciaController.readCompetencia()
                break
            case 3:
                competenciaController.updateCompetencia()
                break
            case 4:
                competenciaController.deleteCompetencia()
                break
            case 0:
                break
            default:
                println "Input inválido"
        }
    }

}

