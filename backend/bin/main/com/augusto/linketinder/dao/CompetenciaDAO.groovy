package com.augusto.linketinder.dao

import com.augusto.linketinder.dao.connectionProvider.ConnectionProvider
import com.augusto.linketinder.model.Competencia

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

class CompetenciaDAO extends BaseDao {

    CompetenciaDAO() {
        super()
    }

    CompetenciaDAO(ConnectionProvider provider) {
        super(provider)
    }

    void insert(Competencia competencia) throws SQLException {
        final String sql = "INSERT INTO competencia(nome) VALUES (?)"

        Connection conn = null
        PreparedStatement stmt = null
        ResultSet rs = null
        try {
            conn = getConnection()
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
            stmt.setString(1, competencia.nome)
            stmt.executeUpdate()

            rs = stmt.getGeneratedKeys()
            if (rs.next()) {
                competencia.id = rs.getInt(1)
            }

        } finally {
            close(rs)
            close(stmt)
            close(conn)
        }
    }

    List<Competencia> listAll() throws SQLException {
        final String sql = "SELECT * FROM competencia ORDER BY id"
        List<Competencia> competencias = []

        Connection conn = null
        Statement stmt = null
        ResultSet rs = null
        try {
            conn = getConnection()
            stmt = conn.createStatement()
            rs = stmt.executeQuery(sql)

            while (rs.next()) {
                competencias << buildCompetencia(rs)
            }
        } finally {
            close(rs)
            close(stmt)
            close(conn)
        }

        return competencias
    }

    List<Competencia> listByCandidato(int candidatoId) throws SQLException {
        return listByRelation("competencia_candidato", "id_candidato", candidatoId)
    }

    List<Competencia> listByEmpresa(int empresaId) throws SQLException {
        return listByRelation("competencia_empresa", "id_empresa", empresaId)
    }

    List<Competencia> listByVaga(int vagaId) throws SQLException {
        return listByRelation("competencia_vaga", "id_vaga", vagaId)
    }

    void associateWithCandidato(int candidatoId, List<Competencia> competencias) throws SQLException {
        associate("competencia_candidato", "id_candidato", candidatoId, competencias)
    }

    void associateWithEmpresa(int empresaId, List<Competencia> competencias) throws SQLException {
        associate("competencia_empresa", "id_empresa", empresaId, competencias)
    }

    void associateWithVaga(int vagaId, List<Competencia> competencias) throws SQLException {
        associate("competencia_vaga", "id_vaga", vagaId, competencias)
    }

    void removeAllFromCandidato(int candidatoId) throws SQLException {
        removeByObject("competencia_candidato", "id_candidato", candidatoId)
    }

    void removeAllFromEmpresa(int empresaId) throws SQLException {
        removeByObject("competencia_empresa", "id_empresa", empresaId)
    }

    void removeAllFromVaga(int vagaId) throws SQLException {
        removeByObject("competencia_vaga", "id_vaga", vagaId)
    }

    void removeAllRelationsFromCompetencia(int competenciaId) throws SQLException {
        removeByCompetencia("competencia_candidato", competenciaId)
        removeByCompetencia("competencia_empresa", competenciaId)
        removeByCompetencia("competencia_vaga", competenciaId)
    }

    void addRelation(String objeto, int competenciaId, int objetoId) throws SQLException {
        RelationType rel = RelationType.fromObjectName(objeto)
        if (rel == null) {
            throw new IllegalArgumentException("Objeto inválido: ${objeto}")
        }
        executeRelationInsert(rel.table, rel.column, competenciaId, objetoId)
    }

    void removeRelation(String objeto, int competenciaId, int objetoId) throws SQLException {
        RelationType rel = RelationType.fromObjectName(objeto)
        if (rel == null) {
            throw new IllegalArgumentException("Objeto inválido: ${objeto}")
        }
        executeRelationDelete(rel.table, rel.column, competenciaId, objetoId)
    }

    String listRelations(String objeto) throws SQLException {
        RelationType rel = RelationType.fromObjectName(objeto)
        if (rel == null) {
            throw new IllegalArgumentException("Objeto inválido: ${objeto}")
        }

        final String sql = "SELECT rel.id_competencia, comp.nome AS nome_competencia, rel.${rel.column} AS id_objeto, obj.nome AS nome_objeto " +
                "FROM ${rel.table} rel " +
                "JOIN competencia comp ON rel.id_competencia = comp.id " +
                "JOIN ${rel.objectName} obj ON rel.${rel.column} = obj.id " +
                "ORDER BY comp.id, obj.id"

        StringBuilder builder = new StringBuilder("*competência*                  *${rel.objectName}*\n")

        Connection conn = null
        Statement stmt = null
        ResultSet rs = null
        try {
            conn = getConnection()
            stmt = conn.createStatement()
            rs = stmt.executeQuery(sql)

            while (rs.next()) {
                builder.append(rs.getInt("id_competencia"))
                        .append(" - ")
                        .append(rs.getString("nome_competencia"))
                        .append("               ")
                        .append(rs.getInt("id_objeto"))
                        .append(" - ")
                        .append(rs.getString("nome_objeto"))
                        .append("\n")
            }
        } finally {
            close(rs)
            close(stmt)
            close(conn)
        }

        return builder.toString()
    }

