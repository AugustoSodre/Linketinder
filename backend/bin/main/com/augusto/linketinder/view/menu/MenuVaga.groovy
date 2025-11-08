package com.augusto.linketinder.view.menu

import com.augusto.linketinder.service.InputService
import com.augusto.linketinder.view.create.CadastroVagaView
import com.augusto.linketinder.view.delete.DeleteView
import com.augusto.linketinder.view.read.ReadView
import com.augusto.linketinder.view.update.UpdateView

class MenuVaga {

    private final InputService inputService
    private final CadastroVagaView cadastroVagaView
    private final ReadView readView
    private final UpdateView updateView
    private final DeleteView deleteView

    MenuVaga() {
        this(new InputService(), new CadastroVagaView(), new ReadView(), new UpdateView(), new DeleteView())
    }

    MenuVaga(InputService inputService,
             CadastroVagaView cadastroVagaView,
             ReadView readView,
             UpdateView updateView,
             DeleteView deleteView) {
        this.inputService = inputService
        this.cadastroVagaView = cadastroVagaView
        this.readView = readView
        this.updateView = updateView
        this.deleteView = deleteView
    }

    void showMenuVaga() {
        printOptions()
        int option = inputService.getIntInput()
        switch (option) {
            case 1:
                cadastroVagaView.show()
                break
            case 2:
                readView.showVagas()
                break
            case 3:
                updateView.showUpdateVaga()
                break
            case 4:
                deleteView.showDeleteVaga()
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
        println "Vagas"
        println()
        println "Opções:"
        println "1. Cadastrar Vaga"
        println "2. Mostrar Vagas"
        println "3. Alterar Vaga"
        println "4. Deletar Vaga"
        println "0. Sair"
        println()
        println("-" * 25)
    }
}