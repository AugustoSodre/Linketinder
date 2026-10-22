package com.augusto.linketinder.API

import com.augusto.linketinder.model.Competencia
import com.augusto.linketinder.model.Vaga
import com.augusto.linketinder.model.pessoa.Empresa
import com.augusto.linketinder.service.AuthService
import com.augusto.linketinder.service.EmpresaService
import com.sun.net.httpserver.HttpExchange

class EmpresaControllerAPI extends BaseControllerAPI {
    
    private final EmpresaService empresaService

    EmpresaControllerAPI() {
        this.empresaService = new EmpresaService()
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
                int status = AuthService.checkAccess(exchange, "empresa", idPath ? idPath.toInteger() : null)
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
        def empresa = empresaService.getEmpresaById(id)
        
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

            if (rejeitaInvalidos(exchange, data, ['nome', 'email', 'estado', 'cep', 'pais', 'cnpj', 'descricao', 'senha'])) {
                return
            }
            for (def vaga in (data.vagas ?: [])) {
                if (!(vaga instanceof Map)) {
                    sendErrorResponse(exchange, 400, "Vaga inválida")
                    return
                }
                if (rejeitaInvalidos(exchange, vaga as Map, ['nome', 'descricao'])) {
                    return
                }
            }

            def empresa = mapToEmpresa(data)
            try {
                empresaService.createEmpresa(empresa)
            } catch (Exception e) {
                if (!isDuplicado(e)) throw e
                sendErrorResponse(exchange, 409, "CNPJ já cadastrado")
                return
            }
            
            sendJsonResponse(exchange, 201, [message: "Empresa criada com sucesso", data: empresaToJson(empresa)])
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
            def campos = data.findAll { key, value -> key != 'id' && key != 'competencias' && key != 'listaVaga' }

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
                    empresaService.updateEmpresa(id, (camposValidos[key] ?: key) as String, value)
                }
            } catch (Exception e) {
                if (!isDuplicado(e)) throw e
                sendErrorResponse(exchange, 409, "Valor já cadastrado por outro usuário")
                return
            }
            def camposAtualizados = campos.keySet()

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

    private boolean empresaExists(int id) {
        return empresaService.getEmpresaById(id) != null
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
            empresa.listaVaga = data.vagas.collect { vaga ->
                if (vaga instanceof Map) {
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
                    
                    return vagaObj
                } else {
                    return vaga as Vaga
                }
            }
        } else {
            empresa.listaVaga = []
        }
        
        return empresa
    }
}
