package com.augusto.linketinder.service

import com.augusto.linketinder.dao.VagaDAO
import com.augusto.linketinder.dao.DaoFactory
import com.augusto.linketinder.model.Vaga

import java.sql.SQLException

class VagaService {
    private final VagaDAO vagaDao

    VagaService(VagaDAO vagaDao) {
        this.vagaDao = vagaDao
    }

    VagaService() {
        this(DaoFactory.getVagaDAO())
    }

    void createVaga(Vaga vaga){
        try{
            vagaDao.insert(vaga)
            println("Vaga adicionada com sucesso!")
        } catch (Exception e){
            println("Erro ao adicionar vaga! " + e.message)
        }
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
        try{
            vagaDao.updateField(id, coluna, novoValor)
        } catch (Exception e){
            throw new SQLException("Error: " + e.message)
        }
    }

    void deleteVaga(int id){
        try{
            vagaDao.delete(id)
        } catch (Exception e){
            throw new SQLException("Error: " + e.message)
        }
    }
}
 
