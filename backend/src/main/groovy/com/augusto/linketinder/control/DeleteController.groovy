package com.augusto.linketinder.control

import com.augusto.linketinder.DAO.DAO_Candidato
import com.augusto.linketinder.DAO.DAO_Competencia
import com.augusto.linketinder.DAO.DAO_Empresa
import com.augusto.linketinder.DAO.DAO_Vaga
import com.augusto.linketinder.model.Competencia
import com.augusto.linketinder.model.Vaga
import com.augusto.linketinder.model.pessoa.Empresa
import com.augusto.linketinder.model.pessoa.Candidato
import com.augusto.linketinder.service.InputService

import java.sql.SQLException

class DeleteController {

    private final DAO_Candidato candidatoDao
    private final DAO_Empresa empresaDao
    private final DAO_Vaga vagaDao
    private final DAO_Competencia competenciaDao
    private final InputService inputService

    DeleteController() {
        this(new DAO_Candidato(), new DAO_Empresa(), new DAO_Vaga(), new DAO_Competencia(), new InputService())
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
