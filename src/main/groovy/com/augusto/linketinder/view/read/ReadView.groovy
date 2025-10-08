package com.augusto.linketinder.view.read

import com.augusto.linketinder.DAO.DAO
import com.augusto.linketinder.model.lista.ListaCandidatoEstatica
import com.augusto.linketinder.model.lista.ListaEmpresaEstatica
import com.augusto.linketinder.model.pessoa.Candidato
import com.augusto.linketinder.model.pessoa.Empresa

class ReadView {

    DAO dao = new DAO()

    void showCandidatos(){
        List<Candidato> listaCandidato = dao.listCandidatos()
        for (final Candidato p in listaCandidato) {
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
