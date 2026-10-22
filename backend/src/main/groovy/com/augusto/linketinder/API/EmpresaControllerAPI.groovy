package com.augusto.linketinder.API

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
    protected int verificarAcesso(HttpExchange exchange, String method, String path) {
        return method in ["PUT", "DELETE"] ? AuthService.checkAccess(exchange, "empresa", idDoPath(path)) : 0
    }

    @Override
    protected void handleGet(HttpExchange exchange, String path) {
        if (path == "/empresas") {
            def empresas = empresaService.getEmpresaList()
            def empresasJson = empresas.collect { empresa ->
                empresaToJson(empresa)
            }
            sendJsonResponse(exchange, 200, empresasJson)
        } else if (path.matches('/empresas/\\d+')) {
            def id = idDoPath(path)
            handleGetById(exchange, id)
        } else {
            sendNotFound(exchange)
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

    @Override
    protected void handlePost(HttpExchange exchange, String path) {
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
            sendNotFound(exchange)
        }
    }

    @Override
    protected void handlePut(HttpExchange exchange, String path) {
        if (path.matches('/empresas/\\d+')) {
            int id = idDoPath(path)

            def body = getRequestBody(exchange)
            def data = objectMapper.readValue(body, Map.class)
            
            if (!empresaExists(id)) {
                sendErrorResponse(exchange, 404, "Empresa não encontrada")
                return
            }
            
            def camposValidos = empresaService.getCamposEmpresa()
            def campos = data.findAll { key, value -> key != 'id' && key != 'competencias' && key != 'listaVaga' }

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
            sendNotFound(exchange)
        }
    }

    @Override
    protected void handleDelete(HttpExchange exchange, String path) {
        if (path.matches('/empresas/\\d+')) {
            def id = idDoPath(path)
            
            if (!empresaExists(id)) {
                sendErrorResponse(exchange, 404, "Empresa não encontrada")
                return
            }
            
            empresaService.deleteEmpresa(id)

            sendJsonResponse(exchange, 200, [message: "Empresa removida", id: id])
        } else {
            sendNotFound(exchange)
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
            competencias: competenciasToJson(empresa.competencias),
            vagas: (empresa.listaVaga ?: []).collect { vagaToJson(it) }
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
        empresa.competencias = toCompetencias(data.competencias)
        
        empresa.listaVaga = (data.vagas ?: []).collect { toVaga(it as Map) }
        return empresa
    }
}
