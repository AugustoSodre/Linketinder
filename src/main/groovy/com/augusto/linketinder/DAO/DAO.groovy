package com.augusto.linketinder.DAO

import com.augusto.linketinder.model.Competencia
import com.augusto.linketinder.model.Vaga
import com.augusto.linketinder.model.pessoa.Candidato
import com.augusto.linketinder.model.pessoa.Empresa

import java.sql.*

class DAO {

    static Connection connection() {
        def url = "jdbc:postgresql://localhost:5432/linketinder"
        def user = "postgres"
        def password = "postgres"

        return DriverManager.getConnection(url, user, password)
    }

    // CREATE (C)

    void insert(Candidato c) {
        def sql = """
            INSERT INTO candidato(nome, email, estado, cep, idade, cpf, descricao, senha)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """
        try {
            Connection conn = connection()
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)

            stmt.setString(1, c.nome)
            stmt.setString(2, c.email)
            stmt.setString(3, c.estado)
            stmt.setString(4, c.cep)
            stmt.setInt(5, c.idade)
            stmt.setString(6, c.cpf)
            stmt.setString(7, c.descricao)
            stmt.setString(8, c.senha)

            stmt.executeUpdate()

            ResultSet rs = stmt.getGeneratedKeys()
            if (rs.next()) c.id = rs.getInt(1)

            DAO_Helper.conectarCompCandidato(c)
            println("Candidato inserido com sucesso! (id=${c.id})")

            rs.close()
            stmt.close()
            conn.close()

        } catch (Exception e) {
            println "Erro ao inserir candidato: ${e.message}"
        }
    }

    void insert(Empresa e) {
        def sql = """
            INSERT INTO empresa(nome, email, estado, cep, pais, cnpj, descricao, senha)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """
        try {
            Connection conn = connection()
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)

            stmt.setString(1, e.nome)
            stmt.setString(2, e.email)
            stmt.setString(3, e.estado)
            stmt.setString(4, e.cep)
            stmt.setString(5, e.pais)
            stmt.setString(6, e.cnpj)
            stmt.setString(7, e.descricao)
            stmt.setString(8, e.senha)

            stmt.executeUpdate()

            ResultSet rs = stmt.getGeneratedKeys()
            if (rs.next()) e.id = rs.getInt(1)

            DAO_Helper.conectarCompEmpresa(e)
            println("Empresa inserida com sucesso! (id=${e.id})")

            rs.close()
            stmt.close()
            conn.close()

        } catch (Exception err) {
            println "Erro ao inserir empresa: ${err.message}"
        }
    }

    void insert(Vaga v) {
        def sql = """
            INSERT INTO vaga(id_empresa, nome, descricao, cidade, estado)
            VALUES (?, ?, ?, ?, ?)
        """
        try {
            Connection conn = connection()
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)

            stmt.setInt(1, v.id_empresa)
            stmt.setString(2, v.nome)
            stmt.setString(3, v.descricao)
            stmt.setString(4, v.cidade)
            stmt.setString(5, v.estado)

            stmt.executeUpdate()

            ResultSet rs = stmt.getGeneratedKeys()
            if (rs.next()) v.id = rs.getInt(1)

            DAO_Helper.conectarCompVaga(v)
            println("Vaga inserida com sucesso! (id=${v.id})")

            rs.close()
            stmt.close()
            conn.close()

        } catch (Exception e) {
            println "Erro ao inserir vaga: ${e.message}"
        }
    }

    void insert(Competencia comp) {
        def sql = "INSERT INTO competencia(nome) VALUES (?)"
        try {
            Connection conn = connection()
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)

            stmt.setString(1, comp.nome)
            stmt.executeUpdate()

            ResultSet rs = stmt.getGeneratedKeys()
            if (rs.next()) comp.id = rs.getInt(1)

            println("Competência inserida com sucesso! (id=${comp.id})")

            rs.close()
            stmt.close()
            conn.close()

        } catch (Exception e) {
            println "Erro ao inserir competência: ${e.message}"
        }
    }

    void insertCompObject(String tabela, int id_competencia, int id_objeto){
        def sql = "INSERT INTO competencia_${tabela}(id_competencia, id_${tabela}) VALUES (?, ?)"

        try {
            Connection conn = connection()
            PreparedStatement stmt = conn.prepareStatement(sql)

            stmt.setInt(1, id_competencia)
            stmt.setInt(2, id_objeto)
            stmt.executeUpdate()

            println("Operação realizada com sucesso!")

            stmt.close()
            conn.close()

        } catch (Exception e) {
            println "Erro ao inserir competência-${tabela}: ${e.message}"
        }
    }

    // READ (R)
    List<Candidato> listCandidatos() {
        def listaCandidato = []
        def sql = "SELECT * FROM candidato"
        try {
            Connection conn = connection()
            Statement statement = conn.createStatement()
            ResultSet resultSet = statement.executeQuery(sql)
            while (resultSet.next()) {

                listaCandidato.add(
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
                )
            }
            return listaCandidato

        } catch (Exception err) {
            println(err.stackTrace)
        }
    }

    List<Empresa> listEmpresas() {
        def listaEmpresa = []
        def sql = "SELECT * FROM empresa"
        try {
            Connection conn = connection()
            Statement statement = conn.createStatement()
            ResultSet resultSet = statement.executeQuery(sql)
            while (resultSet.next()) {

                listaEmpresa.add(
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
                )
            }
            return listaEmpresa

        } catch (Exception err) {
            println(err.stackTrace)
        }
    }

    List<Vaga> listVagas() {
        def listaVagas = []
        def sql = "SELECT * FROM vaga"
        try {
            Connection conn = connection()
            Statement statement = conn.createStatement()
            ResultSet resultSet = statement.executeQuery(sql)
            while (resultSet.next()) {

                listaVagas.add(
                        new Vaga(
                                resultSet.getInt("id"),
                                resultSet.getInt("id_empresa"),
                                resultSet.getString("nome"),
                                resultSet.getString("descricao"),
                                resultSet.getString("cidade"),
                                resultSet.getString("estado"),
                                DAO_Helper.getListaCompVaga(resultSet.getInt("id")),
                        )
                )
            }
            return listaVagas

        } catch (Exception err) {
            println(err.stackTrace)
        }
    }

    List<Competencia> listCompetencia() {
        def listaCompetencias = []
        def sql = "SELECT * FROM competencia"
        try {
            Connection conn = connection()
            Statement statement = conn.createStatement()
            ResultSet resultSet = statement.executeQuery(sql)
            while (resultSet.next()) {

                listaCompetencias.add(
                        new Competencia(
                                resultSet.getInt("id"),
                                resultSet.getString("nome")
                        )
                )
            }
            return listaCompetencias

        } catch (Exception err) {
            println(err.stackTrace)
        }
    }

    String listCompObject(String tabela){
        String sql = "SELECT cc.id_competencia, comp.nome AS nome_competencia, cc.id_${tabela}, ${tabela}.nome as nome_${tabela} \n" +
                "FROM competencia_${tabela} cc\n" +
                "JOIN competencia comp ON cc.id_competencia = comp.id\n" +
                "JOIN ${tabela} ON cc.id_${tabela} = ${tabela}.id\n" +
                "ORDER BY comp.id, ${tabela}.id"

        String text = "*competência*                  *${tabela}*\n"
        try {
            Connection conn = connection()
            Statement statement = conn.createStatement()
            ResultSet resultSet = statement.executeQuery(sql)
            while (resultSet.next()) {
                text += resultSet.getInt("id_competencia")
                text += " - "
                text += resultSet.getString("nome_competencia")
                text += "               "
                text += resultSet.getInt("id_${tabela}")
                text += " - "
                text += resultSet.getString("nome_${tabela}")
                text += "\n"
            }
            return text

        } catch (Exception err) {
            println(err.stackTrace)
        }

        return ""

    }


    // UPDATE (U)
    void update(String tabela, String campo, def novoAtributo, int id) {
        def sql = "UPDATE ${tabela} SET ${campo} = ? WHERE id = ?"
        try {
            Connection conn = connection()
            PreparedStatement stmt = conn.prepareStatement(sql)
            
            if(novoAtributo instanceof String){
                stmt.setString(1, novoAtributo as String)
                println(novoAtributo)
            }
            else if(novoAtributo instanceof Integer){
                stmt.setInt(1, novoAtributo as int)
            }
            stmt.setInt(2, id)

            int linhas = stmt.executeUpdate()
            println(linhas > 0 ? "Objeto atualizado!" : "Nenhum registro atualizado.")

            stmt.close()
            conn.close()
        } catch (Exception e) {
            println "Erro ao atualizar: ${e.message}"
        }
    }

    // DELETE (D)
    void delete(String tabela, int id) {
        def sql = "DELETE FROM ${tabela} WHERE id = ?"
        try {
            Connection conn = connection()
            PreparedStatement stmt = conn.prepareStatement(sql)
            stmt.setInt(1, id)

            int linhas = stmt.executeUpdate()
            println(linhas > 0 ? "Objeto removido!" : "Objeto não encontrado.")

            stmt.close()
            conn.close()
        } catch (Exception e) {
            println "Erro ao deletar: ${e.message}"
        }
    }

    void deleteCompObjeto(String tabela, int id_competencia, int id_objeto){
        def sql = "DELETE FROM competencia_${tabela} WHERE id_competencia = ? AND id_${tabela} = ?"
        try {
            Connection conn = connection()
            PreparedStatement stmt = conn.prepareStatement(sql)
            stmt.setInt(1, id_competencia)
            stmt.setInt(2, id_objeto)

            int linhas = stmt.executeUpdate()
            println(linhas > 0 ? "Objeto removido!" : "Objeto não encontrado.")

            stmt.close()
            conn.close()
        } catch (Exception e) {
            println "Erro ao deletar: ${e.message}"
        }

    }


}
