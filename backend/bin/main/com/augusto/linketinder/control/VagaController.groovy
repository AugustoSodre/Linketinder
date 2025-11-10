package com.augusto.linketinder.control

import com.augusto.linketinder.dao.CompetenciaDAO
import com.augusto.linketinder.dao.DaoFactory
import com.augusto.linketinder.model.Competencia
import com.augusto.linketinder.model.Vaga
import com.augusto.linketinder.service.VagaService
import com.augusto.linketinder.service.InputService
import com.augusto.linketinder.view.VagaView
import com.augusto.linketinder.view.helper.ViewHelper

class VagaController {
    ViewHelper viewHelper
    CompetenciaDAO competenciaDAO
    VagaService vagaService
    VagaView vagaView
    InputService inputService

    VagaController(ViewHelper viewHelper,
                   CompetenciaDAO competenciaDAO,
                   VagaService vagaService,
                   VagaView vagaView,
                   InputService inputService){
        this.viewHelper = viewHelper
        this.competenciaDAO = competenciaDAO
        this.vagaService = vagaService
        this.vagaView = vagaView
        this.inputService = inputService
    }

    VagaController(){
        this(new ViewHelper(), DaoFactory.getCompetenciaDAO(), new VagaService(DaoFactory.getVagaDAO()), new VagaView(), new InputService())
    }

    void createVaga(){
        Vaga vaga = new Vaga()

        println "Cadastro de Vaga"
        println()

        vagaView.printMessage("Digite o ID da Empresa: ")
        vaga.id_empresa = inputService.getIntInput()

        vagaView.printMessage("Digite o Nome da Vaga: ")
        vaga.nome = inputService.getNomeInput()

        vagaView.printMessage("Digite a Descrição da Vaga: ")
        vaga.descricao = inputService.getDescricaoInput()

        vagaView.printMessage("Digite a Cidade da Vaga: ")
        vaga.cidade = inputService.getNomeInput()

        vagaView.printMessage("Digite o Estado da Vaga: ")
        vaga.estado = inputService.getEstadoInput()

        println()
        vagaView.printlnMessage("Competências disponíveis:")
        List<Competencia> listaComp = competenciaDAO.listAll()
        viewHelper.printAllAvailableCompetencias(listaComp)
        vaga.competencias = inputService.getCompetenciasInput(listaComp)

        vagaService.createVaga(vaga)
    }

    void readVaga(){
        vagaView.showReadVagas(vagaService.getVagaList())
    }

    List listarVagas() {
        return vagaService.getVagaList()
    }

    Map<String, String> camposVaga() {
        return vagaService.getCamposVaga()
    }

    void atualizarVagaCampo(int id, String coluna, Object novoValor) {
        vagaService.updateVaga(id, coluna, novoValor)
    }

    void deleteVaga(){
        vagaView.showDeleteVaga()
        int id = inputService.getIntInput()
        vagaService.deleteVaga(id)
    }
}
 
