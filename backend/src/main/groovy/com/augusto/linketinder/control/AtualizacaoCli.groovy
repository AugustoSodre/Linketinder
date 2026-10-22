package com.augusto.linketinder.control

import com.augusto.linketinder.service.InputService

class AtualizacaoCli {

    private final InputService inputService

    AtualizacaoCli(InputService inputService) {
        this.inputService = inputService
    }

    void executar(List itens, Map<String, String> campos, Closure atualizar) {
        if (!itens) {
            println 'Nenhum registro cadastrado.'
            return
        }
        itens.each { println "${it.id} - ${it.nome}" }
        print 'Digite o número do registro [0 para cancelar]: '
        int id = inputService.getIntInput()
        if (id == 0) {
            return
        }

        List<Map.Entry<String, String>> entries = campos.entrySet().toList()
        println()
        entries.eachWithIndex { e, i -> println "${i + 1} - ${e.key}" }
        print 'Escolha o campo a ser atualizado [0 para cancelar]: '
        int opcao = inputService.getIntInput()
        if (opcao == 0) {
            return
        }
        if (opcao < 1 || opcao > entries.size()) {
            println 'Opção inválida.'
            return
        }

        Map.Entry<String, String> campo = entries[opcao - 1]
        print "Digite o novo valor de ${campo.key}: "
        atualizar(id, campo.value, inputService.getCampoInput(campo.value))
        println 'Atualizado com sucesso.'
    }
}
