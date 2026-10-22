package com.augusto.linketinder.API

import com.augusto.linketinder.model.pessoa.Candidato
import com.augusto.linketinder.service.AuthService
import com.augusto.linketinder.service.CandidatoService
import com.sun.net.httpserver.HttpExchange

class CandidatoControllerAPI extends CrudControllerAPI {

    private final CandidatoService candidatoService

    CandidatoControllerAPI() {
        super("/candidatos", "Candidato", "o")
        this.candidatoService = new CandidatoService()
    }

    @Override
    protected int verificarAcesso(HttpExchange exchange, String method, String path) {
        return method in ["PUT", "DELETE"] ? AuthService.checkAccess(exchange, "candidato", idDoPath(path)) : 0
    }

    @Override
    protected List listar() { candidatoService.getCandidatoList() }

    @Override
    protected Object buscar(int id) { candidatoService.getCandidatoById(id) }

    @Override
    protected List<String> camposValidos() { candidatoService.getCamposCandidato() }

    @Override
    protected void atualizar(int id, String coluna, Object valor) { candidatoService.updateCandidato(id, coluna, valor) }

    @Override
    protected void remover(int id) { candidatoService.deleteCandidato(id) }

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

            sendJsonResponse(exchange, 201, [message: "Candidato criado com sucesso", data: toJson(candidato)])
        } else {
            sendNotFound(exchange)
        }
    }

    @Override
    protected Map toJson(Object entidade) {
        Candidato candidato = entidade as Candidato
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
