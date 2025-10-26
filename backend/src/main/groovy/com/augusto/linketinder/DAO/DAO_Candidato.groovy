package com.augusto.linketinder.DAO

import com.augusto.linketinder.model.pessoa.Candidato

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

class DAO_Candidato extends BaseDao {

    private final DAO_Competencia competenciaDao = new DAO_Competencia()

    void insert(Candidato candidato) throws SQLException {
        final String sql = """
            INSERT INTO candidato(nome, email, estado, cep, idade, cpf, descricao, senha)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """

        Connection conn = null
        PreparedStatement stmt = null
        ResultSet rs = null
        try {
            conn = getConnection()
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)

            stmt.setString(1, candidato.nome)
            stmt.setString(2, candidato.email)
            stmt.setString(3, candidato.estado)
            stmt.setString(4, candidato.cep)
            stmt.setInt(5, candidato.idade)
            stmt.setString(6, candidato.cpf)
            stmt.setString(7, candidato.descricao)
            stmt.setString(8, candidato.senha)

            stmt.executeUpdate()

            rs = stmt.getGeneratedKeys()
            if (rs.next()) {
                candidato.id = rs.getInt(1)
            }

        } finally {
            closeQuietly(rs)
            closeQuietly(stmt)
            closeQuietly(conn)
        }

        competenciaDao.associateWithCandidato(candidato.id, candidato.competencias)
    }

    List<Candidato> listAll() throws SQLException {
        final String sql = "SELECT * FROM candidato ORDER BY id"
        List<Candidato> candidatos = []

        Connection conn = null
        Statement stmt = null
        ResultSet rs = null
        try {
            conn = getConnection()
            stmt = conn.createStatement()
            rs = stmt.executeQuery(sql)

            while (rs.next()) {
                int candidatoId = rs.getInt("id")
                candidatos << new Candidato(
                        candidatoId,
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("estado"),
                        rs.getString("cep"),
                        rs.getString("cpf"),
                        rs.getInt("idade"),
                        rs.getString("descricao"),
                        competenciaDao.listByCandidato(candidatoId)
                )
            }
        } finally {
            closeQuietly(rs)
            closeQuietly(stmt)
            closeQuietly(conn)
        }

        return candidatos
    }

    void updateField(int id, String campo, def novoValor) throws SQLException {
        final String sql = "UPDATE candidato SET ${campo} = ? WHERE id = ?"

        Connection conn = null
        PreparedStatement stmt = null
        try {
            conn = getConnection()
            stmt = conn.prepareStatement(sql)

            if (novoValor instanceof Integer) {
                stmt.setInt(1, novoValor as int)
            } else {
                stmt.setString(1, novoValor?.toString())
            }

            stmt.setInt(2, id)
            stmt.executeUpdate()
        } finally {
            closeQuietly(stmt)
            closeQuietly(conn)
        }
    }

    void delete(int id) throws SQLException {
        competenciaDao.removeAllFromCandidato(id)

        final String sql = "DELETE FROM candidato WHERE id = ?"
        Connection conn = null
        PreparedStatement stmt = null
        try {
            conn = getConnection()
            stmt = conn.prepareStatement(sql)
            stmt.setInt(1, id)
            stmt.executeUpdate()
        } finally {
            closeQuietly(stmt)
            closeQuietly(conn)
        }
    }
}
