package com.augusto.linketinder.view.create

import com.augusto.linketinder.DAO.DAO_Competencia
import com.augusto.linketinder.service.InputService
import com.augusto.linketinder.model.Competencia

class CadastroCompView {
    InputService inputService = new InputService()
    private final DAO_Competencia competenciaDao = new DAO_Competencia()

    void show() {
        println "Cadastro da Competência"
        println()

        Competencia comp = new Competencia()

        print("Digite o nome da Competência: ")
        comp.nome = inputService.getNomeInput()

        // Adiciona pessoa ao final do processo
        try {
            competenciaDao.insert(comp)
            println()
            println("Competência adicionada com sucesso!")
        } catch (Exception err){
            println("Erro ao adicionar Competência! " + err.message)
        }


    }
}
