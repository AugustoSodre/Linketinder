package com.augusto.linketinder.view

import com.augusto.linketinder.model.Competencia

class CompetenciaView {

    void printMessage(String message){
        print message
    }

    void printlnMessage(String message){
        println message
    }

    void showReadCompetencias(List<Competencia> competencias){
        for (Competencia p in competencias) {
            println()
            println(p.toString())
            println()
        }
    }

    void showDeleteCompetencia(){
        println "Deletar Competência!"
        println()
        print("Digite o ID da Competência: ")
    }
}
