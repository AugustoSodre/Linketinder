package com.augusto.linketinder.API

import com.augusto.linketinder.dao.connectionProvider.H2ConnectionProvider
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import java.nio.file.Files
import java.nio.file.Path

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertTrue

class ApiIntegrationTest {

    static final int PORT = 18080
    static HTTPServerAPI server

    @BeforeAll
    static void sobeServidor() {
        System.setProperty('DB_PROVIDER', 'h2')
        server = new HTTPServerAPI(PORT)
        server.start()
    }

    @AfterAll
    static void derrubaServidor() {
        server.stop()
        System.clearProperty('DB_PROVIDER')
    }

    @BeforeEach
    void recriaBanco() {
        new H2ConnectionProvider().connection.withCloseable { conn ->
            conn.createStatement().withCloseable { st ->
                ['competencia_candidato', 'competencia_empresa', 'competencia_vaga', 'vaga', 'empresa', 'candidato', 'competencia']
                        .each { st.execute("DROP TABLE IF EXISTS $it") }
                Files.readString(Path.of('../database/scriptEstruturaBasica.sql'))
                        .replaceAll('(?m)^--.*$', '').split(';').findAll { it.trim() }.each { st.execute(it) }
                st.execute("INSERT INTO competencia (nome) VALUES ('Java'), ('Groovy')")
            }
        }
    }

    static Map req(String method, String path, Map body = null, String token = null) {
        def conn = (HttpURLConnection) new URL("http://localhost:$PORT$path").openConnection()
        conn.requestMethod = method
        if (token) conn.setRequestProperty('Authorization', "Bearer $token")
        if (body != null) {
            conn.doOutput = true
            conn.outputStream.withCloseable { it.write(JsonOutput.toJson(body).getBytes('UTF-8')) }
        }
        def stream = conn.responseCode < 400 ? conn.inputStream : conn.errorStream
        def text = stream?.getText('UTF-8')
        return [status: conn.responseCode, json: text ? new JsonSlurper().parseText(text) : null]
    }

    static String cadastraEmpresaELoga(String cnpj = '12345678000199') {
        def criada = req('POST', '/empresas', [nome: 'Tech Corp', email: 'rh@tech.com', estado: 'PE', cep: '50000000',
                                               pais: 'BR', cnpj: cnpj, descricao: 'Consultoria', senha: '123456', competencias: []])
        assertEquals(201, criada.status, criada.json as String)
        return req('POST', '/login', [cnpj: cnpj, senha: '123456']).json.token
    }

    @Test
    void postVagaDeveDevolverAVagaCriadaComId() {
        def token = cadastraEmpresaELoga()
        def login = req('POST', '/login', [cnpj: '12345678000199', senha: '123456']).json

        def resp = req('POST', '/vagas', [id_empresa: login.id, nome: 'Dev Groovy', descricao: 'Backend', cidade: 'Recife',
                                         estado: 'PE', competencias: [[id: 1, nome: 'Java']], extra: 'ignorar'], token)

        assertEquals(201, resp.status)
        def vaga = resp.json.data
        assertTrue(vaga.id > 0)
        assertEquals([id: vaga.id, id_empresa: login.id, nome: 'Dev Groovy', descricao: 'Backend', cidade: 'Recife',
                      estado: 'PE', competencias: [[id: 1, nome: 'Java']]], vaga)
        assertEquals(vaga, req('GET', "/vagas/${vaga.id}").json)
    }
}
