package com.augusto.linketinder.API

import com.augusto.linketinder.service.CompetenciaService
import com.sun.net.httpserver.HttpExchange

class CompetenciaControllerAPI extends BaseControllerAPI {

    private final CompetenciaService competenciaService = new CompetenciaService()

    @Override
    protected void handleGet(HttpExchange exchange, String path) {
        def competencias = competenciaService.getCompetenciaList().collect { [id: it.id, nome: it.nome] }
        sendJsonResponse(exchange, 200, competencias)
    }
}
