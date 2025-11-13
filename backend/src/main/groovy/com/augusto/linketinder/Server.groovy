package com.augusto.linketinder

import com.augusto.linketinder.API.HTTPServerAPI

/**
 * Ponto de entrada principal para o servidor HTTP da API REST.
 * Inicia o servidor na porta 8080 e registra os endpoints para Candidatos, Empresas e Vagas.
 *
 * Para executar:
 *   ./gradlew run (se configurado no build.gradle)
 *   ou
 *   java -cp build/classes/groovy/main:build/libs/* com.augusto.linketinder.Server
 */
class Server {

    static void main(String[] args) {
        // Define a porta do servidor (pode ser configurada via argumentos ou variável de ambiente)
        int port = getPort(args)
        
        // Cria e inicia o servidor HTTP
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
        
        // Adiciona shutdown hook para encerrar graciosamente
        addShutdownHook {
            println "\n🛑 Encerrando servidor..."
            server.stop(0)
            println "✓ Servidor encerrado com sucesso!"
        }
        
        // Mantém o servidor rodando
        Thread.currentThread().join()
    }
    
    /**
     * Obtém a porta do servidor a partir dos argumentos ou variável de ambiente.
     * Padrão: 8080
     */
    private static int getPort(String[] args) {
        // Tenta obter porta dos argumentos de linha de comando
        if (args && args.length > 0) {
            try {
                return Integer.parseInt(args[0])
            } catch (NumberFormatException e) {
                println "⚠️  Porta inválida nos argumentos, usando porta padrão 8080"
            }
        }
        
        // Tenta obter porta da variável de ambiente
        String envPort = System.getenv("PORT")
        if (envPort) {
            try {
                return Integer.parseInt(envPort)
            } catch (NumberFormatException e) {
                println "⚠️  Porta inválida na variável de ambiente PORT, usando porta padrão 8080"
            }
        }
        
        // Retorna porta padrão
        return 8080
    }
}
