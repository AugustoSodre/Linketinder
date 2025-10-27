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

class DeleteController {

    private final DAO_Candidato candidatoDao = new DAO_Candidato()
    private final DAO_Empresa empresaDao = new DAO_Empresa()
    private final DAO_Vaga vagaDao = new DAO_Vaga()
    private final DAO_Competencia competenciaDao = new DAO_Competencia()
    private final InputService inputService = new InputService()

    void delete(def objeto){
        int id = inputService.getIntInput()

        try {
            if(objeto instanceof Candidato){
                candidatoDao.delete(id)

            } else if(objeto instanceof Empresa){
                empresaDao.delete(id)

            } else if(objeto instanceof Vaga){
                vagaDao.delete(id)

            } else if(objeto instanceof Competencia){
                competenciaDao.removeAllRelationsFromCompetencia(id)
                competenciaDao.delete(id)

            }

            println("Operação concluída.")
        } catch (Exception e) {
            println("Erro ao realizar exclusão: ${e.message}")
        }
    }

}
