package com.augusto.linketinder.dao

import com.augusto.linketinder.dao.connectionProvider.ConnectionProvider
import com.augusto.linketinder.dao.connectionProvider.ConnectionProviderFactory
import groovy.sql.Sql

import java.sql.SQLException

abstract class BaseDao {

    private final ConnectionProvider connectionProvider

    BaseDao() {
        this(ConnectionProviderFactory.getProvider())
    }

    BaseDao(ConnectionProvider connectionProvider) {
        if (connectionProvider != null) {
            this.connectionProvider = connectionProvider
        } else {
            throw new IllegalArgumentException("ConnectionProvider vazio!")
        }
    }

    protected <T> T comSql(Closure<T> acao) throws SQLException {
        Sql sql = new Sql(connectionProvider.getConnection())
        try {
            return acao(sql)
        } finally {
            sql.close()
        }
    }

    protected int inserir(String query, List params) throws SQLException {
        return comSql { Sql sql -> sql.executeInsert(query, params)[0][0] as int }
    }

    protected void executar(String query, List params) throws SQLException {
        comSql { Sql sql -> sql.executeUpdate(query, params) }
    }

    protected List<Map> linhas(String query, List params = []) throws SQLException {
        return comSql { Sql sql -> sql.rows(query, params) }
    }

    protected void atualizarCampo(String tabela, int id, String campo, Object novoValor) throws SQLException {
        executar("UPDATE " + tabela + " SET " + campo + " = ? WHERE id = ?", [novoValor instanceof Integer ? novoValor : novoValor?.toString(), id])
    }
}
