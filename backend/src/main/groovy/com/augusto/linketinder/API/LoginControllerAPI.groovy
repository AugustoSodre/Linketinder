package com.augusto.linketinder.API

import com.augusto.linketinder.service.AuthService
import com.sun.net.httpserver.HttpExchange

class LoginControllerAPI extends BaseControllerAPI {

    private final AuthService authService

    LoginControllerAPI(AuthService authService) {
        this.authService = authService
    }

    LoginControllerAPI() {
        this(new AuthService())
    }

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
            if (exchange.requestMethod != "POST") {
                sendErrorResponse(exchange, 405, "Method Not Allowed")
                return
            }

            def data = objectMapper.readValue(exchange.requestBody.text, Map.class)
            def resultado = authService.login(data.cpf as String, data.cnpj as String, data.senha as String)

            if (resultado) {
                sendJsonResponse(exchange, 200, resultado)
            } else {
                sendErrorResponse(exchange, 401, "Credenciais inválidas")
            }
        } catch (Exception e) {
            println "Erro ao processar login: ${e.message}"
            e.printStackTrace()
            sendErrorResponse(exchange, 500, "Erro interno.")
        }
    }
}
