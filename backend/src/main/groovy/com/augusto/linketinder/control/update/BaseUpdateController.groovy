package com.augusto.linketinder.control.update

import com.augusto.linketinder.control.CadastroController

abstract class BaseUpdateController {

    protected final CadastroController cadastroController

    protected BaseUpdateController() {
        this(new CadastroController())
    }

    protected BaseUpdateController(CadastroController cadastroController) {
        this.cadastroController = cadastroController
    }

    protected int solicitarId(List<Integer> idsValidos, String mensagemPrompt, String mensagemListaVazia) {
        if (!idsValidos) {
            println mensagemListaVazia
            return 0
        }

        int opcao = -1
        while (!idsValidos.contains(opcao) && opcao != 0) {
            try {
                print mensagemPrompt
                opcao = cadastroController.getIntInput()
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

    protected int solicitarOpcaoCampo(Map<String, String> campos, String titulo) {
        if (!campos) {
            println "Nenhum campo disponível."
            return 0
        }

        println titulo
        int idx = 1
        campos.each { rotulo, valor -> println "${idx++} - ${rotulo}" }

        int opcao = -1
        while (opcao < 0 || opcao > campos.size()) {
            try {
                print "Digite o número do campo a ser atualizado [0 para cancelar]: "
                opcao = cadastroController.getIntInput()
            } catch (Exception ignored) {
                println "Operação cancelada ou input inválido."
                return 0
            }

            if (opcao < 0 || opcao > campos.size()) {
                println "Opção inválida."
            }
        }

        return opcao
    }
}
