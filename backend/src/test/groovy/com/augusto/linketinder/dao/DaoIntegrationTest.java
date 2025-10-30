package com.augusto.linketinder.dao;

import com.augusto.linketinder.model.Competencia;
import com.augusto.linketinder.model.Vaga;
import com.augusto.linketinder.model.pessoa.Candidato;
import com.augusto.linketinder.model.pessoa.Empresa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DaoIntegrationTest {

    private DataSource dataSource;
    private DAO_Competencia competenciaDao;
    private DAO_Vaga vagaDao;
    private DAO_Candidato candidatoDao;
    private DAO_Empresa empresaDao;

    @BeforeEach
    void setUp() throws SQLException {
        dataSource = new DataSource(
                "jdbc:h2:mem:linketinder;MODE=PostgreSQL;DATABASE_TO_UPPER=false;DB_CLOSE_DELAY=-1",
                "sa",
                ""
        );
        competenciaDao = new DAO_Competencia(dataSource);
        vagaDao = new DAO_Vaga(dataSource, competenciaDao);
        candidatoDao = new DAO_Candidato(dataSource, competenciaDao);
        empresaDao = new DAO_Empresa(dataSource, competenciaDao, vagaDao);
        recreateSchema();
    }

    @Test
    void shouldInsertAndListCompetencias() throws SQLException {
        Competencia groovy = new Competencia("Groovy");
        Competencia java = new Competencia("Java");

        competenciaDao.insert(groovy);
        competenciaDao.insert(java);

        List<Competencia> competencias = competenciaDao.listAll();

        assertEquals(2, competencias.size());
        assertEquals("Groovy", competencias.get(0).getNome());
        assertEquals("Java", competencias.get(1).getNome());
    }

    @Test
    void shouldLinkCompetenciasToCandidatoOnInsert() throws SQLException {
        Competencia groovy = new Competencia("Groovy");
        Competencia java = new Competencia("Java");
        competenciaDao.insert(groovy);
        competenciaDao.insert(java);

        Candidato candidato = new Candidato();
        candidato.setNome("Ana");
        candidato.setEmail("ana@example.com");
        candidato.setEstado("SP");
        candidato.setCep("01000-000");
        candidato.setDescricao("Backend dev");
        candidato.setCpf("123.456.789-00");
        candidato.setIdade(30);
        candidato.setSenha("senha123");
        candidato.setCompetencias(Arrays.asList(groovy, java));

        candidatoDao.insert(candidato);

        assertTrue(candidato.getId() > 0);

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT COUNT(*) FROM competencia_candidato WHERE id_candidato = ?"
             )) {
            statement.setInt(1, candidato.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                assertTrue(resultSet.next());
                assertEquals(2, resultSet.getInt(1));
            }
        }

        List<Candidato> stored = candidatoDao.listAll();
        assertEquals(1, stored.size());
        assertEquals(2, stored.get(0).getCompetencias().size());
    }

    @Test
    void shouldLinkCompetenciasToEmpresaAndVaga() throws SQLException {
        Competencia sql = new Competencia("SQL");
        Competencia agile = new Competencia("Agile");
        competenciaDao.insert(sql);
        competenciaDao.insert(agile);

        Empresa empresa = new Empresa();
        empresa.setNome("Tech Corp");
        empresa.setEmail("contato@techcorp.com");
        empresa.setEstado("PE");
        empresa.setCep("50000-000");
        empresa.setPais("Brasil");
        empresa.setCnpj("12.345.678/0001-99");
        empresa.setDescricao("Consultoria de tecnologia");
        empresa.setSenha("senhaSegura");
        empresa.setCompetencias(Arrays.asList(sql, agile));

        empresaDao.insert(empresa);
        assertTrue(empresa.getId() > 0);

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT COUNT(*) FROM competencia_empresa WHERE id_empresa = ?"
             )) {
            statement.setInt(1, empresa.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                assertTrue(resultSet.next());
                assertEquals(2, resultSet.getInt(1));
            }
        }

        Vaga vaga = new Vaga(
                empresa.getId(),
                "Desenvolvedor Groovy",
                "Atuação em projeto de integração",
                "Recife",
                "PE",
                Arrays.asList(sql, agile)
        );

        vagaDao.insert(vaga);
        assertTrue(vaga.getId() > 0);

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT COUNT(*) FROM competencia_vaga WHERE id_vaga = ?"
             )) {
            statement.setInt(1, vaga.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                assertTrue(resultSet.next());
                assertEquals(2, resultSet.getInt(1));
            }
        }

        List<Vaga> vagas = vagaDao.listByEmpresa(empresa.getId());
        assertEquals(1, vagas.size());
        assertEquals(2, vagas.get(0).getCompetencias().size());

        List<Empresa> empresas = empresaDao.listAll();
        assertEquals(1, empresas.size());
        assertNotNull(empresas.get(0).getListaVaga());
        assertEquals(1, empresas.get(0).getListaVaga().size());
        assertEquals(2, empresas.get(0).getCompetencias().size());
    }

    private void recreateSchema() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            String[] dropStatements = {
                    "DROP TABLE IF EXISTS competencia_candidato",
                    "DROP TABLE IF EXISTS competencia_empresa",
                    "DROP TABLE IF EXISTS competencia_vaga",
                    "DROP TABLE IF EXISTS vaga",
                    "DROP TABLE IF EXISTS empresa",
                    "DROP TABLE IF EXISTS candidato",
                    "DROP TABLE IF EXISTS competencia"
            };

            for (String sql : dropStatements) {
                statement.execute(sql);
            }

            String[] createStatements = {
                    "CREATE TABLE competencia (" +
                            "id SERIAL PRIMARY KEY," +
                            "nome VARCHAR(255) NOT NULL" +
                            ")",
                    "CREATE TABLE candidato (" +
                            "id SERIAL PRIMARY KEY," +
                            "nome VARCHAR(255) NOT NULL," +
                            "email VARCHAR(255) NOT NULL," +
                            "estado VARCHAR(2) NOT NULL," +
                            "cep VARCHAR(12) NOT NULL," +
                            "idade INT NOT NULL," +
                            "cpf VARCHAR(20) NOT NULL," +
                            "descricao VARCHAR(255)," +
                            "senha VARCHAR(255) NOT NULL" +
                            ")",
                    "CREATE TABLE empresa (" +
                            "id SERIAL PRIMARY KEY," +
                            "nome VARCHAR(255) NOT NULL," +
                            "email VARCHAR(255) NOT NULL," +
                            "estado VARCHAR(2) NOT NULL," +
                            "cep VARCHAR(12) NOT NULL," +
                            "pais VARCHAR(60) NOT NULL," +
                            "cnpj VARCHAR(32) NOT NULL," +
                            "descricao VARCHAR(255)," +
                            "senha VARCHAR(255) NOT NULL" +
                            ")",
                    "CREATE TABLE vaga (" +
                            "id SERIAL PRIMARY KEY," +
                            "id_empresa INT NOT NULL," +
                            "nome VARCHAR(255) NOT NULL," +
                            "descricao VARCHAR(255)," +
                            "cidade VARCHAR(255) NOT NULL," +
                            "estado VARCHAR(2) NOT NULL," +
                            "FOREIGN KEY (id_empresa) REFERENCES empresa(id) ON DELETE CASCADE" +
                            ")",
                    "CREATE TABLE competencia_candidato (" +
                            "id_competencia INT NOT NULL," +
                            "id_candidato INT NOT NULL," +
                            "PRIMARY KEY (id_competencia, id_candidato)," +
                            "FOREIGN KEY (id_competencia) REFERENCES competencia(id) ON DELETE CASCADE," +
                            "FOREIGN KEY (id_candidato) REFERENCES candidato(id) ON DELETE CASCADE" +
                            ")",
                    "CREATE TABLE competencia_empresa (" +
                            "id_competencia INT NOT NULL," +
                            "id_empresa INT NOT NULL," +
                            "PRIMARY KEY (id_competencia, id_empresa)," +
                            "FOREIGN KEY (id_competencia) REFERENCES competencia(id) ON DELETE CASCADE," +
                            "FOREIGN KEY (id_empresa) REFERENCES empresa(id) ON DELETE CASCADE" +
                            ")",
                    "CREATE TABLE competencia_vaga (" +
                            "id_competencia INT NOT NULL," +
                            "id_vaga INT NOT NULL," +
                            "PRIMARY KEY (id_competencia, id_vaga)," +
                            "FOREIGN KEY (id_competencia) REFERENCES competencia(id) ON DELETE CASCADE," +
                            "FOREIGN KEY (id_vaga) REFERENCES vaga(id) ON DELETE CASCADE" +
                            ")"
            };

            for (String sql : createStatements) {
                statement.execute(sql);
            }
        }
    }
}
