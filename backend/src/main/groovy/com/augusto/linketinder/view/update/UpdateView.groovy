package com.augusto.linketinder.view.update

import com.augusto.linketinder.DAO.DAO_Candidato
import com.augusto.linketinder.DAO.DAO_Competencia
import com.augusto.linketinder.DAO.DAO_Empresa
import com.augusto.linketinder.DAO.DAO_Vaga
import com.augusto.linketinder.control.update.UpdateController

class UpdateView {

    UpdateController updateController = new UpdateController()
    private final DAO_Candidato candidatoDao = new DAO_Candidato()
    private final DAO_Empresa empresaDao = new DAO_Empresa()
    private final DAO_Vaga vagaDao = new DAO_Vaga()
    private final DAO_Competencia competenciaDao = new DAO_Competencia()

    void showUpdateCandidato(){
        println "Atualização de Candidato"
        println()

        List lista = candidatoDao.listAll()
        if (!lista || lista.size() == 0) {
            println("Nenhum candidato cadastrado.")
            return
        }

        updateController.updateCandidato()
    }

    void showUpdateEmpresa(){
        println "Atualização de Empresa"
        println()

        List lista = empresaDao.listAll()
        if (!lista || lista.size() == 0) {
            println("Nenhuma empresa cadastrada.")
            return
        }

        updateController.updateEmpresa()
    }

    void showUpdateVaga(){
        println "Atualização de Vaga"
        println()

        List lista = vagaDao.listAll()

        if (!lista || lista.size() == 0) {
            println("Nenhuma vaga cadastrada.")
            return
        }

        updateController.updateVaga()
    }

    void showUpdateComp(){
        println "Atualização de Competência"
        println()

        List lista = competenciaDao.listAll()

        if (!lista || lista.size() == 0) {
            println("Nenhuma competência cadastrada.")
            return
        }

        updateController.updateComp()
    }



}
