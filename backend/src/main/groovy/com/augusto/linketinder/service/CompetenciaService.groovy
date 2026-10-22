package com.augusto.linketinder.service

import com.augusto.linketinder.dao.CompetenciaDAO
import com.augusto.linketinder.dao.DaoFactory
import com.augusto.linketinder.model.Competencia

class CompetenciaService {
    private final CompetenciaDAO competenciaDao

    CompetenciaService(CompetenciaDAO competenciaDao) {
        this.competenciaDao = competenciaDao
    }

    CompetenciaService() {
        this(DaoFactory.getCompetenciaDAO())
    }

    void createCompetencia(Competencia competencia){
        try{
            competenciaDao.insert(competencia)
            println("Competência adicionada com sucesso!")
        } catch (Exception e){
            println("Erro ao adicionar competência! " + e.message)
        }
    }

    List<Competencia> getCompetenciaList(){
        return competenciaDao.listAll()
    }

    void updateCompetenciaNome(int id, String novoNome){
        competenciaDao.updateName(id, novoNome)
    }

    String listarRelacionamentos(String objeto) {
        return competenciaDao.listRelations(objeto)
    }

    void adicionarRelacionamento(String objeto, int idCompetencia, int idObjeto) {
        competenciaDao.addRelation(objeto, idCompetencia, idObjeto)
    }

    void removerRelacionamento(String objeto, int idCompetencia, int idObjeto) {
        competenciaDao.removeRelation(objeto, idCompetencia, idObjeto)
    }

    void deleteCompetencia(int id){
        competenciaDao.removeAllRelationsFromCompetencia(id)
        competenciaDao.delete(id)
    }

}
