package com.augusto.linketinder

import com.augusto.linketinder.API.HTTPServerAPI

class Server {

    static void main(String[] args) {
        int port = getPort(args)
        
        HTTPServerAPI server = new HTTPServerAPI(port)
        
        println """
        ╔════════════════════════════════════════════════════════════╗
        ║           Linketinder API REST Server                      ║
        ╠════════════════════════════════════════════════════════════╣
        ║  Servidor iniciando na porta ${port}...                         ║
        ╚════════════════════════════════════════════════════════════╝
        """
        
        server.start()
        
        println """
        ╔════════════════════════════════════════════════════════════╗
        ║  Endpoints disponíveis:                                    ║
        ╠════════════════════════════════════════════════════════════╣
        ║  📋 Candidatos: http://localhost:${port}/candidatos            ║
        ║  🏢 Empresas:   http://localhost:${port}/empresas              ║
        ║  💼 Vagas:      http://localhost:${port}/vagas                 ║
        ╠════════════════════════════════════════════════════════════╣
        ║  Pressione Ctrl+C para encerrar o servidor                ║
        ╚════════════════════════════════════════════════════════════╝
        """
        
        addShutdownHook {
            println "\n🛑 Encerrando servidor..."
            server.stop(0)
            println "✓ Servidor encerrado com sucesso!"
        }
        
        Thread.currentThread().join()
    }
    
    private static int getPort(String[] args) {
        if (args && args.length > 0) {
            try {
                return Integer.parseInt(args[0])
            } catch (NumberFormatException e) {
                println "⚠️  Porta inválida nos argumentos, usando porta padrão 8080"
            }
        }
        
        String envPort = System.getenv("PORT")
        if (envPort) {
            try {
                return Integer.parseInt(envPort)
            } catch (NumberFormatException e) {
                println "⚠️  Porta inválida na variável de ambiente PORT, usando porta padrão 8080"
            }
        }
        
        return 8080
    }
}
