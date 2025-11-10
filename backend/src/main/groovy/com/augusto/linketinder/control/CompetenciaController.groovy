package com.augusto.linketinder.control

import com.augusto.linketinder.model.Competencia
import com.augusto.linketinder.service.CompetenciaService
import com.augusto.linketinder.dao.DaoFactory
import com.augusto.linketinder.service.InputService
import com.augusto.linketinder.view.CompetenciaView

class CompetenciaController {
    CompetenciaService competenciaService
    CompetenciaView competenciaView
    InputService inputService

    CompetenciaController(CompetenciaService competenciaService, CompetenciaView competenciaView, InputService inputService){
        this.competenciaService = competenciaService
        this.competenciaView = competenciaView
        this.inputService = inputService
    }

    CompetenciaController(){
        this(new CompetenciaService(DaoFactory.getCompetenciaDAO()), new CompetenciaView(), new InputService())
    }

    void createCompetencia(){
        println 'Cadastro de Competência'
        println()
        competenciaView.printMessage('Digite o nome da competência: ')
        String nome = inputService.getNomeInput()
        Competencia comp = new Competencia()
        comp.nome = nome
        competenciaService.createCompetencia(comp)
    }

    List<Competencia> listarCompetencias(){
        return competenciaService.getCompetenciaList()
    }

    void readCompetencia(){
        competenciaView.showReadCompetencias(competenciaService.getCompetenciaList())
    }

    void atualizarCompetenciaNome(int id, String novoNome){
        competenciaService.updateCompetenciaNome(id, novoNome)
    }

    String listarRelacionamentos(String objeto){
        return competenciaService.listarRelacionamentos(objeto)
    }

    void adicionarRelacionamento(String objeto, int idCompetencia, int idObjeto){
        competenciaService.adicionarRelacionamento(objeto, idCompetencia, idObjeto)
    }

    void removerRelacionamento(String objeto, int idCompetencia, int idObjeto){
        competenciaService.removerRelacionamento(objeto, idCompetencia, idObjeto)
    }

    void deleteCompetencia(){
        competenciaView.showDeleteCompetencia()
        int id = inputService.getIntInput()
        competenciaService.deleteCompetencia(id)
    }

}
 
