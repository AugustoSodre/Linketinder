package com.augusto.linketinder.view.create

import com.augusto.linketinder.dao.DAO_Competencia
import com.augusto.linketinder.dao.DAO_Vaga
import com.augusto.linketinder.service.InputService
import com.augusto.linketinder.model.Competencia
import com.augusto.linketinder.model.Vaga
import com.augusto.linketinder.view.helper.ViewHelper

class CadastroVagaView {

    private final InputService inputService
    private final DAO_Vaga vagaDao
    private final DAO_Competencia competenciaDao
    private final ViewHelper viewHelper

    CadastroVagaView() {
        this(new InputService(), new DAO_Vaga(), new DAO_Competencia(), new ViewHelper())
    }

    CadastroVagaView(InputService inputService,
                     DAO_Vaga vagaDao,
                     DAO_Competencia competenciaDao,
                     ViewHelper viewHelper) {
        this.inputService = inputService
        this.vagaDao = vagaDao
        this.competenciaDao = competenciaDao
        this.viewHelper = viewHelper
    }

    void show() {
        println "Cadastro da Vaga"
        println()

        Vaga vaga = createVaga()

        try {
            vagaDao.insert(vaga)
            println()
            println("Vaga adicionada com sucesso!")
        } catch (Exception err){
            println("Erro ao adicionar vaga! " + err.message)
        }

    }

    Vaga createVaga(){
        Vaga vaga = new Vaga()

        print("Digite o ID da Empresa: ")
        vaga.id_empresa = inputService.getIntInput()

        print("Digite o Título da Vaga: ")
        vaga.nome = inputService.getNomeInput()

        print("Digite a Descrição da Vaga: ")
        vaga.descricao = inputService.getDescricaoInput()

        print("Digite a Cidade da Vaga: ")
        vaga.cidade = inputService.getNomeInput()

        print("Digite o Estado da Vaga: ")
        vaga.estado = inputService.getEstadoInput()

        println()
        println("Competências disponíveis:")
        List<Competencia> listaComp = competenciaDao.listAll()
    viewHelper.printAllAvailableCompetencias(listaComp)
        vaga.competencias = inputService.getCompetenciasInput(listaComp)

        return vaga
    }
}
