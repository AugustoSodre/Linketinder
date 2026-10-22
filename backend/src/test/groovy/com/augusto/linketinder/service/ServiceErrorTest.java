package com.augusto.linketinder.service;

import com.augusto.linketinder.dao.CandidatoDAO;
import com.augusto.linketinder.dao.connectionProvider.H2ConnectionProvider;
import com.augusto.linketinder.model.pessoa.Candidato;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ServiceErrorTest {

    private final SQLException falha = new SQLException("banco fora do ar");

    private final CandidatoDAO daoQuebrado = new CandidatoDAO(new H2ConnectionProvider("jdbc:h2:mem:erro", "sa", "")) {
        @Override
        public List<Candidato> listAll(Integer id) throws SQLException {
            throw falha;
        }

        @Override
        public void updateField(int id, String campo, Object novoValor) throws SQLException {
            throw falha;
        }

        @Override
        public void delete(int id) throws SQLException {
            throw falha;
        }
    };

    private final CandidatoService service = new CandidatoService(daoQuebrado);

    @Test
    void listaDeveFalharQuandoOBancoFalha() {
        assertSame(falha, assertThrows(SQLException.class, service::getCandidatoList));
    }

    @Test
    void updateEDeleteDevemPropagarOErroOriginal() {
        assertSame(falha, assertThrows(SQLException.class, () -> service.updateCandidato(1, "nome", "x")));
        assertSame(falha, assertThrows(SQLException.class, () -> service.deleteCandidato(1)));
    }
}
