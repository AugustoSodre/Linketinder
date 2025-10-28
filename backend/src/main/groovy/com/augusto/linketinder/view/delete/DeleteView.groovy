package com.augusto.linketinder.view.delete

import com.augusto.linketinder.control.DeleteController

class DeleteView {

    void showDeleteCandidato(){
        println "Deletar Candidato!"
        println()
        print("Digite o ID do Candidato: ")
        new DeleteController().deleteCandidato()
    }

    void showDeleteEmpresa(){
        println "Deletar Empresa!"
        println()
        print("Digite o ID da Empresa: ")
        new DeleteController().deleteEmpresa()
    }

    void showDeleteVaga(){
        println "Deletar Empresa!"
        println()
        print("Digite o ID da Vaga: ")
        new DeleteController().deleteVaga()
    }

    void showDeleteCompetencia(){
        println "Deletar Competência!"
        println()
        print("Digite o ID da Competência: ")
        new DeleteController().deleteCompetencia()
    }
}
