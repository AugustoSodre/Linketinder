package com.augusto.linketinder.view.read

import com.augusto.linketinder.model.lista.ListaFisicaEstatica
import com.augusto.linketinder.model.lista.ListaJuridicaEstatica
import com.augusto.linketinder.model.pessoa.PessoaFisica
import com.augusto.linketinder.model.pessoa.PessoaJuridica

class ReadView {

    void showFisico(){
        for (final PessoaFisica p in ListaFisicaEstatica.getLista()) {
            println()
            println(p.toString())
            println()
        }
    }

    void showJuridico() {
        for (final PessoaJuridica pj in ListaJuridicaEstatica.getLista()) {
            println()
            println(pj.toString())
            println()
        }
    }
}
