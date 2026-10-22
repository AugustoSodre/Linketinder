package com.augusto.linketinder.dao

import com.augusto.linketinder.dao.connectionProvider.ConnectionProvider
import com.augusto.linketinder.model.Competencia
import groovy.sql.Sql

import java.sql.SQLException

import static com.augusto.linketinder.dao.RelationType.CANDIDATO
import static com.augusto.linketinder.dao.RelationType.EMPRESA
import static com.augusto.linketinder.dao.RelationType.VAGA

class CompetenciaDAO extends BaseDao {

    CompetenciaDAO() {
        super()
    }

    CompetenciaDAO(ConnectionProvider provider) {
        super(provider)
    }

    void insert(Competencia competencia) throws SQLException {
        competencia.id = inserir("INSERT INTO competencia(nome) VALUES (?)", [competencia.nome])
    }

    List<Competencia> listAll() throws SQLException {
        return linhas("SELECT * FROM competencia ORDER BY id").collect { buildCompetencia(it) }
    }

    List<Competencia> listByCandidato(int candidatoId) throws SQLException {
        return listByRelation(CANDIDATO, candidatoId)
    }

    List<Competencia> listByEmpresa(int empresaId) throws SQLException {
        return listByRelation(EMPRESA, empresaId)
    }

    List<Competencia> listByVaga(int vagaId) throws SQLException {
        return listByRelation(VAGA, vagaId)
    }

    void associateWithCandidato(int candidatoId, List<Competencia> competencias) throws SQLException {
        associate(CANDIDATO, candidatoId, competencias)
    }

    void associateWithEmpresa(int empresaId, List<Competencia> competencias) throws SQLException {
        associate(EMPRESA, empresaId, competencias)
    }

    void associateWithVaga(int vagaId, List<Competencia> competencias) throws SQLException {
        associate(VAGA, vagaId, competencias)
    }

    void removeAllFromCandidato(int candidatoId) throws SQLException {
        removeByObject(CANDIDATO, candidatoId)
    }

    void removeAllFromEmpresa(int empresaId) throws SQLException {
        removeByObject(EMPRESA, empresaId)
    }

    void removeAllFromVaga(int vagaId) throws SQLException {
        removeByObject(VAGA, vagaId)
    }

    Map<Integer, List<Competencia>> mapByCandidato() throws SQLException {
        return mapByRelation(CANDIDATO)
    }

    Map<Integer, List<Competencia>> mapByEmpresa() throws SQLException {
        return mapByRelation(EMPRESA)
    }

    Map<Integer, List<Competencia>> mapByVaga() throws SQLException {
        return mapByRelation(VAGA)
    }

    private Map<Integer, List<Competencia>> mapByRelation(RelationType rel) throws SQLException {
        final String sql = "SELECT rel.${rel.column} AS id_objeto, comp.id, comp.nome FROM competencia comp " +
                "INNER JOIN ${rel.table} rel ON comp.id = rel.id_competencia ORDER BY comp.id"

        Map<Integer, List<Competencia>> competencias = [:]
        linhas(sql).each { competencias.computeIfAbsent(it.id_objeto as Integer) { [] } << buildCompetencia(it) }
        return competencias
    }

    private List<Competencia> listByRelation(RelationType rel, int idObjeto) throws SQLException {
        final String sql = "SELECT comp.id, comp.nome FROM competencia comp " +
                "INNER JOIN ${rel.table} rel ON comp.id = rel.id_competencia " +
                "WHERE rel.${rel.column} = ? ORDER BY comp.id"
        return linhas(sql, [idObjeto]).collect { buildCompetencia(it) }
    }

    private void associate(RelationType rel, int objetoId, List<Competencia> competencias) throws SQLException {
        if (objetoId <= 0 || competencias == null || competencias.isEmpty()) {
            return
        }
        final String sql = "INSERT INTO ${rel.table}(id_competencia, ${rel.column}) VALUES (?, ?)"
        comSql { Sql db ->
            db.withBatch(sql) { ps -> competencias.each { ps.addBatch([it.id, objetoId]) } }
        }
    }

    private void removeByObject(RelationType rel, int objetoId) throws SQLException {
        executar("DELETE FROM " + rel.table + " WHERE " + rel.column + " = ?", [objetoId])
    }

    private static Competencia buildCompetencia(Map row) {
        return new Competencia(row.id as int, row.nome as String)
    }
}
