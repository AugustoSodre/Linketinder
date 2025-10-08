package com.augusto.linketinder.view.menu

import com.augusto.linketinder.control.MenuController
import com.augusto.linketinder.view.create.CadastroCandidatoView
import com.augusto.linketinder.view.create.CadastroVagaView
import com.augusto.linketinder.view.delete.DeleteView
import com.augusto.linketinder.view.read.ReadView
import com.augusto.linketinder.view.update.UpdateView

class MenuVaga {

    void showMenuVaga() {
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

        //Chamar Controller para input
        switch (new MenuController().getIntInput()) {
            case 1:
                new CadastroVagaView().show()
                break
            case 2:
                new ReadView().showVagas()
                break
            case 3:
                new UpdateView().showUpdateVaga()
                break
            case 4:
                new DeleteView().showDeleteVaga()
                break
            case 0:
                break
            default:
                println "Input inválido"
        }
    }
}