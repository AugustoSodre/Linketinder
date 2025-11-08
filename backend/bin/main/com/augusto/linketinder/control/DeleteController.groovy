package com.augusto.linketinder.control

import com.augusto.linketinder.dao.CandidatoDAO
import com.augusto.linketinder.dao.CompetenciaDAO
import com.augusto.linketinder.dao.EmpresaDAO
import com.augusto.linketinder.dao.VagaDAO
import com.augusto.linketinder.dao.connectionProvider.ConnectionProvider
import com.augusto.linketinder.dao.connectionProvider.ConnectionProviderFactory
import com.augusto.linketinder.service.InputService

import java.sql.SQLException

class DeleteController {

    private final CandidatoDAO candidatoDao
    private final EmpresaDAO empresaDao
    private final VagaDAO vagaDao
    private final CompetenciaDAO competenciaDao
    private final InputService inputService

    DeleteController() {
        this(*createDefaultDependencies())
    }

    DeleteController(CandidatoDAO candidatoDao,
                     EmpresaDAO empresaDao,
                     VagaDAO vagaDao,
                     CompetenciaDAO competenciaDao,
                     InputService inputService) {
        this.candidatoDao = candidatoDao
        this.empresaDao = empresaDao
        this.vagaDao = vagaDao
        this.competenciaDao = competenciaDao
        this.inputService = inputService
    }

    private static List createDefaultDependencies() {
        ConnectionProvider sharedProvider = ConnectionProviderFactory.getProvider()
        CompetenciaDAO competenciaDao = new CompetenciaDAO(sharedProvider)
        VagaDAO vagaDao = new VagaDAO(sharedProvider, competenciaDao)
        CandidatoDAO candidatoDao = new CandidatoDAO(sharedProvider, competenciaDao)
        EmpresaDAO empresaDao = new EmpresaDAO(sharedProvider, competenciaDao, vagaDao)
        InputService inputService = new InputService()
        return [candidatoDao, empresaDao, vagaDao, competenciaDao, inputService]
    }

    void deleteCandidato(){
        int id = inputService.getIntInput()
        try{
            candidatoDao.delete(id)
        } catch (Exception e){
            throw new SQLException("Error: " + e.message)
        }
    }

    void deleteEmpresa(){
        int id = inputService.getIntInput()
        try{
            empresaDao.delete(id)
        } catch (Exception e){
            throw new SQLException("Error: " + e.message)
        }
    }

    void deleteVaga(){
        int id = inputService.getIntInput()
        try{
            vagaDao.delete(id)
        } catch (Exception e){
            throw new SQLException("Error: " + e.message)
        }
    }

    void deleteCompetencia(){
        int id = inputService.getIntInput()
        try{
            competenciaDao.removeAllRelationsFromCompetencia(id)
            competenciaDao.delete(id)
        } catch (Exception e){
            throw new SQLException("Error: " + e.message)
        }
    }

}
