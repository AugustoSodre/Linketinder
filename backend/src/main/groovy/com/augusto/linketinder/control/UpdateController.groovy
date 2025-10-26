package com.augusto.linketinder.control

import com.augusto.linketinder.DAO.DAO_Candidato
import com.augusto.linketinder.DAO.DAO_Competencia
import com.augusto.linketinder.DAO.DAO_Empresa
import com.augusto.linketinder.DAO.DAO_Vaga
import com.augusto.linketinder.model.Competencia
import com.augusto.linketinder.model.Vaga
import com.augusto.linketinder.model.pessoa.Candidato
import com.augusto.linketinder.model.pessoa.Empresa

class UpdateController {

    private final DAO_Candidato candidatoDao = new DAO_Candidato()
    private final DAO_Empresa empresaDao = new DAO_Empresa()
    private final DAO_Vaga vagaDao = new DAO_Vaga()
    private final DAO_Competencia competenciaDao = new DAO_Competencia()
    CadastroController cadastroController = new CadastroController()

    void updateCandidato(){
        List<Candidato> listaCandidatos = candidatoDao.listAll()
        int id = -1
        int opcao = -1

        while(!listaCandidatos*.id.contains(opcao) && opcao != 0){
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
                'nome': 'nome',
                'email': 'email',
                'estado': 'estado',
                'cep': 'cep',
                'idade': 'idade',
                'cpf': 'cpf',
                'descrição': 'descricao',
                'senha': 'senha'
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
            candidatoDao.updateField(id, coluna, novoValor)
        } catch (Exception e){
            println "Erro ao atualizar candidato: ${e.message}"
        }
    }

    void updateEmpresa(){
        List<Empresa> listaEmpresas = empresaDao.listAll()
        int id = -1
        int opcao = -1
        while (!listaEmpresas*.id.contains(opcao) && opcao != 0){
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
                'nome': 'nome',
                'email': 'email',
                'estado': 'estado',
                'cep': 'cep',
                'país': 'pais',
                'cnpj': 'cnpj',
                'descrição': 'descricao',
                'senha': 'senha'
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
            empresaDao.updateField(id, coluna, novoValor)
        } catch (Exception e){
            println "Erro ao atualizar empresa: ${e.message}"
        }
    }

    void updateVaga(){
        List<Vaga> listaVagas = vagaDao.listAll()
        int id = -1
        int opcao = -1

        while (!listaVagas*.id.contains(opcao) && opcao != 0){
            try{
                print "Digite o número da vaga [0 para cancelar]: "
                opcao = cadastroController.getIntInput()
                id = opcao
            } catch (Exception e){
                println "Operação cancelada ou input inválido."
                return
            }
        }

        if(opcao == 0){return}

        // Campos disponíveis para atualização
        def campos = [
                'ID da empresa': 'id_empresa',
                'nome': 'nome',
                'descrição': 'descricao',
                'cidade': 'cidade',
                'estado': 'estado'
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
                case 'id_empresa':
                    print "Digite o novo ID da Empresa: "
                    novoValor = cadastroController.getIntInput()
                    break
                case 'nome':
                    print "Digite o novo título: "
                    novoValor = cadastroController.getNomeInput()
                    break
                case 'descricao':
                    print "Digite a nova descrição: "
                    novoValor = cadastroController.getDescricaoInput()
                    break
                case 'cidade':
                    print "Digite a nova cidade: "
                    novoValor = cadastroController.getNomeInput()
                    break
                case 'estado':
                    print "Digite o novo estado (UF): "
                    novoValor = cadastroController.getEstadoInput()
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
            vagaDao.updateField(id, coluna, novoValor)
        } catch (Exception e){
            println "Erro ao atualizar vaga: ${e.message}"
        }
    }

    void updateComp(){
        List<Competencia> listaComp = competenciaDao.listAll()
        int id = -1
        int opcao = -1

        // Campos disponíveis para atualização
        def campos = [
                'nome': 'nome',
                'relacionamento com candidato': 'cc',
                'relacionamento com empresa': 'ce',
                'relacionamento com vaga': 'cv',
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
                    print "Digite o ID da Competência: "
                    id = cadastroController.getIntInput()
                    print "Digite o novo nome da Competência: "
                    novoValor = cadastroController.getNomeInput()
                    try{
                        competenciaDao.updateName(id, novoValor)
                    } catch (Exception e){
                        println "Erro ao atualizar competência: ${e.message}"
                    }
                    break

                case 'cc':
                    new UpdateController_Helper().handleCompObjeto("candidato")
                    break

                case 'ce':
                    new UpdateController_Helper().handleCompObjeto("empresa")
                    break

                case 'cv':
                    new UpdateController_Helper().handleCompObjeto("vaga")
                    break

                default:
                    println "Campo não suportado."
                    return
            }
        } catch (Exception e){
            println "Limite de tentativas excedido ou input inválido."
        }


    }
}
