package com.augusto.linketinder.view.menu

class MenuMain {

    void showMainMenu(){
        println()
        println("-" * 25)
        println "Linketinder!"
        println()
        println "Opções:"
        println "1. Gerenciar Empregadores"
        println "2. Gerenciar Candidatos"
        println "0. Sair"
        println()
        println("-" * 25)
        print "Digite sua opção: "
    }

    void showFisicoMenu(){
        println()
        println("-" * 25)
        println "Candidatos"
        println()
        println "Opções:"
        println "1. Cadastrar Candidato"
        println "2. Mostrar Candidatos"
        println "3. Alterar Candidato"
        println "4. Deletar Candidato"
        println "0. Sair"
        println()
        println("-" * 25)
        print "Digite sua opção: "

        //Chamar Controller pro input
    }

    void showJuridicoMenu(){
        println()
        println("-" * 25)
        println "Empregadores"
        println()
        println "Opções:"
        println "1. Cadastrar Empregador"
        println "2. Mostrar Empregadores"
        println "3. Alterar Empregador"
        println "4. Deletar Empregador"
        println "0. Sair"
        println()
        println("-" * 25)
        print "Digite sua opção: "
    }
}

