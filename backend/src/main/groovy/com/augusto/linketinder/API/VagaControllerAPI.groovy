package com.augusto.linketinder.API

import com.augusto.linketinder.service.AuthService
import com.augusto.linketinder.service.VagaService
import com.sun.net.httpserver.HttpExchange

class VagaControllerAPI extends BaseControllerAPI {
    
    private final VagaService vagaService

    VagaControllerAPI() {
        this.vagaService = new VagaService()
    }

    @Override
    protected int verificarAcesso(HttpExchange exchange, String method, String path) {
        return method in ["POST", "PUT", "DELETE"] ? AuthService.checkAccess(exchange, "empresa", null) : 0
    }

    @Override
    protected void handleGet(HttpExchange exchange, String path) {
        if (path == "/vagas") {
            def vagas = vagaService.getVagaList()
            def vagasJson = vagas.collect { vaga ->
                vagaToJson(vaga)
            }
            sendJsonResponse(exchange, 200, vagasJson)
        } else if (path.matches('/vagas/\\d+')) {
            def id = idDoPath(path)
            handleGetById(exchange, id)
        } else {
            sendNotFound(exchange)
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

    @Override
    protected void handlePost(HttpExchange exchange, String path) {
        if (path == "/vagas") {
            def body = getRequestBody(exchange)
            def data = objectMapper.readValue(body, Map.class)

            if (rejeitaInvalidos(exchange, data, ['nome', 'descricao'])) {
                return
            }

            def vaga = toVaga(data)
            if (vaga.id_empresa != AuthService.getSession(exchange).id) {
                sendErrorResponse(exchange, 403, "Acesso negado")
                return
            }
            vagaService.createVaga(vaga)

            sendJsonResponse(exchange, 201, [message: "Vaga criada com sucesso", data: vagaToJson(vaga)])
        } else {
            sendNotFound(exchange)
        }
    }

    @Override
    protected void handlePut(HttpExchange exchange, String path) {
        if (path.matches('/vagas/\\d+')) {
            int id = idDoPath(path)

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
            sendNotFound(exchange)
        }
    }

    @Override
    protected void handleDelete(HttpExchange exchange, String path) {
        if (path.matches('/vagas/\\d+')) {
            def id = idDoPath(path)
            
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
            sendNotFound(exchange)
        }
    }

    private boolean isDonaDaVaga(HttpExchange exchange, int id) {
        def vaga = vagaService.getVagaById(id)
        return vaga?.id_empresa == AuthService.getSession(exchange).id
    }

    private boolean vagaExists(int id) {
        return vagaService.getVagaById(id) != null
    }
}
