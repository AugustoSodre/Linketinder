package com.augusto.linketinder.API

import com.augusto.linketinder.service.AuthService
import com.fasterxml.jackson.databind.ObjectMapper
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler

class LoginControllerAPI implements HttpHandler {

    private final AuthService authService
    private final ObjectMapper objectMapper = new ObjectMapper()

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
                sendJsonResponse(exchange, 405, [error: "Method Not Allowed", status: 405])
                return
            }

            def data = objectMapper.readValue(exchange.requestBody.text, Map.class)
            def resultado = authService.login(data.cpf as String, data.cnpj as String, data.senha as String)

            if (resultado) {
                sendJsonResponse(exchange, 200, resultado)
            } else {
                sendJsonResponse(exchange, 401, [error: "Credenciais inválidas", status: 401])
            }
        } catch (Exception e) {
            println "Erro ao processar login: ${e.message}"
            e.printStackTrace()
            sendJsonResponse(exchange, 500, [error: "Erro interno.", status: 500])
        }
    }

    private void addCorsHeaders(HttpExchange exchange) {
        exchange.responseHeaders.add("Access-Control-Allow-Origin", "*")
        exchange.responseHeaders.add("Access-Control-Allow-Methods", "POST, OPTIONS")
        exchange.responseHeaders.add("Access-Control-Allow-Headers", "Content-Type, Authorization")
    }

    private void sendJsonResponse(HttpExchange exchange, int status, Object data) {
        exchange.responseHeaders.add("Content-Type", "application/json; charset=UTF-8")

        byte[] bytes = objectMapper.writeValueAsString(data).getBytes("UTF-8")
        exchange.sendResponseHeaders(status, bytes.length)
        exchange.responseBody.withCloseable { it.write(bytes) }
    }
}
