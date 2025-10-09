package com.augusto.linketinder.view.delete

import com.augusto.linketinder.control.DeleteController
import com.augusto.linketinder.model.Competencia
import com.augusto.linketinder.model.Vaga
import com.augusto.linketinder.model.pessoa.Candidato
import com.augusto.linketinder.model.pessoa.Empresa

class DeleteView {

    void showDeleteCandidato(){
        println "Deletar Candidato!"
        println()
        print("Digite o ID do Candidato: ")
        new DeleteController().delete(new Candidato())
    }

    void showDeleteEmpresa(){
        println "Deletar Empresa!"
        println()
        print("Digite o ID da Empresa: ")
        new DeleteController().delete(new Empresa())
    }

    void showDeleteVaga(){
        println "Deletar Empresa!"
        println()
        print("Digite o ID da Vaga: ")
        new DeleteController().delete(new Vaga())
    }

    void showDeleteComp(){
        println "Deletar Competência!"
        println()
        print("Digite o ID da Competência: ")
        new DeleteController().delete(new Competencia())
    }
}
