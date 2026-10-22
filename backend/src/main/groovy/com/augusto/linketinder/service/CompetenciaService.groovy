package com.augusto.linketinder.service

import com.augusto.linketinder.dao.CompetenciaDAO
import com.augusto.linketinder.dao.DaoFactory
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
}
