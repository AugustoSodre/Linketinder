package com.augusto.linketinder.service

import com.augusto.linketinder.dao.CompetenciaDAO
import com.augusto.linketinder.dao.DaoFactory
import com.augusto.linketinder.model.Competencia

import java.sql.SQLException

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
        try{
            competenciaDao.updateName(id, novoNome)
        } catch (Exception e){
            throw new SQLException("Error: " + e.message)
        }
    }

    String listarRelacionamentos(String objeto) {
        try {
            return competenciaDao.listRelations(objeto)
        } catch (Exception e) {
            throw new SQLException("Error: " + e.message)
        }
    }

    void adicionarRelacionamento(String objeto, int idCompetencia, int idObjeto) {
        try{
            competenciaDao.addRelation(objeto, idCompetencia, idObjeto)
        } catch (Exception e){
            throw new SQLException("Error: " + e.message)
        }
    }

    void removerRelacionamento(String objeto, int idCompetencia, int idObjeto) {
        try{
            competenciaDao.removeRelation(objeto, idCompetencia, idObjeto)
        } catch (Exception e){
            throw new SQLException("Error: " + e.message)
        }
    }

    void deleteCompetencia(int id){
        try{
            competenciaDao.removeAllRelationsFromCompetencia(id)
            competenciaDao.delete(id)
        } catch (Exception e){
            throw new SQLException("Error: " + e.message)
        }
    }

}
