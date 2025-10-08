package com.augusto.linketinder.control

import com.augusto.linketinder.DAO.DAO
import com.augusto.linketinder.model.pessoa.Candidato
import com.augusto.linketinder.model.pessoa.Empresa

class UpdateController {

    DAO dao = new DAO()
    CadastroController cadastroController = new CadastroController()

    void updateCandidato(){
        List<Candidato> listaCandidatos = dao.listCandidatos()
        int id = -1
        int opcao = -1

        while(listaCandidatos.id.contains(id)){
            try{
                print "Digite o número do candidato [0 para cancelar]: "
                opcao = cadastroController.getIntInput()
                id = opcao
            } catch (Exception e){
                println "Operação cancelada ou input inválido."
                return
            }
        }

        if (opcao == 0) return

        // Campos disponíveis para atualização
        def campos = [
                nome: 'nome',
                email: 'email',
                estado: 'estado',
                cep: 'cep',
                idade: 'idade',
                cpf: 'cpf',
                descricao: 'descricao',
                senha: 'senha'
        ]

        println "Campos disponíveis para atualização:"
        int idx = 1
        campos.each { k, v -> println "${idx++} - ${k}" }

        print "Digite o número do campo a ser atualizado [0 para cancelar]: "
        int campoOpc = -1
        try{
            campoOpc = cadastroController.getIntInput()
        } catch (Exception e){
            println "Operação cancelada ou input inválido."
            return
        }

        if (campoOpc == 0) return
        if (campoOpc < 1 || campoOpc > campos.size()){
            println "Opção inválida de campo."
            return
        }

        String campoSelecionado = campos.keySet().toList()[campoOpc - 1]
        String coluna = campos[campoSelecionado]

        // Get new value with validation according to field
        def novoValor
        try{
            switch (coluna) {
                case 'nome':
                    print "Digite o novo nome: "
                    novoValor = cadastroController.getNomeInput()
                    break
                case 'email':
                    print "Digite o novo email: "
                    novoValor = cadastroController.getEmailInput()
                    break
                case 'estado':
                    print "Digite o novo estado (UF): "
                    novoValor = cadastroController.getEstadoInput()
                    break
                case 'cep':
                    print "Digite o novo CEP: "
                    novoValor = cadastroController.getCepInput()
                    break
                case 'idade':
                    print "Digite a nova idade: "
                    novoValor = cadastroController.getIdadeInput()
                    break
                case 'cpf':
                    print "Digite o novo CPF: "
                    novoValor = cadastroController.getCpfInput()
                    break
                case 'descricao':
                    print "Digite a nova descrição: "
                    novoValor = cadastroController.getDescricaoInput()
                    break
                case 'senha':
                    print "Digite a nova senha: "
                    novoValor = cadastroController.getSenhaInput()
                    break
                default:
                    println "Campo não suportado."
                    return
            }
        } catch (Exception e){
            println "Limite de tentativas excedido ou input inválido."
            return
        }

        try{
            dao.update('candidato', coluna, novoValor, id)
        } catch (Exception e){
            println "Erro ao atualizar candidato: ${e.message}"
        }
    }

    void updateEmpresa(){
        List<Empresa> listaEmpresas = dao.listEmpresas()
        int id = -1
        int opcao = -1
        while (!listaEmpresas.id.contains(id)){
            try{
                print "Digite o número da empresa [0 para cancelar]: "
                opcao = cadastroController.getIntInput()
                id = opcao
            } catch (Exception e){
                println "Operação cancelada ou input inválido."
                return
            }
        }

        if (opcao == 0) return

        // Campos disponíveis para atualização
        def campos = [
                nome: 'nome',
                email: 'email',
                estado: 'estado',
                cep: 'cep',
                pais: 'pais',
                cnpj: 'cnpj',
                descricao: 'descricao',
                senha: 'senha'
        ]

        println "Campos disponíveis para atualização:"
        int idx = 1
        campos.each { k, v -> println "${idx++} - ${k}" }

        print "Digite o número do campo a ser atualizado [0 para cancelar]: "
        int campoOpc = -1
        try{
            campoOpc = cadastroController.getIntInput()
        } catch (Exception e){
            println "Operação cancelada ou input inválido."
            return
        }

        if (campoOpc == 0) return
        if (campoOpc < 1 || campoOpc > campos.size()){
            println "Opção inválida de campo."
            return
        }

        String campoSelecionado = campos.keySet().toList()[campoOpc - 1]
        String coluna = campos[campoSelecionado]

        // Get new value with validation according to field
        def novoValor
        try{
            switch (coluna) {
                case 'nome':
                    print "Digite o novo nome: "
                    novoValor = cadastroController.getNomeInput()
                    break
                case 'email':
                    print "Digite o novo email: "
                    novoValor = cadastroController.getEmailInput()
                    break
                case 'estado':
                    print "Digite o novo estado (UF): "
                    novoValor = cadastroController.getEstadoInput()
                    break
                case 'cep':
                    print "Digite o novo CEP: "
                    novoValor = cadastroController.getCepInput()
                    break
                case 'pais':
                    print "Digite o novo país: "
                    novoValor = cadastroController.getPaisInput()
                    break
                case 'cnpj':
                    print "Digite o novo CNPJ: "
                    novoValor = cadastroController.getCnpjInput()
                    break
                case 'descricao':
                    print "Digite a nova descrição: "
                    novoValor = cadastroController.getDescricaoInput()
                    break
                case 'senha':
                    print "Digite a nova senha: "
                    novoValor = cadastroController.getSenhaInput()
                    break
                default:
                    println "Campo não suportado."
                    return
            }
        } catch (Exception e){
            println "Limite de tentativas excedido ou input inválido."
            return
        }

        try{
            dao.update('empresa', coluna, novoValor, id)
        } catch (Exception e){
            println "Erro ao atualizar candidato: ${e.message}"
        }
    }

}
