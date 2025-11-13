package com.augusto.linketinder.API

import com.augusto.linketinder.model.Competencia
import com.augusto.linketinder.model.pessoa.Candidato
import com.augusto.linketinder.service.CandidatoService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler

class CandidatoControllerAPI implements HttpHandler {
    
    private final CandidatoService candidatoService
    private final ObjectMapper objectMapper

    CandidatoControllerAPI() {
        this.candidatoService = new CandidatoService()
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
        if (path == "/candidatos") {
            def candidatos = candidatoService.getCandidatoList()
            def candidatosJson = candidatos.collect { candidato ->
                candidatoToJson(candidato)
            }
            sendJsonResponse(exchange, 200, candidatosJson)
        } else if (path.matches('/candidatos/\\d+')) {
            def id = path.split('/').last().toInteger()
            handleGetById(exchange, id)
        } else {
            sendErrorResponse(exchange, 404, "Rota não encontrada")
        }
    }

    private void handleGetById(HttpExchange exchange, int id) {
        def candidatos = candidatoService.getCandidatoList()
        def candidato = candidatos.find { it.id == id }
        
        if (candidato) {
            def candidatoJson = candidatoToJson(candidato)
            sendJsonResponse(exchange, 200, candidatoJson)
        } else {
            sendErrorResponse(exchange, 404, "Candidato não encontrado")
        }
    }

    private void handlePost(HttpExchange exchange, String path) {
        if (path == "/candidatos") {
            def body = getRequestBody(exchange)
            def data = objectMapper.readValue(body, Map.class)

            def candidato = mapToCandidato(data)
            candidatoService.createCandidato(candidato)
            
            sendJsonResponse(exchange, 201, [message: "Candidato criado com sucesso", data: data])
        } else {
            sendErrorResponse(exchange, 404, "Rota não encontrada")
        }
    }

    private void handlePut(HttpExchange exchange, String path) {
        if (path.matches('/candidatos/\\d+')) {
            int id = path.split('/').last().toInteger()

            def body = getRequestBody(exchange)
            def data = objectMapper.readValue(body, Map.class)
            
            if (!candidatoExists(id)) {
                sendErrorResponse(exchange, 404, "Candidato não encontrado")
                return
            }
            
            def camposValidos = candidatoService.getCamposCandidato()
            def camposAtualizados = []
            
            data.each { key, value ->
                if (key != 'id' && key != 'competencias') {
                    // Mapeia o nome do campo do JSON para o nome da coluna no BD
                    def nomeColuna = camposValidos[key] ?: key
                    
                    try {
                        candidatoService.updateCandidato(id, nomeColuna as String, value)
                        camposAtualizados << key
                    } catch (Exception e) {
                        println "Erro ao atualizar campo '${key}': ${e.message}"
                    }
                }
            }
            
            sendJsonResponse(exchange, 200, [
                message: "Candidato atualizado com sucesso", 
                id: id, 
                camposAtualizados: camposAtualizados
            ])
        } else {
            sendErrorResponse(exchange, 404, "Rota não encontrada")
        }
    }

    private void handleDelete(HttpExchange exchange, String path) {
        if (path.matches('/candidatos/\\d+')) {
            def id = path.split('/').last().toInteger()
            
            if (!candidatoExists(id)) {
                sendErrorResponse(exchange, 404, "Candidato não encontrado")
                return
            }
            
            candidatoService.deleteCandidato(id)

            sendJsonResponse(exchange, 200, [message: "Candidato removido", id: id])
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

    private boolean candidatoExists(int id) {
        def candidatos = candidatoService.getCandidatoList()
        return candidatos.any { it.id == id }
    }

    private Map candidatoToJson(Candidato candidato) {
        return [
            id: candidato.id,
            nome: candidato.nome,
            email: candidato.email,
            estado: candidato.estado,
            cep: candidato.cep,
            cpf: candidato.cpf,
            idade: candidato.idade,
            descricao: candidato.descricao,
            competencias: candidato.competencias?.collect { comp ->
                [id: comp.id, nome: comp.nome]
            } ?: []
        ]
    }

    private Candidato mapToCandidato(Map data) {
        def candidato = new Candidato()
        candidato.nome = data.nome
        candidato.email = data.email
        candidato.estado = data.estado
        candidato.cep = data.cep
        candidato.cpf = data.cpf
        candidato.idade = data.idade as Integer
        candidato.descricao = data.descricao
        candidato.senha = data.senha
        
        if (data.competencias && data.competencias instanceof List) {
            candidato.competencias = data.competencias.collect { comp ->
                if (comp instanceof Map) {
                    new Competencia(comp.id as Integer, comp.nome)
                } else {
                    comp as Competencia
                }
            }
        } else {
            candidato.competencias = []
        }
        
        return candidato
    }
}
