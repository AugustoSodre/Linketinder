package com.augusto.linketinder.service

import com.augusto.linketinder.dao.CandidatoDAO
import com.augusto.linketinder.dao.DaoFactory
import com.augusto.linketinder.model.pessoa.Candidato

class CandidatoService {
    private final CandidatoDAO candidatoDao

    CandidatoService(CandidatoDAO candidatoDao) {
        this.candidatoDao = candidatoDao
    }

    CandidatoService() {
        this(DaoFactory.getCandidatoDAO())
    }

    void createCandidato(Candidato candidato){
        candidato.senha = AuthService.hash(candidato.senha)
        candidatoDao.insert(candidato)
    }

    Candidato getCandidatoById(int id){
        return candidatoDao.findById(id)
    }

    List<Candidato> getCandidatoList(){
        return candidatoDao.listAll()
    }

    List<String> getCamposCandidato() {
        return ['nome', 'email', 'estado', 'cep', 'idade', 'cpf', 'descricao', 'senha']
    }

    void updateCandidato(int id, String coluna, Object novoValor){
        if (!(coluna in getCamposCandidato())) {
            throw new IllegalArgumentException("Campo inválido: " + coluna)
        }
        if (coluna == 'senha') {
            novoValor = AuthService.hash(novoValor as String)
        }
        candidatoDao.updateField(id, coluna, novoValor)
    }

    void deleteCandidato(int id){
        candidatoDao.delete(id)
    }
}
