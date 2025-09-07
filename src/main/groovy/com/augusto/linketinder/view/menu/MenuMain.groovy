package com.augusto.linketinder.view.menu

import com.augusto.linketinder.control.MenuController


class MenuMain {

    boolean showMainMenu(){
        limpaTela()
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


        switch (new MenuController().getIntInput()){
            case 1:
                limpaTela()
                new MenuJuridico().showMenuJuridico()
                break
            case 2:
                limpaTela()
                new MenuFisico().showMenuFisico()
                break
            case 0:
                return true
            default:
                print("Input inválido!")
        }

        return false
    }

    void limpaTela(){
        for (i in 0..<50) {
            println()
        }
    }
}

