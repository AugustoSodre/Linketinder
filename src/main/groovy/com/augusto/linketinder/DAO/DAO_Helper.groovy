package com.augusto.linketinder.DAO

import com.augusto.linketinder.model.Competencia
import com.augusto.linketinder.model.Vaga
import com.augusto.linketinder.model.pessoa.Candidato
import com.augusto.linketinder.model.pessoa.Empresa

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

class DAO_Helper {

    static List<Competencia> getListaCompCandidato(int idCandidato) {
        def sql = "SELECT comp.id, comp.nome FROM competencia comp\n" +
                "INNER JOIN\n" +
                "competencia_candidato cc ON comp.id = cc.id_competencia\n" +
                "INNER JOIN\n" +
                "candidato c ON cc.id_candidato = ?\n" +
                "GROUP BY comp.id, comp.nome"

        try {
            def listaCompCandidato = []
            Connection conn = DAO.connection()
            PreparedStatement preparedStatement = conn.prepareStatement(sql)
            preparedStatement.setInt(1, idCandidato)

            ResultSet resultSet = preparedStatement.executeQuery()

            while (resultSet.next()) {
                listaCompCandidato.add(
                        new Competencia(
                                resultSet.getInt("id"),
                                resultSet.getString("nome")
                        )
                )
            }

            return listaCompCandidato


        } catch (Exception err) {
            println(err.stackTrace)
            println(err.getMessage())
            return null
        }

    }

    static List<Competencia> getListaCompEmprsa(int idEmpresa) {
        def sql = "SELECT comp.id, comp.nome FROM competencia comp\n" +
                "INNER JOIN\n" +
                "competencia_empresa ce ON comp.id = ce.id_competencia\n" +
                "INNER JOIN\n" +
                "empresa e ON ce.id_empresa = ?\n" +
                "GROUP BY comp.id, comp.nome"

        try {
            def listaCompEmpresa = []
            Connection conn = DAO.connection()
            PreparedStatement preparedStatement = conn.prepareStatement(sql)
            preparedStatement.setInt(1, idEmpresa)

            ResultSet resultSet = preparedStatement.executeQuery()

            while (resultSet.next()) {
                listaCompEmpresa.add(
                        new Competencia(
                                resultSet.getInt("id"),
                                resultSet.getString("nome")
                        )
                )
            }

            return listaCompEmpresa


        } catch (Exception err) {
            println(err.stackTrace)
            println(err.getMessage())
            return null
        }

    }

    static List<Competencia> getListaCompVaga(int idVaga) {
        def sql = "SELECT comp.id, comp.nome FROM competencia comp\n" +
                "INNER JOIN\n" +
                "competencia_vaga cv ON comp.id = cv.id_competencia\n" +
                "INNER JOIN\n" +
                "vaga v ON cv.id_vaga = ?\n" +
                "GROUP BY comp.id, comp.nome"

        try {
            def listaCompVaga = []
            Connection conn = DAO.connection()
            PreparedStatement preparedStatement = conn.prepareStatement(sql)
            preparedStatement.setInt(1, idVaga)

            ResultSet resultSet = preparedStatement.executeQuery()

            while (resultSet.next()) {
                listaCompVaga.add(
                        new Competencia(
                                resultSet.getInt("id"),
                                resultSet.getString("nome")
                        )
                )
            }

            return listaCompVaga


        } catch (Exception err) {
            println(err.stackTrace)
            println(err.getMessage())
            return null
        }

    }

    static List<Vaga> getListaVagaEmpresa(int idEmpresa) {
        def sql = "SELECT * FROM vaga WHERE id_empresa = ?"

        try {
            def listaVagas = []
            Connection conn = DAO.connection()
            PreparedStatement preparedStatement = conn.prepareStatement(sql)
            preparedStatement.setInt(1, idEmpresa)

            ResultSet resultSet = preparedStatement.executeQuery()

            while (resultSet.next()) {
                listaVagas.add(
                        new Vaga(
                                resultSet.getInt("id"),
                                resultSet.getInt("id_empresa"),
                                resultSet.getString("titulo"),
                                resultSet.getString("descricao"),
                                resultSet.getString("cidade"),
                                resultSet.getString("estado"),
                                getListaCompVaga(resultSet.getInt("id"))
                        )
                )
            }

            return listaVagas


        } catch (Exception err) {
            println(err.stackTrace)
            println(err.getMessage())
            return null
        }
    }

    static void conectarCompCandidato(Candidato c) {
        def sql = "INSERT INTO competencia_candidato(id_competencia, id_candidato) VALUES (?, ?)"
        Connection conn = DAO.connection()

        def listaComp = c.competencias

        for (int i = 0; i < listaComp.size(); i++) {
            PreparedStatement statement = conn.prepareStatement(sql)
            statement.setInt(1, listaComp[i].id)
            statement.setInt(2, c.id)
            println(listaComp[i].id)
            println(c.id)
            statement.executeUpdate()
        }

    }

    static void conectarCompEmpresa(Empresa e) {
        def sql = "INSERT INTO competencia_empresa(id_competencia, id_empresa) VALUES (?, ?)"
        Connection conn = DAO.connection()

        def listaComp = e.competencias

        for (int i = 0; i < listaComp.size(); i++) {
            PreparedStatement statement = conn.prepareStatement(sql)
            statement.setInt(1, listaComp[i].id)
            statement.setInt(2, e.id)
            println(listaComp[i].id)
            println(e.id)
            statement.executeUpdate()
        }

    }

    static void conectarCompVaga(Vaga v) {
        def sql = "INSERT INTO competencia_vaga(id_competencia, id_vaga) VALUES (?, ?)"
        Connection conn = DAO.connection()

        def listaComp = v.competencias

        for (int i = 0; i < listaComp.size(); i++) {
            PreparedStatement statement = conn.prepareStatement(sql)
            statement.setInt(1, listaComp[i].id)
            statement.setInt(2, v.id)
            println(listaComp[i].id)
            println(v.id)
            statement.executeUpdate()
        }

    }

}
