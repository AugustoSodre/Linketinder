package com.augusto.linketinder.API

import com.augusto.linketinder.service.AuthService
import com.augusto.linketinder.service.CompetenciaService
import com.sun.net.httpserver.HttpExchange

class CompetenciaControllerAPI extends BaseControllerAPI {

    private final CompetenciaService competenciaService = new CompetenciaService()

    @Override
    protected int verificarAcesso(HttpExchange exchange, String method, String path) {
        return method == "POST" && !AuthService.getSession(exchange) ? 401 : 0
    }

    @Override
    protected void handleGet(HttpExchange exchange, String path) {
        def competencias = competenciaService.getCompetenciaList().collect { [id: it.id, nome: it.nome] }
        sendJsonResponse(exchange, 200, competencias)
    }

    @Override
    protected void handlePost(HttpExchange exchange, String path) {
        if (path != "/competencias") {
            sendNotFound(exchange)
            return
        }
        Map data = objectMapper.readValue(getRequestBody(exchange), Map.class)
        if (rejeitaInvalidos(exchange, data, ['nome'])) {
            return
        }
        String nome = (data.nome as String).trim()
        if (competenciaService.existeNome(nome)) {
            sendErrorResponse(exchange, 409, "Competência já cadastrada")
            return
        }
        def competencia = competenciaService.criar(nome)
        sendJsonResponse(exchange, 201, [message: "Competência criada com sucesso", data: [id: competencia.id, nome: competencia.nome]])
    }
}
