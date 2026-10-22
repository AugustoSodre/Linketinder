package com.augusto.linketinder.service

import com.augusto.linketinder.dao.VagaDAO
import com.augusto.linketinder.dao.DaoFactory
import com.augusto.linketinder.model.Vaga

class VagaService {
    private final VagaDAO vagaDao

    VagaService(VagaDAO vagaDao) {
        this.vagaDao = vagaDao
    }

    VagaService() {
        this(DaoFactory.getVagaDAO())
    }

    void createVaga(Vaga vaga){
        vagaDao.insert(vaga)
        println("Vaga adicionada com sucesso!")
    }

    Vaga getVagaById(int id){
        return vagaDao.findById(id)
    }

    List<Vaga> getVagaList(){
        return vagaDao.listAll()
    }

    Map<String, String> getCamposVaga() {
        return [
                'ID da empresa': 'id_empresa',
                'nome'        : 'nome',
                'descrição'   : 'descricao',
                'cidade'      : 'cidade',
                'estado'      : 'estado'
        ]
    }

    void updateVaga(int id, String coluna, Object novoValor){
        if (!(coluna in getCamposVaga().values())) {
            throw new IllegalArgumentException("Campo inválido: " + coluna)
        }
        vagaDao.updateField(id, coluna, novoValor)
    }

    void deleteVaga(int id){
        vagaDao.delete(id)
    }
}
 
