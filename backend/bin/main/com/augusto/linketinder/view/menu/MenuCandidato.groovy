package com.augusto.linketinder.view.menu

import com.augusto.linketinder.service.InputService
import com.augusto.linketinder.view.create.CadastroCandidatoView
import com.augusto.linketinder.view.delete.DeleteView
import com.augusto.linketinder.view.read.ReadView
import com.augusto.linketinder.view.update.UpdateView

class MenuCandidato {

    private final InputService inputService
    private final CadastroCandidatoView cadastroCandidatoView
    private final ReadView readView
    private final UpdateView updateView
    private final DeleteView deleteView

    MenuCandidato() {
        this(new InputService(), new CadastroCandidatoView(), new ReadView(), new UpdateView(), new DeleteView())
    }

    MenuCandidato(InputService inputService,
                  CadastroCandidatoView cadastroCandidatoView,
                  ReadView readView,
                  UpdateView updateView,
                  DeleteView deleteView) {
        this.inputService = inputService
        this.cadastroCandidatoView = cadastroCandidatoView
        this.readView = readView
        this.updateView = updateView
        this.deleteView = deleteView
    }

    void showMenuCandidato(){
        printOptions()
        int option = inputService.getIntInput()
        switch(option){
            case 1:
                cadastroCandidatoView.show()
                break
            case 2:
                readView.showCandidatos()
                break
            case 3:
                updateView.showUpdateCandidato()
                break
            case 4:
                deleteView.showDeleteCandidato()
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
    }
}
