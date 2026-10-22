package com.augusto.linketinder.API

import com.augusto.linketinder.model.Competencia
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
    void handle(HttpExchange exchange) throws IOException {
        println "[${new Date()}] ${exchange.requestMethod} ${exchange.requestURI}"
        
        addCorsHeaders(exchange)

        try {
            def method = exchange.requestMethod
            def path = exchange.requestURI.path.replaceAll('/+$', '') // Remove trailing slashes

            if (method == "OPTIONS") {
                exchange.sendResponseHeaders(204, -1)
                exchange.close()
                return
            }
            // Cadastro (POST) é público; alterar e remover só o próprio perfil
            if (method in ["PUT", "DELETE"]) {
                def idPath = path.find('\\d+$')
                int status = AuthService.checkAccess(exchange, "candidato", idPath ? idPath.toInteger() : null)
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
        def candidato = candidatoService.getCandidatoById(id)
        
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
            def campos = data.findAll { key, value -> key != 'id' && key != 'competencias' }

            // Rejeita tudo antes de alterar qualquer coluna, para não deixar o registro pela metade
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
