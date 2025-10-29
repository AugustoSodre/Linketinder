package com.augusto.linketinder.service

import com.augusto.linketinder.model.Competencia

class InputService {

    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in))
    private ValidateService validateService = new ValidateService()

    void setBr(BufferedReader bufferedReader) {
        this.br = bufferedReader
    }

    String getStringInput(){
        try{
            String input = br.readLine()
            if (input != null && !input.isEmpty()) {
                return input.trim()
            } else {
                println("Input inválido\n")
                print "Digite novamente: "
            }
        } catch (Exception exception){
            throw exception
        }

        throw new RuntimeException("Número de tentativas alcançado! Tente novamente!")
    }

    int getIntInput(){
        try{
            return Integer.parseInt(br.readLine())
        } catch (Exception ignored){
            println("Input inválido")
            print "Digite novamente: "
        }

        throw new RuntimeException("Máximo de tentativas excedido para input inteiro")
    }

    String getNomeInput(){
        String nome = ""

        while(!validateService.isNomeValid(nome)){
            try{
                nome = getStringInput()
            } catch (Exception e){
                printMensagemErro("Nome")
                println e.message
            }
        }

        return nome
    }

    String getDescricaoInput(){
        String descricao = ""

        while(!validateService.isDescricaoValid(descricao)){
            try{
                descricao = getStringInput()
            } catch (Exception e){
                printMensagemErro("Descrição")
                println e.message
            }
        }

        return descricao
    }

    String getPaisInput(){
        String pais = ""

        while(!validateService.isPaisValid(pais)){
            try{
                pais = getStringInput()
            } catch (Exception e){
                printMensagemErro("País")
                println e.message
            }
        }

        return pais
    }

    int getIdadeInput(){
        return getIntInput()
    }

    String getSenhaInput() {
        String senha = ""

        while(!validateService.isSenhaValid(senha)){
            try{
                senha = getStringInput()
            } catch (Exception e){
                printMensagemErro("Senha")
                println e.message
            }
        }

        return senha
    }

    String getEmailInput(){
        String email = ""

        while(!validateService.isEmailValid(email)){
            try{
                email = getStringInput()
            } catch (Exception e){
                printMensagemErro("Email")
                println e.message
            }
        }

        return email
    }

    String getEstadoInput(){
        String estado = ""

        while(!validateService.isEstadoValid(estado)){
            try{
                estado = getStringInput()
                estado = estado.toUpperCase()
            } catch (Exception e){
                printMensagemErro("Estado")
                println e.message
            }
        }

        return estado
    }

    String getCepInput(){
        String cep = ""

        while(!validateService.isCEPValid(cep)){
            try{
                cep = getStringInput()
            } catch (Exception e){
                printMensagemErro("CEP")
                println e.message
            }
        }

        return cep
    }

    String getCpfInput(){
        String cpf = ""

        while(!validateService.isCPFValid(cpf)){
            try{
                cpf = getStringInput()
            } catch (Exception e){
                printMensagemErro("CPF")
                println e.message
            }
        }

        return cpf
    }

    String getCnpjInput(){
        String cnpj = ""

        while(!validateService.isCNPJValid(cnpj)){
            try{
                cnpj = getStringInput()
            } catch (Exception e){
                printMensagemErro("CNPJ")
                println e.message
            }
        }

        return cnpj
    }

    List<Competencia> getCompetenciasInput(List<Competencia> listaComp) {
        List<Competencia> listCompetenciasSelecionadas = []
        final int maxTentativas = 5
        int tentativasInvalidas = 0

        while (tentativasInvalidas < maxTentativas) {
            println()
            println("Competências selecionadas:")
            listCompetenciasSelecionadas.each {println("- " + it.nome)}
            println()
            print("Digite uma competência para adicionar [0 para sair]: ")

            int opcao
            try {
                opcao = getIntInput()
            } catch (RuntimeException e) {
                throw new RuntimeException("Máximo de tentativas excedido para competências")
            }

            if (opcao == 0) break

            if (opcao > 0 && opcao <= listaComp.size()) {
                Competencia comp = listaComp[opcao - 1]
                if (!listCompetenciasSelecionadas.contains(comp)) {
                    listCompetenciasSelecionadas.add(comp)
                    tentativasInvalidas = 0 // Reset contador após sucesso
                } else {
                    println("Competência já foi selecionada.")
                    tentativasInvalidas++
                }
            } else {
                println("Opção inválida, tente novamente.")
                tentativasInvalidas++
            }
        }

        if (tentativasInvalidas >= maxTentativas) {
            throw new RuntimeException("Máximo de tentativas excedido para competências")
        }

        return listCompetenciasSelecionadas
    }


    void printMensagemErro(String objeto){
        println "${objeto} inválido! Tente novamente"
    }

}