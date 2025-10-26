package com.augusto.linketinder.view.create

import com.augusto.linketinder.DAO.DAO_Competencia
import com.augusto.linketinder.control.CadastroController
import com.augusto.linketinder.model.Competencia
import com.augusto.linketinder.model.Vaga

class CadastroCompView {
    CadastroController cadastroController = new CadastroController()
    private final DAO_Competencia competenciaDao = new DAO_Competencia()

    void show() {
        println "Cadastro da Competência"
        println()

        Competencia comp = new Competencia()

        print("Digite o nome da Competência: ")
        comp.nome = cadastroController.getNomeInput()

        // Adiciona pessoa ao final do processo
        try {
            competenciaDao.insert(comp)
            println()
            println("Competência adicionada com sucesso!")
        } catch (Exception err){
            println("Erro ao adicionar Competência!")
        }


    }
}
