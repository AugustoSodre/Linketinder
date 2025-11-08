package com.augusto.linketinder.dao

import com.augusto.linketinder.dao.connectionProvider.ConnectionProvider
import com.augusto.linketinder.dao.connectionProvider.ConnectionProviderFactory
import com.augusto.linketinder.model.Vaga
import com.augusto.linketinder.model.pessoa.Empresa

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

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
