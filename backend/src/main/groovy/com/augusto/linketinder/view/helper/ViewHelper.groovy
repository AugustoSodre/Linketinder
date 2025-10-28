package com.augusto.linketinder.view.helper

import com.augusto.linketinder.DAO.DAO_Competencia
import com.augusto.linketinder.model.Competencia

class ViewHelper {
    private final DAO_Competencia competenciaDao = new DAO_Competencia()

    void printAllAvailableCompetencias(List<Competencia> listaComp){
        int cont = 1
        for(c in listaComp){
            println("${cont} " + c.nome)
            cont++
        }
    }
}
