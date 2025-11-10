package com.augusto.linketinder.service

import com.augusto.linketinder.dao.CandidatoDAO
import com.augusto.linketinder.dao.DaoFactory
import com.augusto.linketinder.model.pessoa.Candidato

import java.sql.SQLException

class CandidatoService {
    private final CandidatoDAO candidatoDao

    CandidatoService(CandidatoDAO candidatoDao) {
        this.candidatoDao = candidatoDao
    }

    CandidatoService() {
        this(DaoFactory.getCandidatoDAO())
    }

    void createCandidato(Candidato candidato){
        try{
            candidatoDao.insert(candidato)
            println("Candidato adicionado com sucesso!")
        } catch (Exception e){
            println("Erro ao adicionar candidato! " + e.message)
        }
    }

    List<Candidato> getCandidatoList(){
        try{
            return candidatoDao.listAll()
        } catch (Exception e){
            println("Erro ao listar candidatos: " + e.message)
            return []
        }

    }

    Map<String, String> getCamposCandidato() {
        return [
                'nome'     : 'nome',
                'email'    : 'email',
                'estado'   : 'estado',
                'cep'      : 'cep',
                'idade'    : 'idade',
                'cpf'      : 'cpf',
                'descrição': 'descricao',
                'senha'    : 'senha'
        ]
    }

    void updateCandidato(int id, String coluna, Object novoValor){
        try{
            candidatoDao.updateField(id, coluna, novoValor)
        } catch (Exception e){
            throw new SQLException("Error: " + e.message)
        }
    }

    void deleteCandidato(int id){
        try{
            candidatoDao.delete(id)
        } catch (Exception e){
            throw new SQLException("Error: " + e.message)
        }
    }
}
