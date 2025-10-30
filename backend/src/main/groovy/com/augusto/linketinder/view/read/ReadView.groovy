package com.augusto.linketinder.view.read

import com.augusto.linketinder.dao.DAO_Candidato
import com.augusto.linketinder.dao.DAO_Competencia
import com.augusto.linketinder.dao.DAO_Empresa
import com.augusto.linketinder.dao.DAO_Vaga
import com.augusto.linketinder.model.Competencia
import com.augusto.linketinder.model.Vaga
import com.augusto.linketinder.model.pessoa.Candidato
import com.augusto.linketinder.model.pessoa.Empresa

class ReadView {

    private final DAO_Candidato candidatoDao
    private final DAO_Empresa empresaDao
    private final DAO_Vaga vagaDao
    private final DAO_Competencia competenciaDao

    ReadView() {
        this(new DAO_Candidato(), new DAO_Empresa(), new DAO_Vaga(), new DAO_Competencia())
    }

    ReadView(DAO_Candidato candidatoDao,
             DAO_Empresa empresaDao,
             DAO_Vaga vagaDao,
             DAO_Competencia competenciaDao) {
        this.candidatoDao = candidatoDao
        this.empresaDao = empresaDao
        this.vagaDao = vagaDao
        this.competenciaDao = competenciaDao
    }

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
