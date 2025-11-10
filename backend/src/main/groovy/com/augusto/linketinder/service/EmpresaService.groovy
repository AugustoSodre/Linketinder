package com.augusto.linketinder.service

import com.augusto.linketinder.dao.EmpresaDAO
import com.augusto.linketinder.dao.DaoFactory
import com.augusto.linketinder.model.pessoa.Empresa

import java.sql.SQLException

class EmpresaService {
    private final EmpresaDAO empresaDao

    EmpresaService(EmpresaDAO empresaDao) {
        this.empresaDao = empresaDao
    }

    EmpresaService() {
        this(DaoFactory.getEmpresaDAO())
    }

    void createEmpresa(Empresa empresa){
        try{
            empresaDao.insert(empresa)
            println("Empresa adicionada com sucesso!")
        } catch (Exception e){
            println("Erro ao adicionar empresa! " + e.message)
        }
    }

    List<Empresa> getEmpresaList(){
        return empresaDao.listAll()
    }

    Map<String, String> getCamposEmpresa() {
        return [
                'nome'     : 'nome',
                'email'    : 'email',
                'estado'   : 'estado',
                'cep'      : 'cep',
                'país'     : 'pais',
                'CNPJ'     : 'cnpj',
                'descrição': 'descricao',
                'senha'    : 'senha'
        ]
    }

    void updateEmpresa(int id, String coluna, Object novoValor){
        try{
            empresaDao.updateField(id, coluna, novoValor)
        } catch (Exception e){
            throw new SQLException("Error: " + e.message)
        }
    }

    void deleteEmpresa(int id){
        try{
            empresaDao.delete(id)
        } catch (Exception e){
            throw new SQLException("Error: " + e.message)
        }
    }
}
