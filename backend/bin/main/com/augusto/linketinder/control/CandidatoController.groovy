package com.augusto.linketinder.control

import com.augusto.linketinder.dao.CompetenciaDAO
import com.augusto.linketinder.dao.DaoFactory
import com.augusto.linketinder.model.Competencia
import com.augusto.linketinder.model.pessoa.Candidato
import com.augusto.linketinder.service.CandidatoService
import com.augusto.linketinder.service.InputService
import com.augusto.linketinder.view.CandidatoView
import com.augusto.linketinder.view.helper.ViewHelper

class CandidatoController {
    ViewHelper viewHelper
    CompetenciaDAO competenciaDAO
    CandidatoService candidatoService
    CandidatoView candidatoView
    InputService inputService

    CandidatoController(ViewHelper viewHelper,
                        CompetenciaDAO competenciaDAO,
                        CandidatoService candidatoService,
                        CandidatoView candidatoView,
                        InputService inputService){
        this.viewHelper = viewHelper
        this.competenciaDAO = competenciaDAO
        this.candidatoService = candidatoService
        this.candidatoView = candidatoView
        this.inputService = inputService
    }

    CandidatoController() {
        this(new ViewHelper(), DaoFactory.getCompetenciaDAO(), new CandidatoService(DaoFactory.getCandidatoDAO()), new CandidatoView(), new InputService())
    }

    void createCandidato(){
        Candidato pessoa = new Candidato()

        println "Cadastro do Candidato"
        println()

        candidatoView.printMessage("Digite o Nome do Candidato: ")
        pessoa.nome = inputService.getNomeInput()

        candidatoView.printMessage("Digite o Email do Candidato: ")
        pessoa.email = inputService.getEmailInput()

        candidatoView.printMessage("Digite o Estado do Candidato: ")
        pessoa.estado = inputService.getEstadoInput()

        candidatoView.printMessage("Digite o CEP do Candidato: ")
        pessoa.cep = inputService.getCepInput()

        candidatoView.printMessage("Digite a Descrição do Candidato: ")
        pessoa.descricao = inputService.getDescricaoInput()

        candidatoView.printMessage("Digite o CPF do Candidato: ")
        pessoa.cpf = inputService.getCpfInput()

        candidatoView.printMessage("Digite a idade do Candidato: ")
        pessoa.idade = inputService.getIdadeInput()

        println()
        candidatoView.printlnMessage("Competências disponíveis:")
        List<Competencia> listaComp = competenciaDAO.listAll()
        viewHelper.printAllAvailableCompetencias(listaComp)
        pessoa.competencias = inputService.getCompetenciasInput(listaComp)

        print("Digite a senha do Candidato: ")
        pessoa.senha = inputService.getSenhaInput()

        candidatoService.createCandidato(pessoa)
    }

    void readCandidato(){
        candidatoView.showReadCandidatos(candidatoService.getCandidatoList())
    }

    void updateCandidato(){

        println 'Atualização de Candidato'
        println()

        List<Candidato> candidatos = candidatoService.getCandidatoList()
        if (candidatos == null || candidatos.isEmpty()) {
            candidatoView.printlnMessage('Nenhum candidato cadastrado.')
            return
        }

        candidatos.each { c ->
            println "${c.id} - ${c.nome}"
        }

        candidatoView.printMessage('Digite o número do candidato [0 para cancelar]: ')
        int candidatoId = inputService.getIntInput()
        if (candidatoId == 0) {
            return
        }

        Map<String, String> campos = candidatoService.getCamposCandidato()
        // exibir opções numeradas
        println()
        int idx = 1
        List<Map.Entry<String, String>> entries = campos.entrySet().toList()
        entries.each { e ->
            println("${idx} - ${e.key}")
            idx++
        }

        candidatoView.printMessage('Escolha o campo a ser atualizado [0 para cancelar]: ')
        int opcaoCampo = inputService.getIntInput()
        if (opcaoCampo == 0) {
            return
        }

        if (opcaoCampo < 1 || opcaoCampo > entries.size()) {
            println 'Opção inválida.'
            return
        }

        Map.Entry<String, String> campo = entries[opcaoCampo - 1]
        String coluna = campo.value

        Object novoValor
        try {
            switch (coluna) {
                case 'nome':
                    candidatoView.printMessage('Digite o novo nome: ')
                    novoValor = inputService.getNomeInput()
                    break
                case 'email':
                    candidatoView.printMessage('Digite o novo email: ')
                    novoValor = inputService.getEmailInput()
                    break
                case 'estado':
                    candidatoView.printMessage('Digite o novo estado (UF): ')
                    novoValor = inputService.getEstadoInput()
                    break
                case 'cep':
                    candidatoView.printMessage('Digite o novo CEP: ')
                    novoValor = inputService.getCepInput()
                    break
                case 'idade':
                    candidatoView.printMessage('Digite a nova idade: ')
                    novoValor = inputService.getIdadeInput()
                    break
                case 'cpf':
                    candidatoView.printMessage('Digite o novo CPF: ')
                    novoValor = inputService.getCpfInput()
                    break
                case 'descricao':
                    candidatoView.printMessage('Digite a nova descrição: ')
                    novoValor = inputService.getDescricaoInput()
                    break
                case 'senha':
                    candidatoView.printMessage('Digite a nova senha: ')
                    novoValor = inputService.getSenhaInput()
                    break
                default:
                    println 'Campo não suportado.'
                    return
            }
        } catch (Exception ignored) {
            println 'Limite de tentativas excedido ou input inválido.'
            return
        }

        try {
            atualizarCandidatoCampo(candidatoId, coluna, novoValor)
            println 'Candidato atualizado com sucesso.'
        } catch (Exception e) {
            println "Erro ao atualizar candidato: ${e.message}"
        }
    }


    void atualizarCandidatoCampo(int id, String coluna, Object novoValor) {
        candidatoService.updateCandidato(id, coluna, novoValor)
    }

    List listarCandidatos() {
        return candidatoService.getCandidatoList()
    }

    Map<String, String> camposCandidato() {
        return candidatoService.getCamposCandidato()
    }

    void deleteCandidato(){
        candidatoView.showDeleteCandidato()
        int id = inputService.getIntInput()
        candidatoService.deleteCandidato(id)
    }


}
