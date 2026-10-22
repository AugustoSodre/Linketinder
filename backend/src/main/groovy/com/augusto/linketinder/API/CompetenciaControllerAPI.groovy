package com.augusto.linketinder.API

import com.augusto.linketinder.service.CompetenciaService
import com.sun.net.httpserver.HttpExchange

class CompetenciaControllerAPI extends BaseControllerAPI {

    private final CompetenciaService competenciaService = new CompetenciaService()

    @Override
    void handle(HttpExchange exchange) throws IOException {
        println "[${new Date()}] ${exchange.requestMethod} ${exchange.requestURI}"

        addCorsHeaders(exchange)

        try {
            if (exchange.requestMethod == "OPTIONS") {
                exchange.sendResponseHeaders(204, -1)
                exchange.close()
                return
            }
            if (exchange.requestMethod != "GET") {
                sendErrorResponse(exchange, 405, "Method Not Allowed")
                return
            }

            def competencias = competenciaService.getCompetenciaList().collect { [id: it.id, nome: it.nome] }
            sendJsonResponse(exchange, 200, competencias)
        } catch (Exception e) {
            println "Erro ao listar competências: ${e.message}"
            sendErrorResponse(exchange, 500, "Erro interno.")
        }
    }
}
