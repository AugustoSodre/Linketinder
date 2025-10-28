package com.augusto.linketinder.service

import com.augusto.linketinder.model.Competencia

class InputService {

    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in))
    private ValidateService validateService = new ValidateService()
    private static final int MAX_TENTATIVAS_PADRAO = 5

    void setBr(BufferedReader bufferedReader) {
        this.br = bufferedReader
    }

    String getStringInput(int maxTentativas = MAX_TENTATIVAS_PADRAO){
        int tentativas = 0
        while (tentativas < maxTentativas){
            try{
                String input = br.readLine()
                if (input != null && !input.isEmpty()) {
                    return input.trim()
                } else {
                    println("Input inválido")
                    tentativas++
                }
            } catch (Exception ignored){
                println("Input inválido")
                tentativas++
            }
        }
        throw new RuntimeException("Máximo de tentativas excedido para input de string")
    }

    String getNomeInput(int maxTentativas = MAX_TENTATIVAS_PADRAO){
        return getStringInput(maxTentativas)
    }

    String getEmailInput(int maxTentativas = MAX_TENTATIVAS_PADRAO){
        String email
        int tentativas = 0

        while(tentativas < maxTentativas){
            email = getStringInput()
            if (validateService.isEmailValid(email)){
                return email
            } else{
                tentativas++
            }
        }
        throw new RuntimeException("Máximo de tentativas excedido para email")
    }

    String getEstadoInput(int maxTentativas = MAX_TENTATIVAS_PADRAO){
        String estado
        int tentativas = 0

        while(tentativas < maxTentativas){
            estado = getStringInput().toUpperCase()
            if(validateService.isEstadoValid(estado)){
                return estado
            } else{
                tentativas++
            }
        }
        throw new RuntimeException("Máximo de tentativas excedido para estado")
    }

    String getCepInput(int maxTentativas = MAX_TENTATIVAS_PADRAO){
        String cep
        int tentativas = 0

        while(tentativas < maxTentativas){
            cep = getStringInput()
            if (validateService.isCEPValid(cep)){
                return cep
            } else{
                tentativas++
            }
        }
        throw new RuntimeException("Máximo de tentativas excedido para CEP")
    }

    String getDescricaoInput(int maxTentativas = MAX_TENTATIVAS_PADRAO){
        return getStringInput(maxTentativas)
    }

    List<Competencia> getCompetenciasInput(List<Competencia> listaComp, int maxTentativas = MAX_TENTATIVAS_PADRAO) {
        List<Competencia> listCompetenciasSelecionadas = []
        int tentativasInvalidas = 0

        while (tentativasInvalidas < maxTentativas) {
            println()
            println("Competências selecionadas:")
            listCompetenciasSelecionadas.each {println("- " + it.nome)}
            println()
            print("Digite uma competência para adicionar [0 para sair]: ")

            int opcao
            try {
                opcao = getIntInput(maxTentativas - tentativasInvalidas)
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

    String getCpfInput(int maxTentativas = MAX_TENTATIVAS_PADRAO){
        String cpf
        int tentativas = 0

        while(tentativas < maxTentativas){
            cpf = getStringInput()
            if (validateService.isCPFValid(cpf)){
                return cpf
            } else{
                tentativas++
            }
        }
        throw new RuntimeException("Máximo de tentativas excedido para CPF")
    }

    String getCnpjInput(int maxTentativas = MAX_TENTATIVAS_PADRAO){
        String cnpj
        int tentativas = 0

        while(tentativas < maxTentativas){
            cnpj = getStringInput()
            if (cnpj ==~ /^\d{2}(\.\d{3}){2}\/\d{4}-\d{2}$|^\d{14}$/){
                return cnpj
            } else{
                println "CNPJ inválido! Tente novamente"
                tentativas++
            }
        }
        throw new RuntimeException("Máximo de tentativas excedido para CNPJ")
    }

    String getPaisInput(int maxTentativas = MAX_TENTATIVAS_PADRAO){
        return getStringInput(maxTentativas)
    }

    int getIntInput(int maxTentativas = MAX_TENTATIVAS_PADRAO){
        int tentativas = 0
        while (tentativas < maxTentativas){
            try{
                return Integer.parseInt(br.readLine())
            } catch (Exception ignored){
                println("Input inválido")
                tentativas++
            }
        }
        throw new RuntimeException("Máximo de tentativas excedido para input inteiro")
    }

    int getIdadeInput(int maxTentativas = MAX_TENTATIVAS_PADRAO){
        return getIntInput(maxTentativas)
    }

    String getSenhaInput(int maxTentativas = MAX_TENTATIVAS_PADRAO) {
        return getStringInput(maxTentativas)
    }

}