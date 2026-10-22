package com.augusto.linketinder.dao

import com.augusto.linketinder.dao.connectionProvider.ConnectionProvider
import com.augusto.linketinder.dao.connectionProvider.ConnectionProviderFactory
import com.augusto.linketinder.model.Competencia
import com.augusto.linketinder.model.pessoa.Candidato

import java.sql.SQLException

class CandidatoDAO extends BaseDao {

    private final CompetenciaDAO competenciaDao

    CandidatoDAO() {
        this(ConnectionProviderFactory.getProvider())
    }

    CandidatoDAO(ConnectionProvider provider) {
        this(provider, new CompetenciaDAO(provider))
    }

    CandidatoDAO(ConnectionProvider provider, CompetenciaDAO competenciaDao) {
        super(provider)
        this.competenciaDao = Objects.requireNonNull(competenciaDao, "competenciaDao")
    }

    CandidatoDAO(CompetenciaDAO competenciaDao) {
        this(ConnectionProviderFactory.getProvider(), competenciaDao)
    }

    void insert(Candidato candidato) throws SQLException {
        candidato.id = inserir(
                "INSERT INTO candidato(nome, email, estado, cep, idade, cpf, descricao, senha) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                [candidato.nome, candidato.email, candidato.estado, candidato.cep, candidato.idade, candidato.cpf, candidato.descricao, candidato.senha])

        try {
            competenciaDao.associateWithCandidato(candidato.id, candidato.competencias)
        } catch (Exception e) {
            delete(candidato.id)
            throw e
        }
    }

    Candidato findById(int id) throws SQLException {
        return listAll(id)[0]
    }

    List<Candidato> listAll(Integer id = null) throws SQLException {
        Map<Integer, List<Competencia>> competencias = id == null ? competenciaDao.mapByCandidato() : null
        def rows = id == null ? linhas("SELECT * FROM candidato ORDER BY id") : linhas("SELECT * FROM candidato WHERE id = ?", [id])

        return rows.collect { row ->
            int candidatoId = row.id
            new Candidato(candidatoId, row.nome, row.email, row.estado, row.cep, row.cpf, row.idade as int, row.descricao,
                    competencias != null ? (competencias[candidatoId] ?: []) : competenciaDao.listByCandidato(candidatoId))
        }
    }

    Map findCredenciaisByCpf(String cpf) throws SQLException {
        def row = linhas("SELECT id, senha FROM candidato WHERE cpf = ?", [cpf])[0]
        return row ? [id: row.id as int, senha: row.senha] : null
    }

    void updateField(int id, String campo, def novoValor) throws SQLException {
        atualizarCampo("candidato", id, campo, novoValor)
    }

    void delete(int id) throws SQLException {
        competenciaDao.removeAllFromCandidato(id)
        executar("DELETE FROM candidato WHERE id = ?", [id])
    }
}
