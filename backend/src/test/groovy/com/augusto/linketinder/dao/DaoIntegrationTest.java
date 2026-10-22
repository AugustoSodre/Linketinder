package com.augusto.linketinder.dao;

import com.augusto.linketinder.model.Competencia;
import com.augusto.linketinder.model.Vaga;
import com.augusto.linketinder.model.pessoa.Candidato;
import com.augusto.linketinder.model.pessoa.Empresa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.augusto.linketinder.dao.connectionProvider.ConnectionProvider;
import com.augusto.linketinder.dao.connectionProvider.H2ConnectionProvider;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class DaoIntegrationTest {

    private ConnectionProvider dataSource;
    private CompetenciaDAO competenciaDao;
    private VagaDAO vagaDao;
    private CandidatoDAO candidatoDao;
    private EmpresaDAO empresaDao;

    @BeforeEach
    void setUp() throws SQLException, IOException {
    dataSource = new H2ConnectionProvider(
        "jdbc:h2:mem:linketinder;MODE=PostgreSQL;DATABASE_TO_UPPER=false;DB_CLOSE_DELAY=-1",
        "sa",
        ""
    );

    competenciaDao = DaoFactory.createCompetenciaDAO(dataSource);
    vagaDao = DaoFactory.createVagaDAO(dataSource, competenciaDao);
    candidatoDao = DaoFactory.createCandidatoDAO(dataSource, competenciaDao);
    empresaDao = DaoFactory.createEmpresaDAO(dataSource, competenciaDao, vagaDao);
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
        candidato.setCep("01000000");
        candidato.setDescricao("Backend dev");
        candidato.setCpf("12345678900");
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
        empresa.setCep("50000000");
        empresa.setPais("BR");
        empresa.setCnpj("12345678000199");
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

        assertEquals("Tech Corp", empresaDao.findById(empresa.getId()).getNome());
        assertEquals("Desenvolvedor Groovy", vagaDao.findById(vaga.getId()).getNome());
        assertEquals(null, empresaDao.findById(9999));
    }

    @Test
    void shouldUpdateAndDeleteCandidato() throws SQLException {
        Candidato candidato = new Candidato();
        candidato.setNome("Ana");
        candidato.setEmail("ana@example.com");
        candidato.setEstado("SP");
        candidato.setCep("01000000");
        candidato.setDescricao("Backend dev");
        candidato.setCpf("12345678900");
        candidato.setIdade(30);
        candidato.setSenha("senha123");
        candidato.setCompetencias(List.of());
        candidatoDao.insert(candidato);

        candidatoDao.updateField(candidato.getId(), "idade", 31);
        candidatoDao.updateField(candidato.getId(), "nome", "Ana Maria");
        Candidato atualizado = candidatoDao.findById(candidato.getId());
        assertEquals(31, atualizado.getIdade());
        assertEquals("Ana Maria", atualizado.getNome());
        assertEquals(candidato.getId(), candidatoDao.findCredenciaisByCpf("12345678900").get("id"));
        assertEquals(null, candidatoDao.findCredenciaisByCpf("00000000000"));

        candidatoDao.delete(candidato.getId());
        assertEquals(0, candidatoDao.listAll().size());
    }

    @Test
    void shouldDeleteEmpresaWithItsVagas() throws SQLException {
        Competencia sql = new Competencia("SQL");
        competenciaDao.insert(sql);

        Empresa empresa = new Empresa();
        empresa.setNome("Tech Corp");
        empresa.setEmail("contato@techcorp.com");
        empresa.setEstado("PE");
        empresa.setCep("50000000");
        empresa.setPais("BR");
        empresa.setCnpj("12345678000199");
        empresa.setDescricao("Consultoria de tecnologia");
        empresa.setSenha("senhaSegura");
        empresa.setCompetencias(List.of(sql));
        empresa.setListaVaga(List.of(new Vaga(0, "Dev", "desc", "Recife", "PE", List.of(sql))));
        empresaDao.insert(empresa);

        vagaDao.updateField(empresa.getListaVaga().get(0).getId(), "cidade", "Olinda");
        assertEquals("Olinda", vagaDao.listAll().get(0).getCidade());
        assertEquals("BR", empresaDao.findById(empresa.getId()).getPais());

        empresaDao.delete(empresa.getId());
        assertEquals(0, empresaDao.listAll().size());
        assertEquals(0, vagaDao.listAll().size());
        assertEquals(0, competenciaDao.mapByVaga().size());
        assertEquals(0, competenciaDao.mapByEmpresa().size());
    }

    @Test
    void shouldNotKeepEmpresaWhenVagaInsertFails() {
        Empresa empresa = new Empresa();
        empresa.setNome("Tech Corp");
        empresa.setEmail("contato@techcorp.com");
        empresa.setEstado("PE");
        empresa.setCep("50000000");
        empresa.setPais("BR");
        empresa.setCnpj("12345678000199");
        empresa.setDescricao("Consultoria de tecnologia");
        empresa.setSenha("senhaSegura");
        empresa.setCompetencias(List.of());
        empresa.setListaVaga(List.of(new Vaga(0, null, "desc", "Recife", "PE", List.of())));

        assertThrows(Exception.class, () -> empresaDao.insert(empresa));
        assertDoesNotThrow(() -> assertEquals(0, empresaDao.listAll().size()));
    }

    private void recreateSchema() throws SQLException, IOException {
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

            String script = Files.readString(Path.of("../database/scriptEstruturaBasica.sql"));
            for (String sql : script.replaceAll("(?m)^--.*$", "").split(";")) {
                if (!sql.isBlank()) {
                    statement.execute(sql);
                }
            }
        }
    }
}
