package com.augusto.linketinder.view.update

import com.augusto.linketinder.DAO.DAO
import com.augusto.linketinder.control.UpdateController

class UpdateView {

    UpdateController updateController = new UpdateController()
    DAO dao = new DAO()

    void showUpdateCandidato(){
        println "Atualização de Candidato"
        println()

        List lista = dao.listCandidatos()
        if (!lista || lista.size() == 0) {
            println("Nenhum candidato cadastrado.")
            return
        }

        updateController.updateCandidato()
    }

    void showUpdateEmpresa(){
        println "Atualização de Empresa"
        println()

        List lista = dao.listEmpresas()
        if (!lista || lista.size() == 0) {
            println("Nenhuma empresa cadastrada.")
            return
        }

        updateController.updateEmpresa()
    }

}
