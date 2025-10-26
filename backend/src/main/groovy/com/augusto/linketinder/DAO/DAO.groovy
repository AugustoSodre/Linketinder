package com.augusto.linketinder.DAO

import com.augusto.linketinder.model.Competencia
import com.augusto.linketinder.model.Vaga
import com.augusto.linketinder.model.pessoa.Candidato
import com.augusto.linketinder.model.pessoa.Empresa

import java.sql.*
import java.util.Collections

class DAO {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/linketinder"
    private static final String DB_USER = "postgres"
    private static final String DB_PASSWORD = "postgres"

    static Connection connection() {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)
    }

    static <T> T withConnection(Closure<T> action) throws SQLException {
        Connection conn = connection()
        try {
            return action.call(conn)
        } finally {
            closeQuietly(conn)
        }
    }

    static void closeQuietly(AutoCloseable resource) {
        if (resource == null) {
            return
        }
        try {
            resource.close()
        } catch (Exception ignored) {
            // Ignored on purpose
        }
    }

    private void logSqlError(String message, Exception exception) {
        System.err.println("${message}. Detalhes: ${exception.message}")
    }

    private <T> List<T> executeListQuery(String sql, Closure<T> mapper, Closure<PreparedStatement> binder = null) throws SQLException {
        withConnection { Connection conn ->
            PreparedStatement stmt = null
            ResultSet rs = null
            try {
                stmt = conn.prepareStatement(sql)
                binder?.call(stmt)
                rs = stmt.executeQuery()
                List<T> items = []
                while (rs.next()) {
                    items << mapper.call(rs)
                }
                return items
            } finally {
                closeQuietly(rs)
                closeQuietly(stmt)
            }
        }
    }

    private int executeUpdate(String sql, Closure<PreparedStatement> binder) throws SQLException {
        withConnection { Connection conn ->
            PreparedStatement stmt = null
            try {
                stmt = conn.prepareStatement(sql)
                binder.call(stmt)
                return stmt.executeUpdate()
            } finally {
                closeQuietly(stmt)
            }
        }
    }

    private int executeInsert(String sql, Closure<PreparedStatement> binder) throws SQLException {
        withConnection { Connection conn ->
            PreparedStatement stmt = null
            ResultSet rs = null
            try {
                stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
                binder.call(stmt)
                stmt.executeUpdate()
                rs = stmt.generatedKeys
                return (rs != null && rs.next()) ? rs.getInt(1) : -1
            } finally {
                closeQuietly(rs)
                closeQuietly(stmt)
            }
        }
    }

    // CREATE (C)

//    void insert(Candidato c) {
//        def sql = """
//            INSERT INTO candidato(nome, email, estado, cep, idade, cpf, descricao, senha)
//            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
//        """
//        try {
//            Connection conn = connection()
//            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
//
//            stmt.setString(1, c.nome)
//            stmt.setString(2, c.email)
//            stmt.setString(3, c.estado)
//            stmt.setString(4, c.cep)
//            stmt.setInt(5, c.idade)
//            stmt.setString(6, c.cpf)
//            stmt.setString(7, c.descricao)
//            stmt.setString(8, c.senha)
//
//            stmt.executeUpdate()
//
//            ResultSet rs = stmt.getGeneratedKeys()
//            if (rs.next()) c.id = rs.getInt(1)
//
//            DAO_Helper.conectarCompCandidato(c)
//            println("Candidato inserido com sucesso! (id=${c.id})")
//
//            rs.close()
//            stmt.close()
//            conn.close()
//
//        } catch (Exception e) {
//            println "Erro ao inserir candidato: ${e.message}"
//        }
//    }

    void insert(Empresa empresa) {
        def sql = """
            INSERT INTO empresa(nome, email, estado, cep, pais, cnpj, descricao, senha)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """

        try {
            int generatedId = executeInsert(sql) { PreparedStatement stmt ->
                stmt.setString(1, empresa.nome)
                stmt.setString(2, empresa.email)
                stmt.setString(3, empresa.estado)
                stmt.setString(4, empresa.cep)
                stmt.setString(5, empresa.pais)
                stmt.setString(6, empresa.cnpj)
                stmt.setString(7, empresa.descricao)
                stmt.setString(8, empresa.senha)
            }

            if (generatedId > 0) {
                empresa.id = generatedId
                DAO_Helper.conectarCompEmpresa(empresa)
                println("Empresa inserida com sucesso! (id=${empresa.id})")
            }
        } catch (SQLException ex) {
            logSqlError("Erro ao inserir empresa", ex)
        }
    }

    void insert(Vaga vaga) {
        def sql = """
            INSERT INTO vaga(id_empresa, nome, descricao, cidade, estado)
            VALUES (?, ?, ?, ?, ?)
        """

        try {
            int generatedId = executeInsert(sql) { PreparedStatement stmt ->
                stmt.setInt(1, vaga.id_empresa)
                stmt.setString(2, vaga.nome)
                stmt.setString(3, vaga.descricao)
                stmt.setString(4, vaga.cidade)
                stmt.setString(5, vaga.estado)
            }

            if (generatedId > 0) {
                vaga.id = generatedId
                DAO_Helper.conectarCompVaga(vaga)
                println("Vaga inserida com sucesso! (id=${vaga.id})")
            }
        } catch (SQLException ex) {
            logSqlError("Erro ao inserir vaga", ex)
        }
    }

    void insert(Competencia competencia) {
        def sql = "INSERT INTO competencia(nome) VALUES (?)"

        try {
            int generatedId = executeInsert(sql) { PreparedStatement stmt ->
                stmt.setString(1, competencia.nome)
            }

            if (generatedId > 0) {
                competencia.id = generatedId
                println("Competência inserida com sucesso! (id=${competencia.id})")
            }
        } catch (SQLException ex) {
            logSqlError("Erro ao inserir competência", ex)
        }
    }

