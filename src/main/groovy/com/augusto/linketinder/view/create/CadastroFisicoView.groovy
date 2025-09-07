package com.augusto.linketinder.view.create

import com.augusto.linketinder.control.CadastroController
import com.augusto.linketinder.model.lista.EnumCompetencias

class CadastroFisicoView {

    CadastroController cadastroController = new CadastroController()

    void show(){
        println "Cadastro Fisico"

        println("Competências disponíveis:")
        int cont = 0
        for(c in EnumCompetencias.values()){
            println("${cont} " + c)
            cont++
        }

        cadastroController.getCompetenciasInput()
    }
}
