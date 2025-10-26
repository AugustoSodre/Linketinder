package com.augusto.linketinder.DAO

import com.augusto.linketinder.model.Competencia
import com.augusto.linketinder.model.Vaga
import com.augusto.linketinder.model.pessoa.Candidato
import com.augusto.linketinder.model.pessoa.Empresa

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.util.Collections

class DAO_Helper {

    static List<Competencia> getListaCompCandidato(int idCandidato) {
        final String sql = "SELECT comp.id, comp.nome FROM competencia comp\n" +
                "INNER JOIN\n" +
                "competencia_candidato cc ON comp.id = cc.id_competencia\n" +
                "INNER JOIN\n" +
                "candidato c ON cc.id_candidato = ?\n" +
                "GROUP BY comp.id, comp.nome"

        return fetchList(sql, idCandidato, { ResultSet resultSet ->
            new Competencia(
                    resultSet.getInt("id"),
                    resultSet.getString("nome")
            )
        }, "Erro ao buscar competências do candidato ${idCandidato}")
    }

    static List<Competencia> getListaCompEmpresa(int idEmpresa) {
        final String sql = "SELECT comp.id, comp.nome FROM competencia comp\n" +
                "INNER JOIN\n" +
                "competencia_empresa ce ON comp.id = ce.id_competencia\n" +
                "INNER JOIN\n" +
                "empresa e ON ce.id_empresa = ?\n" +
                "GROUP BY comp.id, comp.nome"

        return fetchList(sql, idEmpresa, { ResultSet resultSet ->
            new Competencia(
                    resultSet.getInt("id"),
                    resultSet.getString("nome")
            )
        }, "Erro ao buscar competências da empresa ${idEmpresa}")
    }

    static List<Competencia> getListaCompVaga(int idVaga) {
        final String sql = "SELECT comp.id, comp.nome FROM competencia comp\n" +
                "INNER JOIN\n" +
                "competencia_vaga cv ON comp.id = cv.id_competencia\n" +
                "INNER JOIN\n" +
                "vaga v ON cv.id_vaga = ?\n" +
                "GROUP BY comp.id, comp.nome"

        return fetchList(sql, idVaga, { ResultSet resultSet ->
            new Competencia(
                    resultSet.getInt("id"),
                    resultSet.getString("nome")
            )
        }, "Erro ao buscar competências da vaga ${idVaga}")
    }

    static List<Vaga> getListaVagaEmpresa(int idEmpresa) {
        final String sql = "SELECT * FROM vaga WHERE id_empresa = ?"

        return fetchList(sql, idEmpresa, { ResultSet resultSet ->
            new Vaga(
                    resultSet.getInt("id"),
                    resultSet.getInt("id_empresa"),
                    resultSet.getString("nome"),
                    resultSet.getString("descricao"),
                    resultSet.getString("cidade"),
                    resultSet.getString("estado"),
                    getListaCompVaga(resultSet.getInt("id"))
            )
        }, "Erro ao buscar vagas da empresa ${idEmpresa}")
    }

    static void conectarCompCandidato(Candidato c) {
        relacionarCompetencias(
                "INSERT INTO competencia_candidato(id_competencia, id_candidato) VALUES (?, ?)",
                c?.id ?: -1,
                c?.competencias ?: Collections.emptyList(),
                "Erro ao vincular competências ao candidato ${c?.id ?: "desconhecido"}"
        )
    }

    static void conectarCompEmpresa(Empresa e) {
        relacionarCompetencias(
                "INSERT INTO competencia_empresa(id_competencia, id_empresa) VALUES (?, ?)",
                e?.id ?: -1,
                e?.competencias ?: Collections.emptyList(),
                "Erro ao vincular competências à empresa ${e?.id ?: "desconhecida"}"
        )
    }

    static void conectarCompVaga(Vaga v) {
        relacionarCompetencias(
                "INSERT INTO competencia_vaga(id_competencia, id_vaga) VALUES (?, ?)",
                v?.id ?: -1,
                v?.competencias ?: Collections.emptyList(),
                "Erro ao vincular competências à vaga ${v?.id ?: "desconhecida"}"
        )
    }


    private static <T> List<T> fetchList(String sql, int referenceId, Closure<T> mapper, String errorMessage) {
        try {
            return DAO.withConnection { Connection conn ->
                PreparedStatement stmt = null
                ResultSet rs = null
                try {
                    stmt = conn.prepareStatement(sql)
                    stmt.setInt(1, referenceId)
                    rs = stmt.executeQuery()
                    List<T> items = []
                    while (rs.next()) {
                        items << mapper.call(rs)
                    }
                    items
                } finally {
                    DAO.closeQuietly(rs)
                    DAO.closeQuietly(stmt)
                }
            } ?: Collections.emptyList()
        } catch (SQLException ex) {
            logSqlError(errorMessage, ex)
            return Collections.emptyList()
        }
    }

    private static void relacionarCompetencias(String sql, int referenciaId, List<Competencia> competencias, String errorMessage) {
        if (referenciaId <= 0 || competencias == null || competencias.isEmpty()) {
            return
        }

        try {
            DAO.withConnection { Connection conn ->
                PreparedStatement stmt = null
                try {
                    stmt = conn.prepareStatement(sql)
                    competencias.each { Competencia competencia ->
                        stmt.setInt(1, competencia.id)
                        stmt.setInt(2, referenciaId)
                        stmt.addBatch()
                    }
                    stmt.executeBatch()
                } finally {
                    DAO.closeQuietly(stmt)
                }
            }
            println("Competências vinculadas com sucesso.")
        } catch (SQLException ex) {
            logSqlError(errorMessage, ex)
        }
    }

    private static void logSqlError(String message, Exception exception) {
        System.err.println("${message}. Detalhes: ${exception.message}")
    }
}
