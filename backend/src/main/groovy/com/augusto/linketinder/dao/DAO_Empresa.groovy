package com.augusto.linketinder.dao

import com.augusto.linketinder.model.Vaga
import com.augusto.linketinder.model.pessoa.Empresa

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

class DAO_Empresa extends BaseDao {

    private final DAO_Competencia competenciaDao
    private final DAO_Vaga vagaDao

    DAO_Empresa() {
        this(new DataSource())
    }

    DAO_Empresa(DataSource dataSource) {
        this(dataSource, new DAO_Competencia(dataSource))
    }

    DAO_Empresa(DataSource dataSource, DAO_Competencia competenciaDao) {
        this(dataSource, competenciaDao, new DAO_Vaga(dataSource, competenciaDao))
    }

    DAO_Empresa(DataSource dataSource, DAO_Competencia competenciaDao, DAO_Vaga vagaDao) {
        super(dataSource)
        this.competenciaDao = Objects.requireNonNull(competenciaDao, "competenciaDao")
        this.vagaDao = Objects.requireNonNull(vagaDao, "vagaDao")
    }

    DAO_Empresa(DAO_Competencia competenciaDao, DAO_Vaga vagaDao) {
        this(new DataSource(), competenciaDao, vagaDao)
    }

    void insert(Empresa empresa) throws SQLException {
        final String sql = """
			INSERT INTO empresa(nome, email, estado, cep, pais, cnpj, descricao, senha)
			VALUES (?, ?, ?, ?, ?, ?, ?, ?)
		"""

        Connection conn = null
        PreparedStatement stmt = null
        ResultSet rs = null
        try {
            conn = getConnection()
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)

            stmt.setString(1, empresa.nome)
            stmt.setString(2, empresa.email)
            stmt.setString(3, empresa.estado)
            stmt.setString(4, empresa.cep)
            stmt.setString(5, empresa.pais)
            stmt.setString(6, empresa.cnpj)
            stmt.setString(7, empresa.descricao)
            stmt.setString(8, empresa.senha)

            stmt.executeUpdate()

            rs = stmt.getGeneratedKeys()
            if (rs.next()) {
                empresa.id = rs.getInt(1)
            }

        } finally {
            close(rs)
            close(stmt)
            close(conn)
        }

        competenciaDao.associateWithEmpresa(empresa.id, empresa.competencias)
    }

    List<Empresa> listAll() throws SQLException {
        final String sql = "SELECT * FROM empresa ORDER BY id"
        List<Empresa> empresas = []

        Connection conn = null
        Statement stmt = null
        ResultSet rs = null
        try {
            conn = getConnection()
            stmt = conn.createStatement()
            rs = stmt.executeQuery(sql)

            while (rs.next()) {
                int empresaId = rs.getInt("id")
                List<Vaga> vagas = vagaDao.listByEmpresa(empresaId)
                empresas << new Empresa(
                        empresaId,
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("estado"),
                        rs.getString("cep"),
                        rs.getString("pais"),
                        rs.getString("cnpj"),
                        rs.getString("descricao"),
                        competenciaDao.listByEmpresa(empresaId),
                        vagas,
                        rs.getString("senha")
                )
            }
        } finally {
            close(rs)
            close(stmt)
            close(conn)
        }

        return empresas
    }

    void updateField(int id, String campo, def novoValor) throws SQLException {
        final String sql = "UPDATE empresa SET ${campo} = ? WHERE id = ?"

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
        competenciaDao.removeAllFromEmpresa(id)
        vagaDao.deleteByEmpresa(id)

        final String sql = "DELETE FROM empresa WHERE id = ?"

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
