package com.augusto.linketinder.view.menu

import com.augusto.linketinder.control.MenuController
import com.augusto.linketinder.view.create.CadastroCandidatoView
import com.augusto.linketinder.view.delete.DeleteView

import com.augusto.linketinder.view.read.ReadView
import com.augusto.linketinder.view.update.UpdateCandidatoView

class MenuCandidato {

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
                new CadastroCandidatoView().show()
                break
            case 2:
                new ReadView().showFisico()
                break
            case 3:
                new UpdateCandidatoView().show()
                break
            case 4:
                new DeleteView().showDeleteFisico()
                break
            case 0:
                break
            default:
                println "Input inválido"
        }
    }
}
