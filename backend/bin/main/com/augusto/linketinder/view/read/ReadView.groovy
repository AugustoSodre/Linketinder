package com.augusto.linketinder.view.read

import com.augusto.linketinder.dao.CandidatoDAO
import com.augusto.linketinder.dao.CompetenciaDAO
import com.augusto.linketinder.dao.EmpresaDAO
import com.augusto.linketinder.dao.VagaDAO
import com.augusto.linketinder.model.Competencia
import com.augusto.linketinder.model.Vaga
import com.augusto.linketinder.model.pessoa.Candidato
import com.augusto.linketinder.model.pessoa.Empresa

class ReadView {

    private final CandidatoDAO candidatoDao
    private final EmpresaDAO empresaDao
    private final VagaDAO vagaDao
    private final CompetenciaDAO competenciaDao

    ReadView() {
        this(new CandidatoDAO(), new EmpresaDAO(), new VagaDAO(), new CompetenciaDAO())
    }

    ReadView(CandidatoDAO candidatoDao,
             EmpresaDAO empresaDao,
             VagaDAO vagaDao,
             CompetenciaDAO competenciaDao) {
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
