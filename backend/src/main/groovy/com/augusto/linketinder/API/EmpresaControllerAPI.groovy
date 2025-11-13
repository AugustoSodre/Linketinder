package com.augusto.linketinder.API

import com.augusto.linketinder.model.Competencia
import com.augusto.linketinder.model.Vaga
import com.augusto.linketinder.model.pessoa.Empresa
import com.augusto.linketinder.service.EmpresaService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler

class EmpresaControllerAPI implements HttpHandler {
    
    private final EmpresaService empresaService
    private final ObjectMapper objectMapper

    EmpresaControllerAPI() {
        this.empresaService = new EmpresaService()
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
        if (path == "/empresas") {
            def empresas = empresaService.getEmpresaList()
            def empresasJson = empresas.collect { empresa ->
                empresaToJson(empresa)
            }
            sendJsonResponse(exchange, 200, empresasJson)
        } else if (path.matches('/empresas/\\d+')) {
            def id = path.split('/').last().toInteger()
            handleGetById(exchange, id)
        } else {
            sendErrorResponse(exchange, 404, "Rota não encontrada")
        }
    }

    private void handleGetById(HttpExchange exchange, int id) {
        def empresas = empresaService.getEmpresaList()
        def empresa = empresas.find { it.id == id }
        
        if (empresa) {
            def empresaJson = empresaToJson(empresa)
            sendJsonResponse(exchange, 200, empresaJson)
        } else {
            sendErrorResponse(exchange, 404, "Empresa não encontrada")
        }
    }

    private void handlePost(HttpExchange exchange, String path) {
        if (path == "/empresas") {
            def body = getRequestBody(exchange)
            def data = objectMapper.readValue(body, Map.class)

            def empresa = mapToEmpresa(data)
            empresaService.createEmpresa(empresa)
            
            sendJsonResponse(exchange, 201, [message: "Empresa criada com sucesso", data: data])
        } else {
            sendErrorResponse(exchange, 404, "Rota não encontrada")
        }
    }

    private void handlePut(HttpExchange exchange, String path) {
        if (path.matches('/empresas/\\d+')) {
            int id = path.split('/').last().toInteger()

            def body = getRequestBody(exchange)
            def data = objectMapper.readValue(body, Map.class)
            
            if (!empresaExists(id)) {
                sendErrorResponse(exchange, 404, "Empresa não encontrada")
                return
            }
            
            def camposValidos = empresaService.getCamposEmpresa()
            def camposAtualizados = []
            
            data.each { key, value ->
                if (key != 'id' && key != 'competencias' && key != 'listaVaga') {
                    // Mapeia o nome do campo do JSON para o nome da coluna no BD
                    def nomeColuna = camposValidos[key] ?: key
                    
                    try {
                        empresaService.updateEmpresa(id, nomeColuna as String, value)
                        camposAtualizados << key
                    } catch (Exception e) {
                        println "Erro ao atualizar campo '${key}': ${e.message}"
                    }
                }
            }
            
            sendJsonResponse(exchange, 200, [
                message: "Empresa atualizada com sucesso", 
                id: id, 
                camposAtualizados: camposAtualizados
            ])
        } else {
            sendErrorResponse(exchange, 404, "Rota não encontrada")
        }
    }

    private void handleDelete(HttpExchange exchange, String path) {
        if (path.matches('/empresas/\\d+')) {
            def id = path.split('/').last().toInteger()
            
            if (!empresaExists(id)) {
                sendErrorResponse(exchange, 404, "Empresa não encontrada")
                return
            }
            
            empresaService.deleteEmpresa(id)

            sendJsonResponse(exchange, 200, [message: "Empresa removida", id: id])
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

    private boolean empresaExists(int id) {
        def empresas = empresaService.getEmpresaList()
        return empresas.any { it.id == id }
    }

    private Map empresaToJson(Empresa empresa) {
        return [
            id: empresa.id,
            nome: empresa.nome,
            email: empresa.email,
            estado: empresa.estado,
            cep: empresa.cep,
            pais: empresa.pais,
            cnpj: empresa.cnpj,
            descricao: empresa.descricao,
            competencias: empresa.competencias?.collect { comp ->
                [id: comp.id, nome: comp.nome]
            } ?: [],
            vagas: empresa.listaVaga?.collect { vaga ->
                [
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
            } ?: []
        ]
    }

    private Empresa mapToEmpresa(Map data) {
        println "DEBUG: mapToEmpresa recebeu data: ${data}"
        println "DEBUG: data.vagas = ${data.vagas}"
        
        def empresa = new Empresa()
        empresa.nome = data.nome
        empresa.email = data.email
        empresa.estado = data.estado
        empresa.cep = data.cep
        empresa.pais = data.pais
        empresa.cnpj = data.cnpj
        empresa.descricao = data.descricao
        empresa.senha = data.senha
        
        // Converte a lista de competências de Map para objetos Competencia
        if (data.competencias && data.competencias instanceof List) {
            empresa.competencias = data.competencias.collect { comp ->
                if (comp instanceof Map) {
                    new Competencia(comp.id as Integer, comp.nome)
                } else {
                    comp as Competencia
                }
            }
        } else {
            empresa.competencias = []
        }
        
        // Converte a lista de vagas de Map para objetos Vaga
        if (data.vagas && data.vagas instanceof List) {
            println "DEBUG: Convertendo ${data.vagas.size()} vagas"
            empresa.listaVaga = data.vagas.collect { vaga ->
                if (vaga instanceof Map) {
                    println "DEBUG: Criando vaga: ${vaga.nome}"
                    def vagaObj = new Vaga()
                    vagaObj.id_empresa = 0  // Será definido no DAO
                    vagaObj.nome = vaga.nome
                    vagaObj.descricao = vaga.descricao
                    vagaObj.cidade = vaga.cidade
                    vagaObj.estado = vaga.estado
                    
                    // Converte competências da vaga
                    if (vaga.competencias && vaga.competencias instanceof List) {
                        vagaObj.competencias = vaga.competencias.collect { comp ->
                            if (comp instanceof Map) {
                                new Competencia(comp.id as Integer, comp.nome)
                            } else {
                                comp as Competencia
                            }
                        }
                    } else {
                        vagaObj.competencias = []
                    }
                    
                    println "DEBUG: Vaga criada: ${vagaObj.nome}"
                    return vagaObj
                } else {
                    return vaga as Vaga
                }
            }
            println "DEBUG: empresa.listaVaga após conversão = ${empresa.listaVaga}"
        } else {
            println "DEBUG: Nenhuma vaga encontrada nos dados!"
            empresa.listaVaga = []
        }
        
        println "DEBUG: Retornando empresa com ${empresa.listaVaga?.size()} vagas"
        return empresa
    }
}
