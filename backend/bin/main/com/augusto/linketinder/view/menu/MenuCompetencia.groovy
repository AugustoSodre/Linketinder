package com.augusto.linketinder.view.menu

import com.augusto.linketinder.service.InputService
import com.augusto.linketinder.view.create.CadastroCompView
import com.augusto.linketinder.view.delete.DeleteView
import com.augusto.linketinder.view.read.ReadView
import com.augusto.linketinder.view.update.UpdateView

class MenuCompetencia {

    private final InputService inputService
    private final CadastroCompView cadastroCompView
    private final ReadView readView
    private final UpdateView updateView
    private final DeleteView deleteView

    MenuCompetencia() {
        this(new InputService(), new CadastroCompView(), new ReadView(), new UpdateView(), new DeleteView())
    }

    MenuCompetencia(InputService inputService,
                    CadastroCompView cadastroCompView,
                    ReadView readView,
                    UpdateView updateView,
                    DeleteView deleteView) {
        this.inputService = inputService
        this.cadastroCompView = cadastroCompView
        this.readView = readView
        this.updateView = updateView
        this.deleteView = deleteView
    }

    void showMenuComp() {
        printOptions()
        int option = inputService.getIntInput()
        switch (option) {
            case 1:
                cadastroCompView.show()
                break
            case 2:
                readView.showCompetencia()
                break
            case 3:
                updateView.showUpdateCompetencia()
                break
            case 4:
                deleteView.showDeleteCompetencia()
                break
            case 0:
                break
            default:
                println "Input inválido"
        }
    }

    void printOptions(){
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
    }

}
