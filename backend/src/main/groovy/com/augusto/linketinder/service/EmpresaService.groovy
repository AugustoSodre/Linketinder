package com.augusto.linketinder.service

import com.augusto.linketinder.dao.EmpresaDAO
import com.augusto.linketinder.dao.DaoFactory
import com.augusto.linketinder.model.pessoa.Empresa

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
    }

    Empresa getEmpresaById(int id){
        return empresaDao.findById(id)
    }

    List<Empresa> getEmpresaList(){
        return empresaDao.listAll()
    }

    List<String> getCamposEmpresa() {
        return ['nome', 'email', 'estado', 'cep', 'pais', 'cnpj', 'descricao', 'senha']
    }

    void updateEmpresa(int id, String coluna, Object novoValor){
        if (!(coluna in getCamposEmpresa())) {
            throw new IllegalArgumentException("Campo inválido: " + coluna)
        }
        if (coluna == 'senha') {
            novoValor = AuthService.hash(novoValor as String)
        }
        empresaDao.updateField(id, coluna, novoValor)
    }

    void deleteEmpresa(int id){
        empresaDao.delete(id)
    }
}
