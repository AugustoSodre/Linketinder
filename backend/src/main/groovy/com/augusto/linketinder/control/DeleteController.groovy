package com.augusto.linketinder.control

import com.augusto.linketinder.dao.DAO_Candidato
import com.augusto.linketinder.dao.DAO_Competencia
import com.augusto.linketinder.dao.DAO_Empresa
import com.augusto.linketinder.dao.DAO_Vaga
import com.augusto.linketinder.dao.DataSource
import com.augusto.linketinder.service.InputService

import java.sql.SQLException

class DeleteController {

    private final DAO_Candidato candidatoDao
    private final DAO_Empresa empresaDao
    private final DAO_Vaga vagaDao
    private final DAO_Competencia competenciaDao
    private final InputService inputService

    DeleteController() {
        this(*createDefaultDependencies())
    }

    DeleteController(DAO_Candidato candidatoDao,
                     DAO_Empresa empresaDao,
                     DAO_Vaga vagaDao,
                     DAO_Competencia competenciaDao,
                     InputService inputService) {
        this.candidatoDao = candidatoDao
        this.empresaDao = empresaDao
        this.vagaDao = vagaDao
        this.competenciaDao = competenciaDao
        this.inputService = inputService
    }

    private static List createDefaultDependencies() {
        DataSource sharedDataSource = new DataSource()
        DAO_Competencia competenciaDao = new DAO_Competencia(sharedDataSource)
        DAO_Vaga vagaDao = new DAO_Vaga(sharedDataSource, competenciaDao)
        DAO_Candidato candidatoDao = new DAO_Candidato(sharedDataSource, competenciaDao)
        DAO_Empresa empresaDao = new DAO_Empresa(sharedDataSource, competenciaDao, vagaDao)
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
