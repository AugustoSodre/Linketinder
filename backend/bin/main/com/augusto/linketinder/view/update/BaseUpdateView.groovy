package com.augusto.linketinder.view.update

import com.augusto.linketinder.control.UpdateController
import com.augusto.linketinder.service.InputService

/**
 * Shared helper utilities to keep individual update views focused on one responsibility.
 */
class BaseUpdateView {

    protected final UpdateController updateController
    protected final InputService inputService

    BaseUpdateView() {
        this(new UpdateController(), new InputService())
    }

    BaseUpdateView(UpdateController updateController, InputService inputService) {
        this.updateController = updateController
        this.inputService = inputService
    }

    protected boolean exibirListaOuAviso(List registros, String mensagemVazio) {
        if (!registros) {
            println mensagemVazio
            return false
        }
        return true
    }

    protected void imprimirItens(List itens, String propriedadeId, String propriedadeDescricao) {
        for (Object item : itens) {
            def id = item."${propriedadeId}"
            def descricao = item."${propriedadeDescricao}"
            println "${id} - ${descricao}"
        }
        println()
    }

    protected int solicitarId(List<Integer> idsValidos, String prompt) {
        if (!idsValidos) {
            return 0
        }

        int opcao = -1
        while (!idsValidos.contains(opcao) && opcao != 0) {
            try {
                print prompt
                opcao = inputService.getIntInput()
            } catch (Exception ignored) {
                println "Operação cancelada ou input inválido."
                return 0
            }

            if (opcao != 0 && !idsValidos.contains(opcao)) {
                println "ID não encontrado. Tente novamente."
            }
        }

        return opcao
    }

    protected Map.Entry<String, String> selecionarCampo(Map<String, String> campos) {
        if (!campos) {
            println "Nenhum campo disponível."
            return null
        }

        List<Map.Entry<String, String>> entradas = new ArrayList<>(campos.entrySet())
        println "Campos disponíveis para atualização:"
        entradas.eachWithIndex { Map.Entry<String, String> entry, int idx ->
            println "${idx + 1} - ${entry.key}"
        }

        while (true) {
            try {
                print "Digite o número do campo [0 para cancelar]: "
                int opcao = inputService.getIntInput()
                if (opcao == 0) {
                    return null
                }
                if (opcao >= 1 && opcao <= entradas.size()) {
                    return entradas[opcao - 1]
                }
                println "Opção inválida."
            } catch (Exception ignored) {
                println "Operação cancelada ou input inválido."
                return null
            }
        }
    }

    protected Integer selecionarOpcao(List<String> opcoes, String titulo) {
        if (!opcoes) {
            return null
        }

        println titulo
        opcoes.eachWithIndex { String texto, int idx -> println "${idx + 1} - ${texto}" }

        while (true) {
            try {
                print "Digite o número da opção [0 para cancelar]: "
                int opcao = inputService.getIntInput()
                if (opcao == 0) {
                    return null
                }
                if (opcao >= 1 && opcao <= opcoes.size()) {
                    return opcao
                }
                println "Opção inválida."
            } catch (Exception ignored) {
                println "Operação cancelada ou input inválido."
                return null
            }
        }
    }

    protected int solicitarValorInteiro(String prompt) {
        try {
            print prompt
            return inputService.getIntInput()
        } catch (Exception ignored) {
            println 'Operação cancelada ou input inválido.'
            return 0
        }
    }
}
