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

    private final DAO_Candidato candidatoDao = new DAO_Candidato()
    private final DAO_Empresa empresaDao = new DAO_Empresa()
    private final DAO_Vaga vagaDao = new DAO_Vaga()
    private final DAO_Competencia competenciaDao = new DAO_Competencia()
    private final InputService inputService = new InputService()

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
