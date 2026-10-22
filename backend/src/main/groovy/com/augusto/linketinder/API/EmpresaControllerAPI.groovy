package com.augusto.linketinder.API

import com.augusto.linketinder.model.pessoa.Empresa
import com.augusto.linketinder.service.AuthService
import com.augusto.linketinder.service.EmpresaService
import com.sun.net.httpserver.HttpExchange

class EmpresaControllerAPI extends CrudControllerAPI {

    private final EmpresaService empresaService

    EmpresaControllerAPI() {
        super("/empresas", "Empresa", "a")
        this.empresaService = new EmpresaService()
    }

    @Override
    protected int verificarAcesso(HttpExchange exchange, String method, String path) {
        return method in ["PUT", "DELETE"] ? AuthService.checkAccess(exchange, "empresa", idDoPath(path)) : 0
    }

    @Override
    protected List listar() { empresaService.getEmpresaList() }

    @Override
    protected Object buscar(int id) { empresaService.getEmpresaById(id) }

    @Override
    protected List<String> camposValidos() { empresaService.getCamposEmpresa() }

    @Override
    protected List<String> camposIgnorados() { super.camposIgnorados() + 'listaVaga' }

    @Override
    protected void atualizar(int id, String coluna, Object valor) { empresaService.updateEmpresa(id, coluna, valor) }

    @Override
    protected void remover(int id) { empresaService.deleteEmpresa(id) }

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

            sendJsonResponse(exchange, 201, [message: "Empresa criada com sucesso", data: toJson(empresa)])
        } else {
            sendNotFound(exchange)
        }
    }

    @Override
    protected Map toJson(Object entidade) {
        Empresa empresa = entidade as Empresa
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
