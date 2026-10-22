package com.augusto.linketinder.control

import com.augusto.linketinder.dao.CompetenciaDAO
import com.augusto.linketinder.dao.DaoFactory
import com.augusto.linketinder.model.Competencia
import com.augusto.linketinder.model.pessoa.Candidato
import com.augusto.linketinder.service.CandidatoService
import com.augusto.linketinder.service.InputService
import com.augusto.linketinder.view.CandidatoView
import com.augusto.linketinder.view.helper.ViewHelper

class CandidatoController {
    ViewHelper viewHelper
    CompetenciaDAO competenciaDAO
    CandidatoService candidatoService
    CandidatoView candidatoView
    InputService inputService

    CandidatoController(ViewHelper viewHelper,
                        CompetenciaDAO competenciaDAO,
                        CandidatoService candidatoService,
                        CandidatoView candidatoView,
                        InputService inputService){
        this.viewHelper = viewHelper
        this.competenciaDAO = competenciaDAO
        this.candidatoService = candidatoService
        this.candidatoView = candidatoView
        this.inputService = inputService
    }

    CandidatoController() {
        this(new ViewHelper(), DaoFactory.getCompetenciaDAO(), new CandidatoService(DaoFactory.getCandidatoDAO()), new CandidatoView(), new InputService())
    }

    void createCandidato(){
        Candidato pessoa = new Candidato()

        println "Cadastro do Candidato"
        println()

        candidatoView.printMessage("Digite o Nome do Candidato: ")
        pessoa.nome = inputService.getNomeInput()

        candidatoView.printMessage("Digite o Email do Candidato: ")
        pessoa.email = inputService.getEmailInput()

        candidatoView.printMessage("Digite o Estado do Candidato: ")
        pessoa.estado = inputService.getEstadoInput()

        candidatoView.printMessage("Digite o CEP do Candidato: ")
        pessoa.cep = inputService.getCepInput()

        candidatoView.printMessage("Digite a Descrição do Candidato: ")
        pessoa.descricao = inputService.getDescricaoInput()

        candidatoView.printMessage("Digite o CPF do Candidato: ")
        pessoa.cpf = inputService.getCpfInput()

        candidatoView.printMessage("Digite a idade do Candidato: ")
        pessoa.idade = inputService.getIdadeInput()

        println()
        candidatoView.printlnMessage("Competências disponíveis:")
        List<Competencia> listaComp = competenciaDAO.listAll()
        viewHelper.printAllAvailableCompetencias(listaComp)
        pessoa.competencias = inputService.getCompetenciasInput(listaComp)

        print("Digite a senha do Candidato: ")
        pessoa.senha = inputService.getSenhaInput()

        try {
            candidatoService.createCandidato(pessoa)
        } catch (Exception e) {
            println("Erro ao adicionar candidato! " + e.message)
        }
    }

    void readCandidato(){
        candidatoView.showReadCandidatos(candidatoService.getCandidatoList())
    }

    void updateCandidato(){
        println 'Atualização de Candidato'
        new AtualizacaoCli(inputService).executar(candidatoService.getCandidatoList(), candidatoService.getCamposCandidato()) { int id, String coluna, valor ->
            atualizarCandidatoCampo(id, coluna, valor)
        }
    }

    void atualizarCandidatoCampo(int id, String coluna, Object novoValor) {
        candidatoService.updateCandidato(id, coluna, novoValor)
    }

    List listarCandidatos() {
        return candidatoService.getCandidatoList()
    }

    Map<String, String> camposCandidato() {
        return candidatoService.getCamposCandidato()
    }

    void deleteCandidato(){
        candidatoView.showDeleteCandidato()
        int id = inputService.getIntInput()
        candidatoService.deleteCandidato(id)
    }

}
