package com.augusto.linketinder.API

import com.augusto.linketinder.dao.RelationType
import com.augusto.linketinder.service.CompetenciaService
import com.sun.net.httpserver.HttpExchange

abstract class CrudControllerAPI extends BaseControllerAPI {

    private final String base
    private final String rotulo
    private final String genero
    private final RelationType relacao
    private final CompetenciaService competenciaService = new CompetenciaService()

    CrudControllerAPI(String base, String rotulo, String genero, RelationType relacao) {
        this.base = base
        this.rotulo = rotulo
        this.genero = genero
        this.relacao = relacao
    }

    protected abstract List listar()

    protected abstract Object buscar(int id)

    protected abstract Map toJson(Object entidade)

    protected abstract List<String> camposValidos()

    protected abstract void atualizar(int id, String coluna, Object valor)

    protected abstract void remover(int id)

    protected List<String> camposIgnorados() {
        return ['id', 'competencias']
    }

    protected boolean podeAlterar(HttpExchange exchange, int id) {
        return true
    }

    protected boolean isRotaDeItem(String path) {
        return path.matches("${base}/\\d+")
    }

    @Override
    protected void handleGet(HttpExchange exchange, String path) {
        if (path == base) {
            sendJsonResponse(exchange, 200, listar().collect { toJson(it) })
        } else if (isRotaDeItem(path)) {
            def entidade = buscar(idDoPath(path))
            entidade ? sendJsonResponse(exchange, 200, toJson(entidade)) : sendNaoEncontrado(exchange)
        } else {
            sendNotFound(exchange)
        }
    }

    @Override
    protected void handlePut(HttpExchange exchange, String path) {
        if (!isRotaDeItem(path)) {
            sendNotFound(exchange)
            return
        }
        int id = idDoPath(path)
        Map data = objectMapper.readValue(getRequestBody(exchange), Map.class)
        if (!checaExistenciaEDono(exchange, id)) {
            return
        }

        def validos = camposValidos()
        def campos = data.findAll { key, value -> !(key in camposIgnorados()) }
        def invalidos = campos.keySet().findAll { !(it in validos) }
        if (invalidos) {
            sendErrorResponse(exchange, 400, "Campos inválidos: ${invalidos.join(', ')}")
            return
        }
        if (rejeitaInvalidos(exchange, campos, campos.keySet() as List<String>)) {
            return
        }
        boolean trocaCompetencias = data.containsKey('competencias')
        if (trocaCompetencias && !competenciasValidas(data.competencias)) {
            sendErrorResponse(exchange, 400, "Competências inválidas")
            return
        }

        try {
            campos.each { key, value -> atualizar(id, key as String, value) }
            if (trocaCompetencias) {
                competenciaService.substituir(relacao, id, toCompetencias(data.competencias))
            }
        } catch (Exception e) {
            if (!isDuplicado(e)) throw e
            sendErrorResponse(exchange, 409, "Valor já cadastrado por outro usuário")
            return
        }
        sendJsonResponse(exchange, 200, [message: "$rotulo atualizad$genero com sucesso" as String, id: id, camposAtualizados: campos.keySet() + (trocaCompetencias ? ['competencias'] : [])])
    }

    @Override
    protected void handleDelete(HttpExchange exchange, String path) {
        if (!isRotaDeItem(path)) {
            sendNotFound(exchange)
            return
        }
        int id = idDoPath(path)
        if (!checaExistenciaEDono(exchange, id)) {
            return
        }
        remover(id)
        sendJsonResponse(exchange, 200, [message: "$rotulo removid$genero" as String, id: id])
    }

    private boolean competenciasValidas(Object lista) {
        return lista instanceof List && lista.every { it instanceof Map && it.id instanceof Integer } &&
                competenciaService.existem(lista*.id as List<Integer>)
    }

    private boolean checaExistenciaEDono(HttpExchange exchange, int id) {
        if (buscar(id) == null) {
            sendNaoEncontrado(exchange)
            return false
        }
        if (!podeAlterar(exchange, id)) {
            sendErrorResponse(exchange, 403, "Acesso negado")
            return false
        }
        return true
    }

    private void sendNaoEncontrado(HttpExchange exchange) {
        sendErrorResponse(exchange, 404, "$rotulo não encontrad$genero" as String)
    }
}
