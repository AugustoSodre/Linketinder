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

    static String cadastraCandidatoELoga(String cpf = '12345678901') {
        def criado = req('POST', '/candidatos', [nome: 'Jim', email: 'jim@office.com', estado: 'PA', cep: '18500000', idade: 30,
                                                 cpf: cpf, descricao: 'Vendedor', senha: '123456', competencias: [[id: 1, nome: 'Java']]])
        assertEquals(201, criado.status, criado.json as String)
        return req('POST', '/login', [cpf: cpf, senha: '123456']).json.token
    }

    static Map criaVaga(String token, int idEmpresa) {
        return req('POST', '/vagas', [id_empresa: idEmpresa, nome: 'Dev', descricao: 'Backend', cidade: 'Recife', estado: 'PE',
                                      competencias: []], token).json.data
    }

    static int idDoToken(String token, String doc, String valor) {
        return req('POST', '/login', [(doc): valor, senha: '123456']).json.id
    }

    @Test
    void crudDeCandidato() {
        def token = cadastraCandidatoELoga()
        def outro = cadastraCandidatoELoga('98765432100')
        int id = idDoToken(token, 'cpf', '12345678901')

        assertEquals(2, req('GET', '/candidatos').json.size())
        def candidato = req('GET', "/candidatos/$id").json
        assertEquals(['id', 'nome', 'email', 'estado', 'cep', 'cpf', 'idade', 'descricao', 'competencias'], candidato.keySet() as List)
        assertEquals([[id: 1, nome: 'Java']], candidato.competencias)

        verificaCrud('/candidatos', id, token, outro, [nome: 'Jim Halpert'], 'Candidato', 'o', [cpf: '98765432100'])
    }

    @Test
    void crudDeEmpresa() {
        def token = cadastraEmpresaELoga()
        def outro = cadastraEmpresaELoga('22222222000122')
        int id = idDoToken(token, 'cnpj', '12345678000199')

        assertEquals(['id', 'nome', 'email', 'estado', 'cep', 'pais', 'cnpj', 'descricao', 'competencias', 'vagas'],
                req('GET', "/empresas/$id").json.keySet() as List)

        verificaCrud('/empresas', id, token, outro, [descricao: 'Software'], 'Empresa', 'a', [cnpj: '22222222000122'])
    }

    @Test
    void crudDeVaga() {
        def token = cadastraEmpresaELoga()
        def outro = cadastraEmpresaELoga('22222222000122')
        int id = criaVaga(token, idDoToken(token, 'cnpj', '12345678000199')).id

        assertEquals(1, req('GET', '/vagas').json.size())
        assertEquals(401, req('POST', '/vagas', [nome: 'x', descricao: 'y']).status)
        assertEquals(403, req('POST', '/vagas', [id_empresa: 999, nome: 'x', descricao: 'y'], token).status)

        verificaCrud('/vagas', id, token, outro, [cidade: 'Olinda'], 'Vaga', 'a', null)
    }

    static void verificaCrud(String base, int id, String token, String outroToken, Map alteracao, String rotulo, String genero, Map duplicado) {
        assertEquals([status: 404, json: [error: "$rotulo não encontrad$genero" as String, status: 404]], req('GET', "$base/9999"))
        assertEquals([status: 404, json: [error: 'Rota não encontrada', status: 404]], req('GET', "$base/abc"))

        assertEquals(401, req('PUT', "$base/$id", alteracao).status)
        assertEquals(401, req('DELETE', "$base/$id").status)
        assertEquals(403, req('PUT', "$base/$id", alteracao, outroToken).status)
        assertEquals(403, req('DELETE', "$base/$id", null, outroToken).status)

        assertEquals([error: 'Campos inválidos: inventado', status: 400], req('PUT', "$base/$id", [inventado: 1], token).json)
        assertEquals([error: 'Estado inválido (use a sigla, ex: SP)', status: 400], req('PUT', "$base/$id", [estado: 'XX'], token).json)
        if (duplicado) {
            assertEquals([error: 'Valor já cadastrado por outro usuário', status: 409], req('PUT', "$base/$id", duplicado, token).json)
        }

        def atualizado = req('PUT', "$base/$id", alteracao + [id: 1, competencias: []], token)
        assertEquals([status: 200, json: [message: "$rotulo atualizad$genero com sucesso" as String, id: id, camposAtualizados: alteracao.keySet() as List]], atualizado)
        alteracao.each { k, v -> assertEquals(v, req('GET', "$base/$id").json[k]) }

        assertEquals([status: 200, json: [message: "$rotulo removid$genero" as String, id: id]], req('DELETE', "$base/$id", null, token))
        assertEquals(404, req('GET', "$base/$id").status)
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
