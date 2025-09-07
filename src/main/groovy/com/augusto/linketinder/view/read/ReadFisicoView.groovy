package com.augusto.linketinder.view.read

import com.augusto.linketinder.model.lista.ListaFisicaEstatica
import com.augusto.linketinder.model.pessoa.PessoaFisica

class ReadFisicoView {

    void show(){

        for (final PessoaFisica p in ListaFisicaEstatica.getLista()) {
            println()
            println(p.toString())
            println()
        }

    }
}
