package com.augusto.linketinder.view.delete

import com.augusto.linketinder.control.DeleteController

class DeleteView {

    private final DeleteController deleteController

    DeleteView() {
        this(new DeleteController())
    }

    DeleteView(DeleteController deleteController) {
        this.deleteController = deleteController
    }

    void showDeleteCandidato(){
        println "Deletar Candidato!"
        println()
        print("Digite o ID do Candidato: ")
        deleteController.deleteCandidato()
    }

    void showDeleteEmpresa(){
        println "Deletar Empresa!"
        println()
        print("Digite o ID da Empresa: ")
        deleteController.deleteEmpresa()
    }

    void showDeleteVaga(){
        println "Deletar Empresa!"
        println()
        print("Digite o ID da Vaga: ")
        deleteController.deleteVaga()
    }

    void showDeleteCompetencia(){
        println "Deletar Competência!"
        println()
        print("Digite o ID da Competência: ")
        deleteController.deleteCompetencia()
    }
}
