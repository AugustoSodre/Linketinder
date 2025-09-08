package com.augusto.linketinder.view.menu

import com.augusto.linketinder.control.MenuController
import com.augusto.linketinder.view.create.CadastroFisicoView
import com.augusto.linketinder.view.delete.DeleteView

import com.augusto.linketinder.view.read.ReadView
import com.augusto.linketinder.view.update.UpdateFisicoView

class MenuFisico {

    void showMenuFisico(){
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

        //Chamar Controller para input
        switch(new MenuController().getIntInput()){
            case 1:
                new CadastroFisicoView().show()
                break
            case 2:
                new ReadView().showFisico()
                break
            case 3:
                new UpdateFisicoView().show()
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
