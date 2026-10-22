package com.augusto.linketinder.dao

import com.augusto.linketinder.dao.connectionProvider.ConnectionProvider
import com.augusto.linketinder.dao.connectionProvider.ConnectionProviderFactory
import com.augusto.linketinder.model.Vaga
import com.augusto.linketinder.model.Competencia

import java.sql.SQLException

class VagaDAO extends BaseDao {

    private final CompetenciaDAO competenciaDao

    VagaDAO() {
        this(ConnectionProviderFactory.getProvider())
    }

    VagaDAO(ConnectionProvider provider) {
        this(provider, new CompetenciaDAO(provider))
    }

    VagaDAO(ConnectionProvider provider, CompetenciaDAO competenciaDao) {
        super(provider)
        this.competenciaDao = Objects.requireNonNull(competenciaDao, "competenciaDao")
    }

    VagaDAO(CompetenciaDAO competenciaDao) {
        this(ConnectionProviderFactory.getProvider(), competenciaDao)
    }

    void insert(Vaga vaga) throws SQLException {
        vaga.id = inserir("INSERT INTO vaga(id_empresa, nome, descricao, cidade, estado) VALUES (?, ?, ?, ?, ?)",
                [vaga.id_empresa, vaga.nome, vaga.descricao, vaga.cidade, vaga.estado])

        competenciaDao.associateWithVaga(vaga.id, vaga.competencias)
    }

    Vaga findById(int id) throws SQLException {
        return listAll(id)[0]
    }

    List<Vaga> listAll(Integer id = null) throws SQLException {
        Map<Integer, List<Competencia>> competencias = id == null ? competenciaDao.mapByVaga() : null
        def rows = id == null ? linhas("SELECT * FROM vaga ORDER BY id") : linhas("SELECT * FROM vaga WHERE id = ?", [id])
        return rows.collect { buildVaga(it, competencias) }
    }

    List<Vaga> listByEmpresa(int empresaId) throws SQLException {
        return linhas("SELECT * FROM vaga WHERE id_empresa = ? ORDER BY id", [empresaId]).collect { buildVaga(it) }
    }

    void updateField(int id, String campo, def novoValor) throws SQLException {
        atualizarCampo("vaga", id, campo, novoValor)
    }

    void delete(int id) throws SQLException {
        competenciaDao.removeAllFromVaga(id)
        executar("DELETE FROM vaga WHERE id = ?", [id])
    }

    void deleteByEmpresa(int empresaId) throws SQLException {
        listByEmpresa(empresaId).each { Vaga vaga -> delete(vaga.id) }
    }

    private Vaga buildVaga(Map row, Map<Integer, List<Competencia>> competenciasPorVaga = null) throws SQLException {
        int vagaId = row.id
        List<Competencia> competencias = competenciasPorVaga != null ? (competenciasPorVaga[vagaId] ?: []) : competenciaDao.listByVaga(vagaId)
        return new Vaga(vagaId, row.id_empresa as int, row.nome, row.descricao, row.cidade, row.estado, competencias)
    }
}
