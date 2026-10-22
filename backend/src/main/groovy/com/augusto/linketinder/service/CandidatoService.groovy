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
        println("Candidato adicionado com sucesso!")
    }

    Candidato getCandidatoById(int id){
        return candidatoDao.findById(id)
    }

    List<Candidato> getCandidatoList(){
        return candidatoDao.listAll()
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
        if (!(coluna in getCamposCandidato().values())) {
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
