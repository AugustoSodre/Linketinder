package com.augusto.linketinder.API

import com.augusto.linketinder.model.Competencia
import com.augusto.linketinder.model.Vaga
import com.augusto.linketinder.service.VagaService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler

class VagaControllerAPI implements HttpHandler {
    
    private final VagaService vagaService
    private final ObjectMapper objectMapper

    VagaControllerAPI() {
        this.vagaService = new VagaService()
        this.objectMapper = new ObjectMapper()
        
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT)
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
    }

    @Override
    void handle(HttpExchange exchange) throws IOException {
        println "[${new Date()}] ${exchange.requestMethod} ${exchange.requestURI}"
        
        addCorsHeaders(exchange)

        try {
            def method = exchange.requestMethod
            def path = exchange.requestURI.path.replaceAll('/+$', '') // Remove trailing slashes
            
            switch (method) {
                case "GET":
                    handleGet(exchange, path)
                    break
                case "POST":
                    handlePost(exchange, path)
                    break
                case "PUT":
                    handlePut(exchange, path)
                    break
                case "DELETE":
                    handleDelete(exchange, path)
                    break
                default:
                    sendErrorResponse(exchange, 405, "Method Not Allowed")
            }
        } catch (Exception e) {
            println "Erro ao processar requisição: ${e.message}"
            e.printStackTrace()
            sendErrorResponse(exchange, 500, "Erro interno.")
        }
    }

    private void handleGet(HttpExchange exchange, String path) {
        if (path == "/vagas") {
            def vagas = vagaService.getVagaList()
            def vagasJson = vagas.collect { vaga ->
                vagaToJson(vaga)
            }
            sendJsonResponse(exchange, 200, vagasJson)
        } else if (path.matches('/vagas/\\d+')) {
            def id = path.split('/').last().toInteger()
            handleGetById(exchange, id)
        } else {
            sendErrorResponse(exchange, 404, "Rota não encontrada")
        }
    }

    private void handleGetById(HttpExchange exchange, int id) {
        def vagas = vagaService.getVagaList()
        def vaga = vagas.find { it.id == id }
        
        if (vaga) {
            def vagaJson = vagaToJson(vaga)
            sendJsonResponse(exchange, 200, vagaJson)
        } else {
            sendErrorResponse(exchange, 404, "Vaga não encontrada")
        }
    }

    private void handlePost(HttpExchange exchange, String path) {
        if (path == "/vagas") {
            def body = getRequestBody(exchange)
            def data = objectMapper.readValue(body, Map.class)

            def vaga = mapToVaga(data)
            vagaService.createVaga(vaga)
            
            sendJsonResponse(exchange, 201, [message: "Vaga criada com sucesso", data: data])
        } else {
            sendErrorResponse(exchange, 404, "Rota não encontrada")
        }
    }

    private void handlePut(HttpExchange exchange, String path) {
        if (path.matches('/vagas/\\d+')) {
            int id = path.split('/').last().toInteger()

            def body = getRequestBody(exchange)
            def data = objectMapper.readValue(body, Map.class)
            
            if (!vagaExists(id)) {
                sendErrorResponse(exchange, 404, "Vaga não encontrada")
                return
            }
            
            def camposValidos = vagaService.getCamposVaga()
            def camposAtualizados = []
            
            data.each { key, value ->
                if (key != 'id' && key != 'competencias') {
                    // Mapeia o nome do campo do JSON para o nome da coluna no BD
                    def nomeColuna = camposValidos[key] ?: key
                    
                    try {
                        vagaService.updateVaga(id, nomeColuna as String, value)
                        camposAtualizados << key
                    } catch (Exception e) {
                        println "Erro ao atualizar campo '${key}': ${e.message}"
                    }
                }
            }
            
            sendJsonResponse(exchange, 200, [
                message: "Vaga atualizada com sucesso", 
                id: id, 
                camposAtualizados: camposAtualizados
            ])
        } else {
            sendErrorResponse(exchange, 404, "Rota não encontrada")
        }
    }

    private void handleDelete(HttpExchange exchange, String path) {
        if (path.matches('/vagas/\\d+')) {
            def id = path.split('/').last().toInteger()
            
            if (!vagaExists(id)) {
                sendErrorResponse(exchange, 404, "Vaga não encontrada")
                return
            }
            
            vagaService.deleteVaga(id)

            sendJsonResponse(exchange, 200, [message: "Vaga removida", id: id])
        } else {
            sendErrorResponse(exchange, 404, "Rota não encontrada")
        }
    }

    private String getRequestBody(HttpExchange exchange) {
        return exchange.requestBody.text
    }

    private void addCorsHeaders(HttpExchange exchange) {
        exchange.responseHeaders.add("Access-Control-Allow-Origin", "*")
        exchange.responseHeaders.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
        exchange.responseHeaders.add("Access-Control-Allow-Headers", "Content-Type, Authorization")
    }

    private void sendJsonResponse(HttpExchange exchange, int status, Object data) {
        def response = objectMapper.writeValueAsString(data)
        sendResponse(exchange, status, response)
    }

    private void sendErrorResponse(HttpExchange exchange, int status, String message) {
        def response = objectMapper.writeValueAsString([error: message, status: status])
        sendResponse(exchange, status, response)
    }

    private void sendResponse(HttpExchange exchange, int status, String response) {
        exchange.responseHeaders.add("Content-Type", "application/json; charset=UTF-8")
        
        byte[] bytes = response.getBytes("UTF-8")
        exchange.sendResponseHeaders(status, bytes.length)
        exchange.responseBody.withCloseable { it.write(bytes) }
        
        println "Resposta enviada: ${status} - ${bytes.length} bytes"
    }

    private boolean vagaExists(int id) {
        def vagas = vagaService.getVagaList()
        return vagas.any { it.id == id }
    }

    private Map vagaToJson(Vaga vaga) {
        return [
            id: vaga.id,
            id_empresa: vaga.id_empresa,
            nome: vaga.nome,
            descricao: vaga.descricao,
            cidade: vaga.cidade,
            estado: vaga.estado,
            competencias: vaga.competencias?.collect { comp ->
                [id: comp.id, nome: comp.nome]
            } ?: []
        ]
    }

    private Vaga mapToVaga(Map data) {
        def vaga = new Vaga()
        vaga.id_empresa = data.id_empresa as Integer
        vaga.nome = data.nome
        vaga.descricao = data.descricao
        vaga.cidade = data.cidade
        vaga.estado = data.estado
        
        // Converte a lista de competências de Map para objetos Competencia
        if (data.competencias && data.competencias instanceof List) {
            vaga.competencias = data.competencias.collect { comp ->
                if (comp instanceof Map) {
                    new Competencia(comp.id as Integer, comp.nome)
                } else {
                    comp as Competencia
                }
            }
        } else {
            vaga.competencias = []
        }
        
        return vaga
    }
}
