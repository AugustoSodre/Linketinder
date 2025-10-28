package com.augusto.linketinder.view.read

import com.augusto.linketinder.DAO.DAO_Candidato
import com.augusto.linketinder.DAO.DAO_Competencia
import com.augusto.linketinder.DAO.DAO_Empresa
import com.augusto.linketinder.DAO.DAO_Vaga
import com.augusto.linketinder.model.Competencia
import com.augusto.linketinder.model.Vaga
import com.augusto.linketinder.model.pessoa.Candidato
import com.augusto.linketinder.model.pessoa.Empresa

class ReadView {

    private final DAO_Candidato candidatoDao = new DAO_Candidato()
    private final DAO_Empresa empresaDao = new DAO_Empresa()
    private final DAO_Vaga vagaDao = new DAO_Vaga()
    private final DAO_Competencia competenciaDao = new DAO_Competencia()

    void showCandidatos(){
        for (final Candidato p in candidatoDao.listAll()) {
            println()
            println(p.toString())
            println()
        }
    }

    void showEmpresas() {
        for (final Empresa pj in empresaDao.listAll()) {
            println()
            println(pj.toString())
            println()
        }
    }

    void showVagas(){
        for (final Vaga v in vagaDao.listAll()) {
            println()
            println(v.toString())
            println()
        }
    }

    void showCompetencia(){
        for (Competencia comp in competenciaDao.listAll()){
            println()
            println(comp.toString())
            println()
        }
    }

}
