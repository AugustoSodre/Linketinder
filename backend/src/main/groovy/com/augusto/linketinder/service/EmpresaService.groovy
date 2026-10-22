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
        empresa.senha = AuthService.hash(empresa.senha)
        empresaDao.insert(empresa)
        println("Empresa adicionada com sucesso!")
    }

    Empresa getEmpresaById(int id){
        return empresaDao.findById(id)
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
        if (!(coluna in getCamposEmpresa().values())) {
            throw new IllegalArgumentException("Campo inválido: " + coluna)
        }
        if (coluna == 'senha') {
            novoValor = AuthService.hash(novoValor as String)
        }
        try{
            empresaDao.updateField(id, coluna, novoValor)
        } catch (Exception e){
            throw new SQLException("Error: " + e.message, e)
        }
    }

    void deleteEmpresa(int id){
        try{
            empresaDao.delete(id)
        } catch (Exception e){
            throw new SQLException("Error: " + e.message, e)
        }
    }
}
