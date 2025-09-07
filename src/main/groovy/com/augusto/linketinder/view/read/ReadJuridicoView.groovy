package com.augusto.linketinder.view.read

import com.augusto.linketinder.model.lista.ListaJuridicaEstatica
import com.augusto.linketinder.model.pessoa.PessoaJuridica

class ReadJuridicoView {

    void show(){

        for (final PessoaJuridica pj in ListaJuridicaEstatica.getLista()) {
            println()
            println(pj.toString())
            println()
        }
    }
}
