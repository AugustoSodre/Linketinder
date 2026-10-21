package com.augusto.linketinder.API

import com.augusto.linketinder.service.CompetenciaService
import com.fasterxml.jackson.databind.ObjectMapper
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler

// Somente leitura: o frontend precisa da lista (id + nome) para montar os checkboxes
class CompetenciaControllerAPI implements HttpHandler {

    private final CompetenciaService competenciaService = new CompetenciaService()
    private final ObjectMapper objectMapper = new ObjectMapper()

    @Override
    void handle(HttpExchange exchange) throws IOException {
        println "[${new Date()}] ${exchange.requestMethod} ${exchange.requestURI}"

        exchange.responseHeaders.add("Access-Control-Allow-Origin", "*")
        exchange.responseHeaders.add("Access-Control-Allow-Methods", "GET, OPTIONS")
        exchange.responseHeaders.add("Access-Control-Allow-Headers", "Content-Type, Authorization")

        try {
            if (exchange.requestMethod == "OPTIONS") {
                exchange.sendResponseHeaders(204, -1)
                exchange.close()
                return
            }
            if (exchange.requestMethod != "GET") {
                sendJsonResponse(exchange, 405, [error: "Method Not Allowed", status: 405])
                return
            }

            def competencias = competenciaService.getCompetenciaList().collect { [id: it.id, nome: it.nome] }
            sendJsonResponse(exchange, 200, competencias)
        } catch (Exception e) {
            println "Erro ao listar competências: ${e.message}"
            sendJsonResponse(exchange, 500, [error: "Erro interno.", status: 500])
        }
    }

    private void sendJsonResponse(HttpExchange exchange, int status, Object data) {
        exchange.responseHeaders.add("Content-Type", "application/json; charset=UTF-8")

        byte[] bytes = objectMapper.writeValueAsString(data).getBytes("UTF-8")
        exchange.sendResponseHeaders(status, bytes.length)
        exchange.responseBody.withCloseable { it.write(bytes) }
    }
}
