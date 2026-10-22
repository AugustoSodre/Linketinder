package com.augusto.linketinder.API

import com.augusto.linketinder.model.pessoa.Candidato
import com.augusto.linketinder.service.AuthService
import com.augusto.linketinder.service.CandidatoService
import com.sun.net.httpserver.HttpExchange

class CandidatoControllerAPI extends BaseControllerAPI {
    
    private final CandidatoService candidatoService

    CandidatoControllerAPI() {
        this.candidatoService = new CandidatoService()
    }

    @Override
    protected int verificarAcesso(HttpExchange exchange, String method, String path) {
        return method in ["PUT", "DELETE"] ? AuthService.checkAccess(exchange, "candidato", idDoPath(path)) : 0
    }

    @Override
    protected void handleGet(HttpExchange exchange, String path) {
        if (path == "/candidatos") {
            def candidatos = candidatoService.getCandidatoList()
            def candidatosJson = candidatos.collect { candidato ->
                candidatoToJson(candidato)
            }
            sendJsonResponse(exchange, 200, candidatosJson)
        } else if (path.matches('/candidatos/\\d+')) {
            def id = idDoPath(path)
            handleGetById(exchange, id)
        } else {
            sendNotFound(exchange)
        }
    }

    private void handleGetById(HttpExchange exchange, int id) {
        def candidato = candidatoService.getCandidatoById(id)
        
        if (candidato) {
            def candidatoJson = candidatoToJson(candidato)
            sendJsonResponse(exchange, 200, candidatoJson)
        } else {
            sendErrorResponse(exchange, 404, "Candidato não encontrado")
        }
    }

    @Override
    protected void handlePost(HttpExchange exchange, String path) {
        if (path == "/candidatos") {
            def body = getRequestBody(exchange)
            def data = objectMapper.readValue(body, Map.class)

            if (rejeitaInvalidos(exchange, data, ['nome', 'email', 'estado', 'cep', 'idade', 'cpf', 'descricao', 'senha'])) {
                return
            }

            def candidato = mapToCandidato(data)
            try {
                candidatoService.createCandidato(candidato)
            } catch (Exception e) {
                if (!isDuplicado(e)) throw e
                sendErrorResponse(exchange, 409, "CPF já cadastrado")
                return
            }
            
            sendJsonResponse(exchange, 201, [message: "Candidato criado com sucesso", data: candidatoToJson(candidato)])
        } else {
            sendNotFound(exchange)
        }
    }

    @Override
    protected void handlePut(HttpExchange exchange, String path) {
        if (path.matches('/candidatos/\\d+')) {
            int id = idDoPath(path)

            def body = getRequestBody(exchange)
            def data = objectMapper.readValue(body, Map.class)
            
            if (!candidatoExists(id)) {
                sendErrorResponse(exchange, 404, "Candidato não encontrado")
                return
            }
            
            def camposValidos = candidatoService.getCamposCandidato()
            def campos = data.findAll { key, value -> key != 'id' && key != 'competencias' }

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
                    candidatoService.updateCandidato(id, (camposValidos[key] ?: key) as String, value)
                }
            } catch (Exception e) {
                if (!isDuplicado(e)) throw e
                sendErrorResponse(exchange, 409, "Valor já cadastrado por outro usuário")
                return
            }
            def camposAtualizados = campos.keySet()

            sendJsonResponse(exchange, 200, [
                message: "Candidato atualizado com sucesso", 
                id: id, 
                camposAtualizados: camposAtualizados
            ])
        } else {
            sendNotFound(exchange)
        }
    }

    @Override
    protected void handleDelete(HttpExchange exchange, String path) {
        if (path.matches('/candidatos/\\d+')) {
            def id = idDoPath(path)
            
            if (!candidatoExists(id)) {
                sendErrorResponse(exchange, 404, "Candidato não encontrado")
                return
            }
            
            candidatoService.deleteCandidato(id)

            sendJsonResponse(exchange, 200, [message: "Candidato removido", id: id])
        } else {
            sendNotFound(exchange)
        }
    }

    private boolean candidatoExists(int id) {
        return candidatoService.getCandidatoById(id) != null
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
            competencias: competenciasToJson(candidato.competencias)
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
        
        candidato.competencias = toCompetencias(data.competencias)
        
        return candidato
    }
}
