package com.augusto.linketinder.view

import com.augusto.linketinder.model.pessoa.Empresa

class EmpresaView {

    void printMessage(String message){
        print message
    }

    void printlnMessage(String message){
        println message
    }

    void showReadEmpresas(List<Empresa> empresas){
        for (Empresa p in empresas) {
            println()
            println(p.toString())
            println()
        }
    }

    void showDeleteEmpresa(){
        println "Deletar Empresa!"
        println()
        print("Digite o ID da Empresa: ")
    }
}
