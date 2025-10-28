package com.augusto.linketinder.view.update

import com.augusto.linketinder.control.UpdateController
import com.augusto.linketinder.model.Competencia
import com.augusto.linketinder.service.InputService

class UpdateCompetenciaView extends BaseUpdateView {

    UpdateCompetenciaView() {
        super()
    }

    UpdateCompetenciaView(UpdateController updateController, InputService inputService) {
        super(updateController, inputService)
    }

    void show() {
        println 'Atualização de Competência'
        println()

        List<Competencia> competencias = updateController.listarCompetencias()
        if (!exibirListaOuAviso(competencias, 'Nenhuma competência cadastrada.')) {
            return
        }

        List<String> opcoes = [
                'Atualizar nome',
                'Gerenciar relacionamento com candidato',
                'Gerenciar relacionamento com empresa',
                'Gerenciar relacionamento com vaga'
        ]

        Integer opcao = selecionarOpcao(opcoes, 'Opções de atualização:')
        if (opcao == null) {
            return
        }

        switch (opcao) {
            case 1:
                atualizarNome(competencias)
                break
            case 2:
                gerenciarRelacionamento('candidato')
                break
            case 3:
                gerenciarRelacionamento('empresa')
                break
            case 4:
                gerenciarRelacionamento('vaga')
                break
            default:
                println 'Opção inválida.'
        }
    }

    private void atualizarNome(List<Competencia> competencias) {
        imprimirItens(competencias, 'id', 'nome')

        int competenciaId = solicitarId(competencias.collect { it.id }, 'Digite o ID da competência [0 para cancelar]: ')
        if (competenciaId == 0) {
            return
        }

        try {
            print 'Digite o novo nome: '
            String novoNome = inputService.getNomeInput()
            updateController.atualizarCompetenciaNome(competenciaId, novoNome)
            println 'Competência atualizada com sucesso.'
        } catch (Exception e) {
            println "Erro ao atualizar competência: ${e.message}"
        }
    }

    private void gerenciarRelacionamento(String objeto) {
        List<String> opcoes = ['Adicionar relacionamento', 'Remover relacionamento']
        Integer opcao = selecionarOpcao(opcoes, 'Opções:')
        if (opcao == null) {
            return
        }

        try {
            println updateController.listarRelacionamentos(objeto)
        } catch (Exception e) {
            println "Erro ao listar relacionamentos: ${e.message}"
            return
        }

        int idCompetencia = solicitarValorInteiro('Digite o ID da competência [0 para cancelar]: ')
        if (idCompetencia == 0) {
            return
        }

        int idObjeto = solicitarValorInteiro("Digite o ID de ${objeto} [0 para cancelar]: ")
        if (idObjeto == 0) {
            return
        }

        try {
            if (opcao == 1) {
                updateController.adicionarRelacionamento(objeto, idCompetencia, idObjeto)
            } else {
                updateController.removerRelacionamento(objeto, idCompetencia, idObjeto)
            }
            println 'Relacionamento atualizado com sucesso.'
        } catch (Exception e) {
            println "Erro ao atualizar relacionamento: ${e.message}"
        }
    }
}
