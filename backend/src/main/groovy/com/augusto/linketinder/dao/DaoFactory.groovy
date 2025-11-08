package com.augusto.linketinder.dao

import com.augusto.linketinder.dao.connectionProvider.ConnectionProvider
import com.augusto.linketinder.dao.connectionProvider.ConnectionProviderFactory


class DaoFactory {
    
    static CandidatoDAO getCandidatoDAO() {
        return new CandidatoDAO(ConnectionProviderFactory.getProvider())
    }

    static CompetenciaDAO getCompetenciaDAO() {
        return new CompetenciaDAO(ConnectionProviderFactory.getProvider())
    }

    static EmpresaDAO getEmpresaDAO() {
        return new EmpresaDAO(ConnectionProviderFactory.getProvider())
    }

    static VagaDAO getVagaDAO() {
        return new VagaDAO(ConnectionProviderFactory.getProvider())
    }

    // Test Constructors
    static CompetenciaDAO createCompetenciaDAO(ConnectionProvider provider) {
        return new CompetenciaDAO(provider)
    }

    static CandidatoDAO createCandidatoDAO(ConnectionProvider provider, CompetenciaDAO compDao) {
        return new CandidatoDAO(provider, compDao)
    }

    static VagaDAO createVagaDAO(ConnectionProvider provider, CompetenciaDAO compDao) {
        return new VagaDAO(provider, compDao)
    }

    static EmpresaDAO createEmpresaDAO(ConnectionProvider provider, CompetenciaDAO compDao, VagaDAO vagaDao) {
        return new EmpresaDAO(provider, compDao, vagaDao)
    }
}
