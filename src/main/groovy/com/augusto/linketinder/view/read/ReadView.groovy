package com.augusto.linketinder.view.read

import com.augusto.linketinder.model.lista.ListaCandidatoEstatica
import com.augusto.linketinder.model.lista.ListaEmpresaEstatica
import com.augusto.linketinder.model.pessoa.Candidato
import com.augusto.linketinder.model.pessoa.Empresa

class ReadView {

    void showFisico(){
        for (final Candidato p in ListaCandidatoEstatica.getLista()) {
            println()
            println(p.toString())
            println()
        }
    }

    void showJuridico() {
        for (final Empresa pj in ListaEmpresaEstatica.getLista()) {
            println()
            println(pj.toString())
            println()
        }
    }
}