    void delete(int id) throws SQLException {
        final String sql = "DELETE FROM competencia WHERE id = ?"

        Connection conn = null
        PreparedStatement stmt = null
        try {
            conn = getConnection()
            stmt = conn.prepareStatement(sql)
            stmt.setInt(1, id)
            stmt.executeUpdate()
        } finally {
            close(stmt)
            close(conn)
        }
    }

    void updateName(int id, String novoNome) throws SQLException {
        final String sql = "UPDATE competencia SET nome = ? WHERE id = ?"

        Connection conn = null
        PreparedStatement stmt = null
        try {
            conn = getConnection()
            stmt = conn.prepareStatement(sql)
            stmt.setString(1, novoNome)
            stmt.setInt(2, id)
            stmt.executeUpdate()
        } finally {
            close(stmt)
            close(conn)
        }
    }

    private List<Competencia> listByRelation(String tabela, String colunaObjeto, int idObjeto) throws SQLException {
        final String sql = "SELECT comp.id, comp.nome FROM competencia comp " +
                "INNER JOIN ${tabela} rel ON comp.id = rel.id_competencia " +
                "WHERE rel.${colunaObjeto} = ? ORDER BY comp.id"

        List<Competencia> competencias = []

        Connection conn = null
        PreparedStatement stmt = null
        ResultSet rs = null
        try {
            conn = getConnection()
            stmt = conn.prepareStatement(sql)
            stmt.setInt(1, idObjeto)
            rs = stmt.executeQuery()

            while (rs.next()) {
                competencias << buildCompetencia(rs)
            }
        } finally {
            close(rs)
            close(stmt)
            close(conn)
        }

        return competencias
    }

    private void associate(String tabela, String colunaObjeto, int objetoId, List<Competencia> competencias) throws SQLException {
        if (objetoId <= 0 || competencias == null || competencias.isEmpty()) {
            return
        }

        Connection conn = null
        PreparedStatement stmt = null
        try {
            conn = getConnection()
            stmt = conn.prepareStatement("INSERT INTO ${tabela}(id_competencia, ${colunaObjeto}) VALUES (?, ?)")
            competencias.each { Competencia competencia ->
                stmt.setInt(1, competencia.id)
                stmt.setInt(2, objetoId)
                stmt.addBatch()
            }
            stmt.executeBatch()
        } finally {
            close(stmt)
            close(conn)
        }
    }

    private void removeByObject(String tabela, String colunaObjeto, int objetoId) throws SQLException {
        Connection conn = null
        PreparedStatement stmt = null
        try {
            conn = getConnection()
            stmt = conn.prepareStatement("DELETE FROM ${tabela} WHERE ${colunaObjeto} = ?")
            stmt.setInt(1, objetoId)
            stmt.executeUpdate()
        } finally {
            close(stmt)
            close(conn)
        }
    }

    private void removeByCompetencia(String tabela, int competenciaId) throws SQLException {
        Connection conn = null
        PreparedStatement stmt = null
        try {
            conn = getConnection()
            stmt = conn.prepareStatement("DELETE FROM ${tabela} WHERE id_competencia = ?")
            stmt.setInt(1, competenciaId)
            stmt.executeUpdate()
        } finally {
            close(stmt)
            close(conn)
        }
    }

    private void executeRelationInsert(String tabela, String colunaObjeto, int competenciaId, int objetoId) throws SQLException {
        Connection conn = null
        PreparedStatement stmt = null
        try {
            conn = getConnection()
            stmt = conn.prepareStatement("INSERT INTO ${tabela}(id_competencia, ${colunaObjeto}) VALUES (?, ?)")
            stmt.setInt(1, competenciaId)
            stmt.setInt(2, objetoId)
            stmt.executeUpdate()
        } finally {
            close(stmt)
            close(conn)
        }
    }

    private void executeRelationDelete(String tabela, String colunaObjeto, int competenciaId, int objetoId) throws SQLException {
        Connection conn = null
        PreparedStatement stmt = null
        try {
            conn = getConnection()
            stmt = conn.prepareStatement("DELETE FROM ${tabela} WHERE id_competencia = ? AND ${colunaObjeto} = ?")
            stmt.setInt(1, competenciaId)
            stmt.setInt(2, objetoId)
            stmt.executeUpdate()
        } finally {
            close(stmt)
            close(conn)
        }
    }

    private Competencia buildCompetencia(ResultSet rs) throws SQLException {
        return new Competencia(
                rs.getInt("id"),
                rs.getString("nome")
        )
    }

    // relation resolution is now centralized in RelationType enum
}
