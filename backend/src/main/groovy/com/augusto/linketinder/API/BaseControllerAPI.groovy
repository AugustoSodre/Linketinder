package com.augusto.linketinder.API

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

    // Responde 400 e retorna true se algum campo for inválido
    protected boolean rejeitaInvalidos(HttpExchange exchange, Map data, List<String> obrigatorios) {
        def erros = validateService.validarCampos(data, obrigatorios)
        if (erros) {
            sendErrorResponse(exchange, 400, erros.join('; '))
        }
        return erros as boolean
    }

    // 23505 = unique_violation (mesmo código no PostgreSQL e no H2)
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
