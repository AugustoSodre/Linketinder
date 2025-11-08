package com.augusto.linketinder.service

class InputServiceCore {

    private BufferedReader br
    private ValidateService validateService

    InputServiceCore(){
        this(new BufferedReader(new InputStreamReader(System.in)), new ValidateService())
    }

    InputServiceCore(BufferedReader br, ValidateService validateService){
        if (br == null) {
            throw new IllegalArgumentException("BufferedReader não pode ser nulo")
        }
        if (validateService == null) {
            throw new IllegalArgumentException("ValidateService não pode ser nulo")
        }
        this.br = br
        this.validateService = validateService
    }

    String getStringInput(){
        while (true) {
            String input
            try {
                input = br.readLine()
            } catch (IOException e) {
                throw new RuntimeException("Erro ao ler input", e)
            }

            if (input == null) {
                throw new RuntimeException("Entrada encerrada antes de receber um texto")
            }

            String trimmed = input.trim()
            if (!trimmed.isEmpty()) {
                return trimmed
            }

            println("Input inválido")
            print "Digite novamente: "
        }
    }

    int getIntInput(){
        while (true) {
            String raw
            try {
                raw = br.readLine()
            } catch (IOException e) {
                throw new RuntimeException("Erro ao ler input inteiro", e)
            }

            if (raw == null) {
                throw new RuntimeException("Entrada encerrada antes de receber um número inteiro")
            }

            try {
                return Integer.parseInt(raw.trim())
            } catch (NumberFormatException ignored) {
                println("Input inválido")
                print "Digite novamente: "
            }
        }
    }

    void printMensagemErro(String objeto){
        println "${objeto} inválido! Tente novamente"
    }
}
