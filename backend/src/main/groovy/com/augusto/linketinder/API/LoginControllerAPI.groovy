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
    protected void handlePost(HttpExchange exchange, String path) {
        def data = objectMapper.readValue(getRequestBody(exchange), Map.class)
        def resultado = authService.login(data.cpf as String, data.cnpj as String, data.senha as String)

        if (resultado?.bloqueado) {
            sendErrorResponse(exchange, 429, "Muitas tentativas de login. Tente novamente em alguns minutos.")
        } else if (resultado) {
            sendJsonResponse(exchange, 200, resultado)
        } else {
            sendErrorResponse(exchange, 401, "Credenciais inválidas")
        }
    }
}
