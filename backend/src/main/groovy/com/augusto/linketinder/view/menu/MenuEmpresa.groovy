package com.augusto.linketinder.view.menu

import com.augusto.linketinder.service.InputService
import com.augusto.linketinder.view.create.CadastroEmpresaView
import com.augusto.linketinder.view.delete.DeleteView

import com.augusto.linketinder.view.read.ReadView
import com.augusto.linketinder.view.update.UpdateView

class MenuEmpresa {

    void showMenuEmpresa(){
        printOptions()
        switch(new InputService().getIntInput()){
            case 1:
                new CadastroEmpresaView().show()
                break
            case 2:
                new ReadView().showEmpresas()
                break
            case 3:
                new UpdateView().showUpdateEmpresa()
                break
            case 4:
                new DeleteView().showDeleteEmpresa()
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
