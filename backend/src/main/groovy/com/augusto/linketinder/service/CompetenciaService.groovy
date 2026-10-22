package com.augusto.linketinder.service

import com.augusto.linketinder.dao.CompetenciaDAO
import com.augusto.linketinder.dao.DaoFactory
import com.augusto.linketinder.dao.RelationType
import com.augusto.linketinder.model.Competencia

class CompetenciaService {
    private final CompetenciaDAO competenciaDao

    CompetenciaService(CompetenciaDAO competenciaDao) {
        this.competenciaDao = competenciaDao
    }

    CompetenciaService() {
        this(DaoFactory.getCompetenciaDAO())
    }

    List<Competencia> getCompetenciaList(){
        return competenciaDao.listAll()
    }

    boolean existeNome(String nome) {
        return competenciaDao.listAll().any { it.nome.equalsIgnoreCase(nome) }
    }

    Competencia criar(String nome) {
        def competencia = new Competencia(nome)
        competenciaDao.insert(competencia)
        return competencia
    }

    boolean existem(List<Integer> ids) {
        return competenciaDao.listAll()*.id.containsAll(ids)
    }

    void substituir(RelationType rel, int idObjeto, List<Competencia> competencias) {
        competenciaDao.substituir(rel, idObjeto, competencias)
    }
}
