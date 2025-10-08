package com.augusto.linketinder

import com.augusto.linketinder.DAO.DAO
import com.augusto.linketinder.DAO.DAO_Helper
import com.augusto.linketinder.model.Competencia
import com.augusto.linketinder.model.Vaga
import com.augusto.linketinder.model.pessoa.Candidato

class Sandbox {

    static void main(String[] args){
        DAO dao = new DAO()
        Competencia comp1 = new Competencia(7, "TypeScript")
//        dao.insert(comp1)
        Candidato candidato = new Candidato(123, "jose", "a@gmail.com", "MG", "70674404", "09876543525", 21, "Soy cool", [comp1], "f")
//        Vaga vaga = new Vaga(1, "Dev Novo", "Teste", "Uberlandia", "MG")
        //dao.insert(vaga)
//        dao.insert(candidato)

//        DAO_Helper daoHelper = new DAO_Helper()
//        println(daoHelper.getListaCompCandidato(3))

//        println(dao.listCompetencia())
        dao.deleteCompetencia(7)
    }
}
