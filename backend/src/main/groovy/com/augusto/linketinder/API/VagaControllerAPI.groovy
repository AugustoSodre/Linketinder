package com.augusto.linketinder.API

import com.augusto.linketinder.model.Competencia
import com.augusto.linketinder.model.Vaga
import com.augusto.linketinder.service.AuthService
import com.augusto.linketinder.service.VagaService
import com.sun.net.httpserver.HttpExchange

class VagaControllerAPI extends BaseControllerAPI {
    
    private final VagaService vagaService

    VagaControllerAPI() {
        this.vagaService = new VagaService()
    }

    @Override
    void handle(HttpExchange exchange) throws IOException {
        println "[${new Date()}] ${exchange.requestMethod} ${exchange.requestURI}"
        
        addCorsHeaders(exchange)

        try {
            def method = exchange.requestMethod
            def path = exchange.requestURI.path.replaceAll('/+$', '')

            if (method == "OPTIONS") {
                exchange.sendResponseHeaders(204, -1)
                exchange.close()
                return
            }
            if (method in ["POST", "PUT", "DELETE"]) {
                int status = AuthService.checkAccess(exchange, "empresa", null)
                if (status) {
                    sendErrorResponse(exchange, status, status == 401 ? "Não autorizado" : "Acesso negado")
                    return
                }
            }

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
        def vaga = vagaService.getVagaById(id)
        
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

            if (rejeitaInvalidos(exchange, data, ['nome', 'descricao'])) {
                return
            }

            def vaga = mapToVaga(data)
            if (vaga.id_empresa != AuthService.getSession(exchange).id) {
                sendErrorResponse(exchange, 403, "Acesso negado")
                return
            }
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
            if (!isDonaDaVaga(exchange, id)) {
                sendErrorResponse(exchange, 403, "Acesso negado")
                return
            }

            def camposValidos = vagaService.getCamposVaga()
            def campos = data.findAll { key, value -> key != 'id' && key != 'competencias' && key != 'id_empresa' }

            def invalidos = campos.keySet().findAll { !((camposValidos[it] ?: it) in camposValidos.values()) }
            if (invalidos) {
                sendErrorResponse(exchange, 400, "Campos inválidos: ${invalidos.join(', ')}")
                return
            }
            if (rejeitaInvalidos(exchange, campos, campos.keySet() as List<String>)) {
                return
            }

            try {
                campos.each { key, value ->
                    vagaService.updateVaga(id, (camposValidos[key] ?: key) as String, value)
                }
            } catch (Exception e) {
                if (!isDuplicado(e)) throw e
                sendErrorResponse(exchange, 409, "Valor já cadastrado por outro usuário")
                return
            }
            def camposAtualizados = campos.keySet()

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
            if (!isDonaDaVaga(exchange, id)) {
                sendErrorResponse(exchange, 403, "Acesso negado")
                return
            }

            vagaService.deleteVaga(id)

            sendJsonResponse(exchange, 200, [message: "Vaga removida", id: id])
        } else {
            sendErrorResponse(exchange, 404, "Rota não encontrada")
        }
    }

    private boolean isDonaDaVaga(HttpExchange exchange, int id) {
        def vaga = vagaService.getVagaById(id)
        return vaga?.id_empresa == AuthService.getSession(exchange).id
    }

    private boolean vagaExists(int id) {
        return vagaService.getVagaById(id) != null
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
