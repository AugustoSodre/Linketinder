package com.augusto.linketinder.service

import com.augusto.linketinder.model.Competencia

class InputService {

    private ValidateService validateService
    private InputServiceCore inputServiceCore

    InputService() {
        this(new ValidateService(), new InputServiceCore())
    }

    InputService(ValidateService validateService) {
        this(validateService, new InputServiceCore())
    }

    InputService(ValidateService validateService, InputServiceCore inputServiceCore){
        this.validateService = validateService ?: new ValidateService()
        this.inputServiceCore = inputServiceCore ?: new InputServiceCore()
    }

    void setBr(BufferedReader bufferedReader) {
        if (bufferedReader == null) {
            throw new IllegalArgumentException("BufferedReader não pode ser nulo")
        }
        this.inputServiceCore = new InputServiceCore(bufferedReader, validateService)
    }

    String getNomeInput(){
        String nome = ""

        while(!validateService.isNomeValid(nome)){
            try{
                nome = inputServiceCore.getStringInput()
            } catch (Exception e){
                inputServiceCore.printMensagemErro("Nome")
                println e.message
            }
        }

        return nome
    }

    String getDescricaoInput(){
        String descricao = ""

        while(!validateService.isDescricaoValid(descricao)){
            try{
                descricao = inputServiceCore.getStringInput()
            } catch (Exception e){
                inputServiceCore.printMensagemErro("Descrição")
                println e.message
            }
        }

        return descricao
    }

    String getPaisInput(){
        String pais = ""

        while(!validateService.isPaisValid(pais)){
            try{
                pais = inputServiceCore.getStringInput()
            } catch (Exception e){
                inputServiceCore.printMensagemErro("País")
                println e.message
            }
        }

        return pais
    }

    int getIntInput() {
        return inputServiceCore.getIntInput()
    }

    int getIdadeInput(){
        return inputServiceCore.getIntInput()
    }

    String getSenhaInput() {
        String senha = ""

        while(!validateService.isSenhaValid(senha)){
            try{
                senha = inputServiceCore.getStringInput()
            } catch (Exception e){
                inputServiceCore.printMensagemErro("Senha")
                println e.message
            }
        }

        return senha
    }

    String getEmailInput(){
        String email = ""

        while(!validateService.isEmailValid(email)){
            try{
                email = inputServiceCore.getStringInput()
            } catch (Exception e){
                inputServiceCore.printMensagemErro("Email")
                println e.message
            }
        }

        return email
    }

    String getEstadoInput(){
        String estado = ""

        while(!validateService.isEstadoValid(estado)){
            try{
                estado = inputServiceCore.getStringInput()
                estado = estado.toUpperCase()
            } catch (Exception e){
                inputServiceCore.printMensagemErro("Estado")
                println e.message
            }
        }

        return estado
    }

    String getCepInput(){
        String cep = ""

        while(!validateService.isCEPValid(cep)){
            try{
                cep = inputServiceCore.getStringInput()
            } catch (Exception e){
                inputServiceCore.printMensagemErro("CEP")
                println e.message
            }
        }

        return cep
    }

    String getCpfInput(){
        String cpf = ""

        while(!validateService.isCPFValid(cpf)){
            try{
                cpf = inputServiceCore.getStringInput()
            } catch (Exception e){
                inputServiceCore.printMensagemErro("CPF")
                println e.message
            }
        }

        return cpf
    }

    String getCnpjInput(){
        String cnpj = ""

        while(!validateService.isCNPJValid(cnpj)){
            try{
                cnpj = inputServiceCore.getStringInput()
            } catch (Exception e){
                inputServiceCore.printMensagemErro("CNPJ")
                println e.message
            }
        }

        return cnpj
    }

    List<Competencia> getCompetenciasInput(List<Competencia> listaComp) {
        if (!validateService.isListCompetenciaValid(listaComp)) {
            throw new IllegalArgumentException("Lista de competências inválida")
        }

        List<Competencia> listCompetenciasSelecionadas = []

        while (true) {
            println()
            println("Competências selecionadas:")
            listCompetenciasSelecionadas.each {println("- " + it.nome)}
            println()
            print("Digite uma competência para adicionar [0 para sair]: ")

            int opcao
            try {
                opcao = inputServiceCore.getIntInput()
            } catch (RuntimeException e) {
                println e.message
                continue
            }

            if (opcao == 0) {
                break
            }

            if (!validateService.isCompetenciaOpcaoValida(opcao, listaComp.size())) {
                println("Opção inválida, tente novamente.")
                continue
            }

            Competencia comp = listaComp[opcao - 1]
            if (!validateService.isCompetenciaSelecionavel(listCompetenciasSelecionadas, comp)) {
                println("Competência já foi selecionada.")
                continue
            }

            listCompetenciasSelecionadas.add(comp)
        }

        return listCompetenciasSelecionadas
    }

}