//    void insertCompObject(String tabela, int id_competencia, int id_objeto) {
//        def sql = "INSERT INTO competencia_${tabela}(id_competencia, id_${tabela}) VALUES (?, ?)"
//
//        try {
//            Connection conn = connection()
//            PreparedStatement stmt = conn.prepareStatement(sql)
//
//            stmt.setInt(1, id_competencia)
//            stmt.setInt(2, id_objeto)
//            stmt.executeUpdate()
//
//            println("Operação realizada com sucesso!")
//
//            stmt.close()
//            conn.close()
//
//        } catch (Exception e) {
//            println "Erro ao inserir competência-${tabela}: ${e.message}"
//        }
//    }

    // READ (R)
//    List<Candidato> listCandidatos() {
//        def listaCandidato = []
//        def sql = "SELECT * FROM candidato"
//        try {
//            Connection conn = connection()
//            Statement statement = conn.createStatement()
//            ResultSet resultSet = statement.executeQuery(sql)
//            while (resultSet.next()) {
//
//                listaCandidato.add(
//                        new Candidato(
//                                resultSet.getInt("id"),
//                                resultSet.getString("nome"),
//                                resultSet.getString("email"),
//                                resultSet.getString("estado"),
//                                resultSet.getString("cep"),
//                                resultSet.getString("cpf"),
//                                resultSet.getInt("idade"),
//                                resultSet.getString("descricao"),
//                                DAO_Helper.getListaCompCandidato(resultSet.getInt("id"))
//                        )
//                )
//            }
//            return listaCandidato
//
//        } catch (Exception err) {
//            println(err.stackTrace)
//        }
//    }

    List<Candidato> listCandidatos() {
        def sql = "SELECT * FROM candidato"
        try {
            return executeListQuery(sql) { ResultSet resultSet ->
                new Candidato(
                        resultSet.getInt("id"),
                        resultSet.getString("nome"),
                        resultSet.getString("email"),
                        resultSet.getString("estado"),
                        resultSet.getString("cep"),
                        resultSet.getString("cpf"),
                        resultSet.getInt("idade"),
                        resultSet.getString("descricao"),
                        DAO_Helper.getListaCompCandidato(resultSet.getInt("id"))
                )
            }
        } catch (SQLException ex) {
            logSqlError("Erro ao listar candidatos", ex)
            return Collections.emptyList()
        }
    }

    List<Empresa> listEmpresas() {
        def sql = "SELECT * FROM empresa"
        try {
            return executeListQuery(sql) { ResultSet resultSet ->
                new Empresa(
                        resultSet.getInt("id"),
                        resultSet.getString("nome"),
                        resultSet.getString("email"),
                        resultSet.getString("estado"),
                        resultSet.getString("cep"),
                        resultSet.getString("pais"),
                        resultSet.getString("cnpj"),
                        resultSet.getString("descricao"),
                        DAO_Helper.getListaCompEmpresa(resultSet.getInt("id")),
                        DAO_Helper.getListaVagaEmpresa(resultSet.getInt("id")),
                        resultSet.getString("senha")
                )
            }
        } catch (SQLException ex) {
            logSqlError("Erro ao listar empresas", ex)
            return Collections.emptyList()
        }
    }

    List<Vaga> listVagas() {
        def sql = "SELECT * FROM vaga"
        try {
            return executeListQuery(sql) { ResultSet resultSet ->
                new Vaga(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_empresa"),
                        resultSet.getString("nome"),
                        resultSet.getString("descricao"),
                        resultSet.getString("cidade"),
                        resultSet.getString("estado"),
                        DAO_Helper.getListaCompVaga(resultSet.getInt("id"))
                )
            }
        } catch (SQLException ex) {
            logSqlError("Erro ao listar vagas", ex)
            return Collections.emptyList()
        }
    }

    List<Competencia> listCompetencia() {
        def sql = "SELECT * FROM competencia"
        try {
            return executeListQuery(sql) { ResultSet resultSet ->
                new Competencia(
                        resultSet.getInt("id"),
                        resultSet.getString("nome")
                )
            }
        } catch (SQLException ex) {
            logSqlError("Erro ao listar competências", ex)
            return Collections.emptyList()
        }
    }

    String listCompObject(String tabela) {
        String sql = "SELECT cc.id_competencia, comp.nome AS nome_competencia, cc.id_${tabela}, ${tabela}.nome as nome_${tabela} \n" +
                "FROM competencia_${tabela} cc\n" +
                "JOIN competencia comp ON cc.id_competencia = comp.id\n" +
                "JOIN ${tabela} ON cc.id_${tabela} = ${tabela}.id\n" +
                "ORDER BY comp.id, ${tabela}.id"

        String header = "*competência*                  *${tabela}*"

        try {
            List<String> linhas = executeListQuery(sql) { ResultSet resultSet ->
                String idTabelaColumn = "id_${tabela}".toString()
                String nomeTabelaColumn = "nome_${tabela}".toString()
                return String.format(
                        "%-5d - %-20s %-5d - %s",
                        resultSet.getInt("id_competencia"),
                        resultSet.getString("nome_competencia"),
                        resultSet.getInt(idTabelaColumn),
                        resultSet.getString(nomeTabelaColumn)
                )
            }

            if (linhas.isEmpty()) {
                return header + "\n"
            }

            return ([header] + linhas).join("\n") + "\n"

        } catch (SQLException ex) {
            logSqlError("Erro ao listar competências relacionadas a ${tabela}", ex)
            return header + "\n"
        }
    }


    // UPDATE (U)
    void update(String tabela, String campo, def novoAtributo, int id) {
        def sql = "UPDATE ${tabela} SET ${campo} = ? WHERE id = ?"
        try {
            int linhas = executeUpdate(sql) { PreparedStatement stmt ->
                if (novoAtributo instanceof String) {
                    stmt.setString(1, novoAtributo as String)
                } else if (novoAtributo instanceof Integer) {
                    stmt.setInt(1, novoAtributo as int)
                } else {
                    stmt.setObject(1, novoAtributo)
                }
                stmt.setInt(2, id)
            }

            println(linhas > 0 ? "Objeto atualizado!" : "Nenhum registro atualizado.")
        } catch (SQLException ex) {
            logSqlError("Erro ao atualizar ${tabela}", ex)
        }
    }

    // DELETE (D)
    void delete(String tabela, int id) {
        def sql = "DELETE FROM ${tabela} WHERE id = ?"
        try {
            int linhas = executeUpdate(sql) { PreparedStatement stmt ->
                stmt.setInt(1, id)
            }

            println(linhas > 0 ? "Objeto removido!" : "Objeto não encontrado.")
        } catch (SQLException ex) {
            logSqlError("Erro ao deletar em ${tabela}", ex)
        }
    }

    void deleteCompObjeto(String tabela, int id_competencia, int id_objeto) {
        def sql = "DELETE FROM competencia_${tabela} WHERE id_competencia = ? AND id_${tabela} = ?"
        try {
            int linhas = executeUpdate(sql) { PreparedStatement stmt ->
                stmt.setInt(1, id_competencia)
                stmt.setInt(2, id_objeto)
            }

            println(linhas > 0 ? "Objeto removido!" : "Objeto não encontrado.")
        } catch (SQLException ex) {
            logSqlError("Erro ao remover relação de competência com ${tabela}", ex)
        }

    }


}
