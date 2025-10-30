package com.augusto.linketinder.view.create

import com.augusto.linketinder.dao.DAO_Competencia
import com.augusto.linketinder.service.InputService
import com.augusto.linketinder.model.Competencia

class CadastroCompView {

    private final InputService inputService
    private final DAO_Competencia competenciaDao

    CadastroCompView() {
        this(new InputService(), new DAO_Competencia())
    }

    CadastroCompView(InputService inputService, DAO_Competencia competenciaDao) {
        this.inputService = inputService
        this.competenciaDao = competenciaDao
    }

    void show() {
        println "Cadastro da Competência"
        println()

        Competencia comp = createCompetencia()

        try {
            competenciaDao.insert(comp)
            println()
            println("Competência adicionada com sucesso!")
        } catch (Exception err){
            println("Erro ao adicionar Competência! " + err.message)
        }

    }

    Competencia createCompetencia(){
        Competencia comp = new Competencia()

        print("Digite o nome da Competência: ")
        comp.nome = inputService.getNomeInput()

        return comp
    }
}
