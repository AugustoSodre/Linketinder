package com.augusto.linketinder.view.update

import com.augusto.linketinder.model.pessoa.Candidato

class UpdateCandidatoView extends BaseUpdateView {

    void show() {
        println 'Atualização de Candidato'
        println()

        List<Candidato> candidatos = updateController.listarCandidatos()
        if (!exibirListaOuAviso(candidatos, 'Nenhum candidato cadastrado.')) {
            return
        }

        imprimirItens(candidatos, 'id', 'nome')

        int candidatoId = solicitarId(candidatos.collect { it.id }, 'Digite o número do candidato [0 para cancelar]: ')
        if (candidatoId == 0) {
            return
        }

        Map.Entry<String, String> campo = selecionarCampo(updateController.camposCandidato())
        if (campo == null) {
            return
        }

        Object novoValor = lerValor(campo.value)
        if (novoValor == null) {
            return
        }

        try {
            updateController.atualizarCandidatoCampo(candidatoId, campo.value, novoValor)
            println 'Candidato atualizado com sucesso.'
        } catch (Exception e) {
            println "Erro ao atualizar candidato: ${e.message}"
        }
    }

    private Object lerValor(String coluna) {
        try {
            switch (coluna) {
                case 'nome':
                    print 'Digite o novo nome: '
                    return inputService.getNomeInput()
                case 'email':
                    print 'Digite o novo email: '
                    return inputService.getEmailInput()
                case 'estado':
                    print 'Digite o novo estado (UF): '
                    return inputService.getEstadoInput()
                case 'cep':
                    print 'Digite o novo CEP: '
                    return inputService.getCepInput()
                case 'idade':
                    print 'Digite a nova idade: '
                    return inputService.getIdadeInput()
                case 'cpf':
                    print 'Digite o novo CPF: '
                    return inputService.getCpfInput()
                case 'descricao':
                    print 'Digite a nova descrição: '
                    return inputService.getDescricaoInput()
                case 'senha':
                    print 'Digite a nova senha: '
                    return inputService.getSenhaInput()
                default:
                    println 'Campo não suportado.'
                    return null
            }
        } catch (Exception ignored) {
            println 'Limite de tentativas excedido ou input inválido.'
            return null
        }
    }
}
