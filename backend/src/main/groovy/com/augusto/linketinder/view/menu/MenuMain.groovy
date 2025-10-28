package com.augusto.linketinder.view.menu

import com.augusto.linketinder.service.InputService

class MenuMain {

    boolean showMainMenu(){
        printOptions()
        int option = new InputService().getIntInput()
        switch (option){
            case 1:
                limpaTela()
                new MenuEmpresa().showMenuEmpresa()
                break
            case 2:
                limpaTela()
                new MenuCandidato().showMenuCandidato()
                break
            case 3:
                limpaTela()
                new MenuVaga().showMenuVaga()
                break
            case 4:
                limpaTela()
                new MenuCompetencia().showMenuComp()
                break
            case 0:
                return true
            default:
                print("Input inválido!")
        }

        return false
    }

    void printOptions(){
        limpaTela()
        println()
        println("-" * 25)
        println "Linketinder!"
        println()
        println "Opções:"
        println "1. Gerenciar Empresas"
        println "2. Gerenciar Candidatos"
        println "3. Gerenciar Vagas"
        println "4. Gerenciar Competências"
        println "0. Sair"
        println()
        println("-" * 25)
    }

    void limpaTela(){
        for (i in 0..<50) {
            println()
        }
    }
}

