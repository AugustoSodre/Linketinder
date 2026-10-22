package com.augusto.linketinder.API

import com.augusto.linketinder.model.Competencia
import com.augusto.linketinder.model.Vaga
import com.augusto.linketinder.service.ValidateService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler

import java.sql.SQLException

abstract class BaseControllerAPI implements HttpHandler {

    protected final ObjectMapper objectMapper = new ObjectMapper()
    protected final ValidateService validateService = new ValidateService()

    BaseControllerAPI() {
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
    }

    @Override
    void handle(HttpExchange exchange) throws IOException {
        println "[${new Date()}] ${exchange.requestMethod} ${exchange.requestURI}"
        addCorsHeaders(exchange)

        try {
            String method = exchange.requestMethod
            String path = exchange.requestURI.path.replaceAll('/+$', '')

            if (method == "OPTIONS") {
                exchange.sendResponseHeaders(204, -1)
                exchange.close()
                return
            }

            int status = verificarAcesso(exchange, method, path)
            if (status) {
                sendErrorResponse(exchange, status, status == 401 ? "Não autorizado" : "Acesso negado")
                return
            }

            switch (method) {
                case "GET": handleGet(exchange, path); break
                case "POST": handlePost(exchange, path); break
                case "PUT": handlePut(exchange, path); break
                case "DELETE": handleDelete(exchange, path); break
                default: sendMethodNotAllowed(exchange)
            }
        } catch (Exception e) {
            println "Erro ao processar requisição: ${e.message}"
            e.printStackTrace()
            sendErrorResponse(exchange, 500, "Erro interno.")
        }
    }

    protected int verificarAcesso(HttpExchange exchange, String method, String path) {
        return 0
    }

    protected void handleGet(HttpExchange exchange, String path) { sendMethodNotAllowed(exchange) }

    protected void handlePost(HttpExchange exchange, String path) { sendMethodNotAllowed(exchange) }

    protected void handlePut(HttpExchange exchange, String path) { sendMethodNotAllowed(exchange) }

    protected void handleDelete(HttpExchange exchange, String path) { sendMethodNotAllowed(exchange) }

    protected void sendMethodNotAllowed(HttpExchange exchange) {
        sendErrorResponse(exchange, 405, "Method Not Allowed")
    }

    protected void sendNotFound(HttpExchange exchange) {
        sendErrorResponse(exchange, 404, "Rota não encontrada")
    }

    protected static Integer idDoPath(String path) {
        String id = path.find('\\d+$')
        return id ? id.toInteger() : null
    }

    protected static Map vagaToJson(Vaga vaga) {
        return [
            id: vaga.id,
            id_empresa: vaga.id_empresa,
            nome: vaga.nome,
            descricao: vaga.descricao,
            cidade: vaga.cidade,
            estado: vaga.estado,
            competencias: competenciasToJson(vaga.competencias)
        ]
    }

    protected static List<Map> competenciasToJson(List<Competencia> competencias) {
        return (competencias ?: []).collect { [id: it.id, nome: it.nome] }
    }

    protected static Vaga toVaga(Map data) {
        def vaga = new Vaga()
        vaga.id_empresa = (data.id_empresa ?: 0) as Integer
        vaga.nome = data.nome
        vaga.descricao = data.descricao
        vaga.cidade = data.cidade
        vaga.estado = data.estado
        vaga.competencias = toCompetencias(data.competencias)
        return vaga
    }

    protected static List<Competencia> toCompetencias(Object lista) {
        if (!(lista instanceof List)) {
            return []
        }
        return lista.collect { it instanceof Map ? new Competencia(it.id as Integer, it.nome as String) : it as Competencia }
    }

    protected boolean rejeitaInvalidos(HttpExchange exchange, Map data, List<String> obrigatorios) {
        def erros = validateService.validarCampos(data, obrigatorios)
        if (erros) {
            sendErrorResponse(exchange, 400, erros.join('; '))
        }
        return erros as boolean
    }

    protected static boolean isDuplicado(Throwable e) {
        for (def causa = e; causa != null; causa = causa.cause) {
            if (causa instanceof SQLException && causa.SQLState == '23505') {
                return true
            }
        }
        return false
    }

    protected String getRequestBody(HttpExchange exchange) {
        return exchange.requestBody.text
    }

    protected void addCorsHeaders(HttpExchange exchange) {
        exchange.responseHeaders.add("Access-Control-Allow-Origin", "*")
        exchange.responseHeaders.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
        exchange.responseHeaders.add("Access-Control-Allow-Headers", "Content-Type, Authorization")
    }

    protected void sendJsonResponse(HttpExchange exchange, int status, Object data) {
        def response = objectMapper.writeValueAsString(data)
        sendResponse(exchange, status, response)
    }

    protected void sendErrorResponse(HttpExchange exchange, int status, String message) {
        def response = objectMapper.writeValueAsString([error: message, status: status])
        sendResponse(exchange, status, response)
    }

    protected void sendResponse(HttpExchange exchange, int status, String response) {
        exchange.responseHeaders.add("Content-Type", "application/json; charset=UTF-8")

        byte[] bytes = response.getBytes("UTF-8")
        exchange.sendResponseHeaders(status, bytes.length)
        exchange.responseBody.withCloseable { it.write(bytes) }

        println "Resposta enviada: ${status} - ${bytes.length} bytes"
    }
}
