package com.augusto.linketinder.view.delete

import com.augusto.linketinder.control.DeleteController
import com.augusto.linketinder.model.pessoa.PessoaFisica
import com.augusto.linketinder.model.pessoa.PessoaJuridica

class DeleteView {

    void showDeleteFisico(){
        println "Deletar Candidato!"
        println()
        print("Digite o ID do Candidato: ")
        new DeleteController().delete(new PessoaFisica())
    }

    void showDeleteJuridico(){
        println "Deletar Empresa!"
        println()
        print("Digite o ID da Empresa: ")
        new DeleteController().delete(new PessoaJuridica())
    }
}
