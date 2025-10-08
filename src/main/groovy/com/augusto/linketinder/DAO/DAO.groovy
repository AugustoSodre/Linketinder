package com.augusto.linketinder.DAO

import com.augusto.linketinder.model.Competencia
import com.augusto.linketinder.model.Vaga
import com.augusto.linketinder.model.pessoa.Candidato
import com.augusto.linketinder.model.pessoa.Empresa

import javax.swing.plaf.nimbus.State
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement

class DAO {

    static Connection connection() {
        def url = "jdbc:postgresql://localhost:5432/linketinder"
        def user = "postgres"
        def password = "postgres"

        return DriverManager.getConnection(url, user, password)
    }

    //Create (C)
    void insert(Candidato c) {
        def sql = "INSERT INTO candidato(nome, email, estado, cep, idade, cpf, descricao, senha) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
        try {
            Connection conn = connection()
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
            statement.setString(1, c.nome)
            statement.setString(2, c.email)
            statement.setString(3, c.estado)
            statement.setString(4, c.cep)
            statement.setInt(5, c.idade)
            statement.setString(6, c.cpf)
            statement.setString(7, c.descricao)
            statement.setString(8, c.senha)
            statement.executeUpdate()

            ResultSet rs = statement.getGeneratedKeys()
            if (rs.next()) {
                int generatedId = rs.getInt(1)
                c.setId(generatedId)
            }

            DAO_Helper.conectarCompCandidato(c)


            println("Candidato inserido com sucesso!")

        } catch (Exception err) {
            println(err.stackTrace)
        }

    }

    void insert(Empresa e) {
        def sql = "INSERT INTO empresa(nome, email, estado, cep, pais, cnpj, descricao, senha) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
        try {
            Connection conn = connection()
            PreparedStatement statement = conn.prepareStatement(sql)
            statement.setString(1, e.nome)
            statement.setString(2, e.email)
            statement.setString(3, e.estado)
            statement.setString(4, e.cep)
            statement.setString(5, e.pais)
            statement.setString(6, e.cnpj)
            statement.setString(7, e.descricao)
            statement.setString(8, e.senha)
            statement.executeUpdate()

            println("Empresa inserida com sucesso!")

        } catch (Exception err) {
            println(err.stackTrace)
        }
    }

    void insert(Vaga v) {
        def sql = "INSERT INTO vaga(id_empresa, titulo, descricao, cidade, estado) " +
                "VALUES (?, ?, ?, ?, ?)"
        try {
            Connection conn = connection()
            PreparedStatement statement = conn.prepareStatement(sql)
            statement.setInt(1, v.id_empresa)
            statement.setString(2, v.titulo)
            statement.setString(3, v.descricao)
            statement.setString(4, v.cidade)
            statement.setString(5, v.estado)
            statement.executeUpdate()

            println("Vaga inserida com sucesso!")

        } catch (Exception err) {
            println(err.stackTrace)
        }
    }

    void insert(Competencia comp) {
        def sql = "INSERT INTO competencia(nome) " +
                "VALUES (?)"
        try {
            Connection conn = connection()
            PreparedStatement statement = conn.prepareStatement(sql)
            statement.setString(1, comp.nome)
            statement.executeUpdate()

            println("Competencia inserida com sucesso!")

        } catch (Exception err) {
            println(err.stackTrace)
        }
    }

    //Read (R)
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
                                DAO_Helper.getListaCompCandidato(resultSet.getInt("id")),
                                DAO_Helper.getListaVagaEmpresa(resultSet.getInt("id"))
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
                                resultSet.getString("titulo"),
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

    //Update (U)
    void update(String tabela, String campo, String novoAtributo, int id){
        def sql = "UPDATE ? SET ? = ? WHERE id = ?"

        try{
            Connection conn = connection()
            PreparedStatement preparedStatement = conn.prepareStatement(sql)
            preparedStatement.setString(1, tabela)
            preparedStatement.setString(2, campo)
            preparedStatement.setString(3, novoAtributo)
            preparedStatement.setInt(4, id)

            int linhas = preparedStatement.executeUpdate()
            linhas > 0 ? println("Objeto atualizado!") : println("Objeto não atualizado.")
        } catch (Exception e){
            println(e.stackTrace)
        }

    }

    //Delete (D)
    void delete(String tabela, int id){
        def sql = "DELETE FROM ? WHERE id = ?"

        try{
            Connection conn = connection()
            PreparedStatement preparedStatement = conn.prepareStatement(sql)
            preparedStatement.setString(1, tabela)
            preparedStatement.setInt(2, id)
            int linhas = preparedStatement.executeUpdate()
            linhas > 0 ? println("Objeto removido!") : println("Objeto não encontrado.")
        } catch (Exception e){
            println(e.stackTrace)
        }
    }




}
