package com.augusto.linketinder.view.read

import com.augusto.linketinder.DAO.DAO
import com.augusto.linketinder.model.lista.ListaCandidatoEstatica
import com.augusto.linketinder.model.lista.ListaEmpresaEstatica
import com.augusto.linketinder.model.pessoa.Candidato
import com.augusto.linketinder.model.pessoa.Empresa

class ReadView {

    DAO dao = new DAO()

    void showCandidatos(){
        for (final Candidato p in dao.listCandidatos()) {
            println()
            println(p.toString())
            println()
        }
    }

    void showEmpresas() {
        for (final Empresa pj in dao.listEmpresas()) {
            println()
            println(pj.toString())
            println()
        }
    }
}
