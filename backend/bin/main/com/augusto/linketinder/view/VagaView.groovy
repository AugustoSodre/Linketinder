package com.augusto.linketinder.view

import com.augusto.linketinder.model.Vaga

class VagaView {

    void printMessage(String message){
        print message
    }

    void printlnMessage(String message){
        println message
    }

    void showReadVagas(List<Vaga> vagas){
        for (Vaga p in vagas) {
            println()
            println(p.toString())
            println()
        }
    }

    void showDeleteVaga(){
        println "Deletar Vaga!"
        println()
        print("Digite o ID da Vaga: ")
    }
}
