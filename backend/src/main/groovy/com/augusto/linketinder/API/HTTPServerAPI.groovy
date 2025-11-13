package com.augusto.linketinder.API

import com.sun.net.httpserver.HttpServer

class HTTPServerAPI {

    private final HttpServer httpServer
    private final int port

    HTTPServerAPI(int port) {
        this.port = port
        this.httpServer = HttpServer.create(new InetSocketAddress(port), 0)
        configureRoutes()
        httpServer.setExecutor(null) // Usa o executor padrão
    }

    private void configureRoutes() {
        httpServer.createContext("/candidatos", new CandidatoControllerAPI())
        // Adicione mais rotas aqui conforme necessário
        // httpServer.createContext("/empresas", new EmpresaControllerAPI())
        // httpServer.createContext("/vagas", new VagaControllerAPI())
    }

    void start() {
        httpServer.start()
        println "✓ Servidor HTTP iniciado na porta ${port}"
        println "✓ Acesse: http://localhost:${port}/candidatos"
    }

    void stop(int delayInSeconds = 0) {
        httpServer.stop(delayInSeconds)
        println "✗ Servidor HTTP encerrado"
    }
}
