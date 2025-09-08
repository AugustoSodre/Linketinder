package com.augusto.linketinder.view.menu

import com.augusto.linketinder.control.MenuController
import com.augusto.linketinder.view.create.CadastroJuridicoView
import com.augusto.linketinder.view.delete.DeleteView

import com.augusto.linketinder.view.read.ReadView
import com.augusto.linketinder.view.update.UpdateJuridicoView

class MenuJuridico {

    void showMenuJuridico(){
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

        //Chamar Controller para input
        switch(new MenuController().getIntInput()){
            case 1:
                new CadastroJuridicoView().show()
                break
            case 2:
                new ReadView().showJuridico()
                break
            case 3:
                new UpdateJuridicoView().show()
                break
            case 4:
                new DeleteView().show()
                break
            case 0:
                break
            default:
                println "Input inválido"
        }

    }
}
