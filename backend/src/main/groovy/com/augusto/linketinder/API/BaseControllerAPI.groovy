package com.augusto.linketinder.API

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler

abstract class BaseControllerAPI implements HttpHandler {

    protected final ObjectMapper objectMapper = new ObjectMapper()

    BaseControllerAPI() {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT)
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
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
