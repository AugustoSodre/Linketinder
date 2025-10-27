package com.augusto.linketinder.view.menu


import com.augusto.linketinder.service.InputService
import com.augusto.linketinder.view.create.CadastroCompView
import com.augusto.linketinder.view.delete.DeleteView
import com.augusto.linketinder.view.read.ReadView
import com.augusto.linketinder.view.update.UpdateView

class MenuCompetencia {

    void showMenuComp() {
        println()
        println("-" * 25)
        println "Competências"
        println()
        println "Opções:"
        println "1. Cadastrar Competência"
        println "2. Mostrar Competências"
        println "3. Alterar Competência"
        println "4. Deletar Competência"
        println "0. Sair"
        println()
        println("-" * 25)

        //Chamar Controller para input
        switch (new InputService().getIntInput()) {
            case 1:
                new CadastroCompView().show()
                break
            case 2:
                new ReadView().showComp()
                break
            case 3:
                new UpdateView().showUpdateComp()
                break
            case 4:
                new DeleteView().showDeleteComp()
                break
            case 0:
                break
            default:
                println "Input inválido"
        }
    }

}
