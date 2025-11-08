package com.augusto.linketinder.view.helper


import com.augusto.linketinder.model.Competencia

class ViewHelper {

    void printAllAvailableCompetencias(List<Competencia> listaComp){
        int cont = 1
        for(c in listaComp){
            println("${cont} " + c.nome)
            cont++
        }
    }
}
