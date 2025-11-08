package com.augusto.linketinder.view.create

import com.augusto.linketinder.dao.CandidatoDAO
import com.augusto.linketinder.dao.CompetenciaDAO
import com.augusto.linketinder.service.InputService
import com.augusto.linketinder.model.Competencia
import com.augusto.linketinder.model.pessoa.Candidato
import com.augusto.linketinder.view.helper.ViewHelper

class CadastroCandidatoView {

    private final InputService inputService
    private final CandidatoDAO candidatoDao
    private final CompetenciaDAO competenciaDao
    private final ViewHelper viewHelper

    CadastroCandidatoView() {
        this(new InputService(), new CandidatoDAO(), new CompetenciaDAO(), new ViewHelper())
    }

    CadastroCandidatoView(InputService inputService,
                          CandidatoDAO candidatoDao,
                          CompetenciaDAO competenciaDao,
                          ViewHelper viewHelper) {
        this.inputService = inputService
        this.candidatoDao = candidatoDao
        this.competenciaDao = competenciaDao
        this.viewHelper = viewHelper
    }

    void show(){
        println "Cadastro do Candidato"
        println()

        Candidato candidato = createCandidato()

        try {
            candidatoDao.insert(candidato)
            println()
            println("Candidato adicionado com sucesso!")
        } catch (Exception e){
            println("Erro ao adicionar candidato! " + e.message)
        }
    }

    Candidato createCandidato(){
        Candidato pessoa = new Candidato()

        print("Digite o Nome do Candidato: ")
        pessoa.nome = inputService.getNomeInput()

        print("Digite o Email do Candidato: ")
        pessoa.email = inputService.getEmailInput()

        print("Digite o Estado do Candidato: ")
        pessoa.estado = inputService.getEstadoInput()

        print("Digite o CEP do Candidato: ")
        pessoa.cep = inputService.getCepInput()

        print("Digite a Descrição do Candidato: ")
        pessoa.descricao = inputService.getDescricaoInput()

        print("Digite o CPF do Candidato: ")
        pessoa.cpf = inputService.getCpfInput()

        print("Digite a idade do Candidato: ")
        pessoa.idade = inputService.getIdadeInput()

        println()
        println("Competências disponíveis:")
        List<Competencia> listaComp = competenciaDao.listAll()
    viewHelper.printAllAvailableCompetencias(listaComp)
        pessoa.competencias = inputService.getCompetenciasInput(listaComp)

        print("Digite a senha do Candidato: ")
        pessoa.senha = inputService.getSenhaInput()

        return pessoa
    }
}
