package com.augusto.linketinder.dao

import com.augusto.linketinder.dao.connectionProvider.ConnectionProvider
import com.augusto.linketinder.dao.connectionProvider.ConnectionProviderFactory
import com.augusto.linketinder.model.pessoa.Candidato

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

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
            close(rs)
            close(stmt)
            close(conn)
        }

        // shortcut: desfaz com delete em vez de transação (cada DAO abre sua conexão); trocar por transação se os DAOs passarem a compartilhar a Connection
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

    // Sem id lista todos; com id traz só aquele registro (evita carregar a tabela inteira)
    List<Candidato> listAll(Integer id = null) throws SQLException {
        final String sql = id == null ? "SELECT * FROM candidato ORDER BY id" : "SELECT * FROM candidato WHERE id = ?"
        List<Candidato> candidatos = []

        Connection conn = null
        PreparedStatement stmt = null
        ResultSet rs = null
        try {
            conn = getConnection()
            stmt = conn.prepareStatement(sql)
            if (id != null) {
                stmt.setInt(1, id)
            }
            rs = stmt.executeQuery()

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
            close(rs)
            close(stmt)
            close(conn)
        }

        return candidatos
    }

    Map findCredenciaisByCpf(String cpf) throws SQLException {
        final String sql = "SELECT id, senha FROM candidato WHERE cpf = ?"

        Connection conn = null
        PreparedStatement stmt = null
        ResultSet rs = null
        try {
            conn = getConnection()
            stmt = conn.prepareStatement(sql)
            stmt.setString(1, cpf)
            rs = stmt.executeQuery()
            return rs.next() ? [id: rs.getInt("id"), senha: rs.getString("senha")] : null
        } finally {
            close(rs)
            close(stmt)
            close(conn)
        }
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
            close(stmt)
            close(conn)
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
            close(stmt)
            close(conn)
        }
    }
}
