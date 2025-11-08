package com.augusto.linketinder.view.menu

import com.augusto.linketinder.dao.CandidatoDAO
import com.augusto.linketinder.dao.CompetenciaDAO
import com.augusto.linketinder.dao.DaoFactory
import com.augusto.linketinder.dao.EmpresaDAO
import com.augusto.linketinder.dao.VagaDAO
import com.augusto.linketinder.control.DeleteController
import com.augusto.linketinder.control.UpdateController
import com.augusto.linketinder.service.InputService
import com.augusto.linketinder.view.create.CadastroCandidatoView
import com.augusto.linketinder.view.create.CadastroCompView
import com.augusto.linketinder.view.create.CadastroEmpresaView
import com.augusto.linketinder.view.create.CadastroVagaView
import com.augusto.linketinder.view.delete.DeleteView
import com.augusto.linketinder.view.helper.ViewHelper
import com.augusto.linketinder.view.read.ReadView
import com.augusto.linketinder.view.update.UpdateCompetenciaView
import com.augusto.linketinder.view.update.UpdateCandidatoView
import com.augusto.linketinder.view.update.UpdateEmpresaView
import com.augusto.linketinder.view.update.UpdateVagaView
import com.augusto.linketinder.view.update.UpdateView

class MenuMain {

    private final InputService inputService
    private final MenuEmpresa menuEmpresa
    private final MenuCandidato menuCandidato
    private final MenuVaga menuVaga
    private final MenuCompetencia menuCompetencia

    MenuMain() {
        InputService sharedInputService = new InputService()

        CompetenciaDAO competenciaDao = DaoFactory.getCompetenciaDAO()
        VagaDAO vagaDao = DaoFactory.getVagaDAO()
        CandidatoDAO candidatoDao = DaoFactory.getCandidatoDAO()
        EmpresaDAO empresaDao = DaoFactory.getEmpresaDAO()
        ViewHelper viewHelper = new ViewHelper()

        CadastroCandidatoView cadastroCandidatoView = new CadastroCandidatoView(sharedInputService, candidatoDao, competenciaDao, viewHelper)
        CadastroEmpresaView cadastroEmpresaView = new CadastroEmpresaView(sharedInputService, empresaDao, competenciaDao, viewHelper)
        CadastroVagaView cadastroVagaView = new CadastroVagaView(sharedInputService, vagaDao, competenciaDao, viewHelper)
        CadastroCompView cadastroCompView = new CadastroCompView(sharedInputService, competenciaDao)

        DeleteController deleteController = new DeleteController(candidatoDao, empresaDao, vagaDao, competenciaDao, sharedInputService)
        DeleteView deleteView = new DeleteView(deleteController)

    UpdateController updateController = new UpdateController(candidatoDao, empresaDao, vagaDao, competenciaDao)
        UpdateCandidatoView updateCandidatoView = new UpdateCandidatoView(updateController, sharedInputService)
        UpdateEmpresaView updateEmpresaView = new UpdateEmpresaView(updateController, sharedInputService)
        UpdateVagaView updateVagaView = new UpdateVagaView(updateController, sharedInputService)
        UpdateCompetenciaView updateCompetenciaView = new UpdateCompetenciaView(updateController, sharedInputService)
        UpdateView updateView = new UpdateView(updateCandidatoView, updateEmpresaView, updateVagaView, updateCompetenciaView)

        ReadView readView = new ReadView(candidatoDao, empresaDao, vagaDao, competenciaDao)

        this.inputService = sharedInputService
        this.menuEmpresa = new MenuEmpresa(sharedInputService, cadastroEmpresaView, readView, updateView, deleteView)
        this.menuCandidato = new MenuCandidato(sharedInputService, cadastroCandidatoView, readView, updateView, deleteView)
        this.menuVaga = new MenuVaga(sharedInputService, cadastroVagaView, readView, updateView, deleteView)
        this.menuCompetencia = new MenuCompetencia(sharedInputService, cadastroCompView, readView, updateView, deleteView)
    }

    MenuMain(InputService inputService,
             MenuEmpresa menuEmpresa,
             MenuCandidato menuCandidato,
             MenuVaga menuVaga,
             MenuCompetencia menuCompetencia) {
        this.inputService = inputService
        this.menuEmpresa = menuEmpresa
        this.menuCandidato = menuCandidato
        this.menuVaga = menuVaga
        this.menuCompetencia = menuCompetencia
    }

    boolean showMainMenu() {
        printOptions()
        int option = inputService.getIntInput()
        switch (option) {
            case 1:
                limpaTela()
                menuEmpresa.showMenuEmpresa()
                break
            case 2:
                limpaTela()
                menuCandidato.showMenuCandidato()
                break
            case 3:
                limpaTela()
                menuVaga.showMenuVaga()
                break
            case 4:
                limpaTela()
                menuCompetencia.showMenuComp()
                break
            case 0:
                return true
            default:
                print("Input inválido!")
        }

        return false
    }

    void printOptions() {
        limpaTela()
        println()
        println("-" * 25)
        println "Linketinder!"
        println()
        println "Opções:"
        println "1. Gerenciar Empresas"
        println "2. Gerenciar Candidatos"
        println "3. Gerenciar Vagas"
        println "4. Gerenciar Competências"
        println "0. Sair"
        println()
        println("-" * 25)
    }

    void limpaTela() {
        for (i in 0..<50) {
            println()
        }
    }
}

