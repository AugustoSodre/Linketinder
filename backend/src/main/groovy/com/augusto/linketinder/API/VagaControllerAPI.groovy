package com.augusto.linketinder.API

import com.augusto.linketinder.model.Vaga
import com.augusto.linketinder.service.AuthService
import com.augusto.linketinder.service.VagaService
import com.sun.net.httpserver.HttpExchange

class VagaControllerAPI extends CrudControllerAPI {

    private final VagaService vagaService

    VagaControllerAPI() {
        super("/vagas", "Vaga", "a")
        this.vagaService = new VagaService()
    }

    @Override
    protected int verificarAcesso(HttpExchange exchange, String method, String path) {
        return method in ["POST", "PUT", "DELETE"] ? AuthService.checkAccess(exchange, "empresa", null) : 0
    }

    @Override
    protected List listar() { vagaService.getVagaList() }

    @Override
    protected Object buscar(int id) { vagaService.getVagaById(id) }

    @Override
    protected Map toJson(Object entidade) { vagaToJson(entidade as Vaga) }

    @Override
    protected List<String> camposValidos() { vagaService.getCamposVaga() }

    @Override
    protected List<String> camposIgnorados() { super.camposIgnorados() + 'id_empresa' }

    @Override
    protected void atualizar(int id, String coluna, Object valor) { vagaService.updateVaga(id, coluna, valor) }

    @Override
    protected void remover(int id) { vagaService.deleteVaga(id) }

    @Override
    protected boolean podeAlterar(HttpExchange exchange, int id) {
        return vagaService.getVagaById(id)?.id_empresa == AuthService.getSession(exchange).id
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
}
