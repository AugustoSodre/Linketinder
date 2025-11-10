package com.augusto.linketinder.view

import com.augusto.linketinder.model.pessoa.Candidato

class CandidatoView {

    void printMessage(String message){
        print message
    }

    void printlnMessage(String message){
        println message
    }

    void showReadCandidatos(List<Candidato> candidatos){
        for (Candidato p in candidatos) {
            println()
            println(p.toString())
            println()
        }
    }

    void showDeleteCandidato(){
        println "Deletar Candidato!"
        println()
        print("Digite o ID do Candidato: ")
    }
}
