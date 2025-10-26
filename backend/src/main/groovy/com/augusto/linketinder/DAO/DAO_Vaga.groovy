package com.augusto.linketinder.DAO

import com.augusto.linketinder.model.Vaga
import com.augusto.linketinder.model.Competencia

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

class DAO_Vaga extends BaseDao {

	private final DAO_Competencia competenciaDao = new DAO_Competencia()

	void insert(Vaga vaga) throws SQLException {
		final String sql = """
			INSERT INTO vaga(id_empresa, nome, descricao, cidade, estado)
			VALUES (?, ?, ?, ?, ?)
		"""

		Connection conn = null
		PreparedStatement stmt = null
		ResultSet rs = null
		try {
			conn = getConnection()
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)

			stmt.setInt(1, vaga.id_empresa)
			stmt.setString(2, vaga.nome)
			stmt.setString(3, vaga.descricao)
			stmt.setString(4, vaga.cidade)
			stmt.setString(5, vaga.estado)

			stmt.executeUpdate()

			rs = stmt.getGeneratedKeys()
			if (rs.next()) {
				vaga.id = rs.getInt(1)
			}

		} finally {
			closeQuietly(rs)
			closeQuietly(stmt)
			closeQuietly(conn)
		}

		competenciaDao.associateWithVaga(vaga.id, vaga.competencias)
	}

	List<Vaga> listAll() throws SQLException {
		final String sql = "SELECT * FROM vaga ORDER BY id"
		List<Vaga> vagas = []

		Connection conn = null
		Statement stmt = null
		ResultSet rs = null
		try {
			conn = getConnection()
			stmt = conn.createStatement()
			rs = stmt.executeQuery(sql)

			while (rs.next()) {
				vagas << buildVaga(rs)
			}
		} finally {
			closeQuietly(rs)
			closeQuietly(stmt)
			closeQuietly(conn)
		}

		return vagas
	}

	List<Vaga> listByEmpresa(int empresaId) throws SQLException {
		final String sql = "SELECT * FROM vaga WHERE id_empresa = ? ORDER BY id"
		List<Vaga> vagas = []

		Connection conn = null
		PreparedStatement stmt = null
		ResultSet rs = null
		try {
			conn = getConnection()
			stmt = conn.prepareStatement(sql)
			stmt.setInt(1, empresaId)
			rs = stmt.executeQuery()

			while (rs.next()) {
				vagas << buildVaga(rs)
			}
		} finally {
			closeQuietly(rs)
			closeQuietly(stmt)
			closeQuietly(conn)
		}

		return vagas
	}

	void updateField(int id, String campo, def novoValor) throws SQLException {
		final String sql = "UPDATE vaga SET ${campo} = ? WHERE id = ?"

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
		competenciaDao.removeAllFromVaga(id)

		final String sql = "DELETE FROM vaga WHERE id = ?"
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

	void deleteByEmpresa(int empresaId) throws SQLException {
		List<Vaga> vagas = listByEmpresa(empresaId)
		vagas.each { Vaga vaga -> delete(vaga.id) }
	}

	private Vaga buildVaga(ResultSet rs) throws SQLException {
		int vagaId = rs.getInt("id")
		List<Competencia> competencias = competenciaDao.listByVaga(vagaId)
		return new Vaga(
				vagaId,
				rs.getInt("id_empresa"),
				rs.getString("nome"),
				rs.getString("descricao"),
				rs.getString("cidade"),
				rs.getString("estado"),
				competencias
		)
	}
}
