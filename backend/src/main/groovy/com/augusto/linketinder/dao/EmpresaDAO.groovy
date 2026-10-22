package com.augusto.linketinder.dao

import com.augusto.linketinder.dao.connectionProvider.ConnectionProvider
import com.augusto.linketinder.dao.connectionProvider.ConnectionProviderFactory
import com.augusto.linketinder.model.Competencia
import com.augusto.linketinder.model.Vaga
import com.augusto.linketinder.model.pessoa.Empresa

import java.sql.SQLException

class EmpresaDAO extends BaseDao {

    private final CompetenciaDAO competenciaDao
    private final VagaDAO vagaDao

    EmpresaDAO() {
        this(ConnectionProviderFactory.getProvider())
    }

    EmpresaDAO(ConnectionProvider provider) {
        this(provider, new CompetenciaDAO(provider))
    }

    EmpresaDAO(ConnectionProvider provider, CompetenciaDAO competenciaDao) {
        this(provider, competenciaDao, new VagaDAO(provider, competenciaDao))
    }

    EmpresaDAO(ConnectionProvider provider, CompetenciaDAO competenciaDao, VagaDAO vagaDao) {
        super(provider)
        this.competenciaDao = Objects.requireNonNull(competenciaDao, "competenciaDao")
        this.vagaDao = Objects.requireNonNull(vagaDao, "vagaDao")
    }

    EmpresaDAO(CompetenciaDAO competenciaDao, VagaDAO vagaDao) {
        this(ConnectionProviderFactory.getProvider(), competenciaDao, vagaDao)
    }

    void insert(Empresa empresa) throws SQLException {
        empresa.id = inserir(
                "INSERT INTO empresa(nome, email, estado, cep, pais, cnpj, descricao, senha) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                [empresa.nome, empresa.email, empresa.estado, empresa.cep, empresa.pais, empresa.cnpj, empresa.descricao, empresa.senha])

        try {
            competenciaDao.associateWithEmpresa(empresa.id, empresa.competencias)

            empresa.listaVaga?.each { vaga ->
                vaga.id_empresa = empresa.id
                vagaDao.insert(vaga)
            }
        } catch (Exception e) {
            delete(empresa.id)
            throw e
        }
    }

    Empresa findById(int id) throws SQLException {
        return listAll(id)[0]
    }

    List<Empresa> listAll(Integer id = null) throws SQLException {
        Map<Integer, List<Vaga>> vagasPorEmpresa = id == null ? vagaDao.listAll().groupBy { it.id_empresa } : null
        Map<Integer, List<Competencia>> competencias = id == null ? competenciaDao.mapByEmpresa() : null
        def rows = id == null ? linhas("SELECT * FROM empresa ORDER BY id") : linhas("SELECT * FROM empresa WHERE id = ?", [id])

        return rows.collect { row ->
            int empresaId = row.id
            new Empresa(empresaId, row.nome, row.email, row.estado, row.cep, row.pais, row.cnpj, row.descricao,
                    competencias != null ? (competencias[empresaId] ?: []) : competenciaDao.listByEmpresa(empresaId),
                    vagasPorEmpresa != null ? (vagasPorEmpresa[empresaId] ?: []) : vagaDao.listByEmpresa(empresaId),
                    row.senha)
        }
    }

    Map findCredenciaisByCnpj(String cnpj) throws SQLException {
        def row = linhas("SELECT id, senha FROM empresa WHERE cnpj = ?", [cnpj])[0]
        return row ? [id: row.id as int, senha: row.senha] : null
    }

    void updateField(int id, String campo, def novoValor) throws SQLException {
        atualizarCampo("empresa", id, campo, novoValor)
    }

    void delete(int id) throws SQLException {
        competenciaDao.removeAllFromEmpresa(id)
        vagaDao.deleteByEmpresa(id)
        executar("DELETE FROM empresa WHERE id = ?", [id])
    }
}
