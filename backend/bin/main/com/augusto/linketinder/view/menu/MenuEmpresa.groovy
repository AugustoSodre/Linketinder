package com.augusto.linketinder.view.menu

import com.augusto.linketinder.service.InputService
import com.augusto.linketinder.view.create.CadastroEmpresaView
import com.augusto.linketinder.view.delete.DeleteView
import com.augusto.linketinder.view.read.ReadView
import com.augusto.linketinder.view.update.UpdateView

class MenuEmpresa {

    private final InputService inputService
    private final CadastroEmpresaView cadastroEmpresaView
    private final ReadView readView
    private final UpdateView updateView
    private final DeleteView deleteView

    MenuEmpresa() {
        this(new InputService(), new CadastroEmpresaView(), new ReadView(), new UpdateView(), new DeleteView())
    }

    MenuEmpresa(InputService inputService,
                CadastroEmpresaView cadastroEmpresaView,
                ReadView readView,
                UpdateView updateView,
                DeleteView deleteView) {
        this.inputService = inputService
        this.cadastroEmpresaView = cadastroEmpresaView
        this.readView = readView
        this.updateView = updateView
        this.deleteView = deleteView
    }

    void showMenuEmpresa(){
        printOptions()
        switch(inputService.getIntInput()){
            case 1:
                cadastroEmpresaView.show()
                break
            case 2:
                readView.showEmpresas()
                break
            case 3:
                updateView.showUpdateEmpresa()
                break
            case 4:
                deleteView.showDeleteEmpresa()
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
        println "Empresa"
        println()
        println "Opções:"
        println "1. Cadastrar Empresa"
        println "2. Mostrar Empresas"
        println "3. Alterar Empresa"
        println "4. Deletar Empresa"
        println "0. Sair"
        println()
        println("-" * 25)
    }
}
