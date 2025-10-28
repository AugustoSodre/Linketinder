package com.augusto.linketinder.view.update

import com.augusto.linketinder.control.UpdateController
import com.augusto.linketinder.model.Vaga
import com.augusto.linketinder.service.InputService

class UpdateVagaView extends BaseUpdateView {

    UpdateVagaView() {
        super()
    }

    UpdateVagaView(UpdateController updateController, InputService inputService) {
        super(updateController, inputService)
    }

    void show() {
        println 'Atualização de Vaga'
        println()

        List<Vaga> vagas = updateController.listarVagas()
        if (!exibirListaOuAviso(vagas, 'Nenhuma vaga cadastrada.')) {
            return
        }

        imprimirItens(vagas, 'id', 'nome')

        int vagaId = solicitarId(vagas.collect { it.id }, 'Digite o número da vaga [0 para cancelar]: ')
        if (vagaId == 0) {
            return
        }

        Map.Entry<String, String> campo = selecionarCampo(updateController.camposVaga())
        if (campo == null) {
            return
        }

        Object novoValor = lerValor(campo.value)
        if (novoValor == null) {
            return
        }

        try {
            updateController.atualizarVagaCampo(vagaId, campo.value, novoValor)
            println 'Vaga atualizada com sucesso.'
        } catch (Exception e) {
            println "Erro ao atualizar vaga: ${e.message}"
        }
    }

    private Object lerValor(String coluna) {
        try {
            switch (coluna) {
                case 'id_empresa':
                    print 'Digite o novo ID da empresa: '
                    return inputService.getIntInput()
                case 'nome':
                    print 'Digite o novo título: '
                    return inputService.getNomeInput()
                case 'descricao':
                    print 'Digite a nova descrição: '
                    return inputService.getDescricaoInput()
                case 'cidade':
                    print 'Digite a nova cidade: '
                    return inputService.getNomeInput()
                case 'estado':
                    print 'Digite o novo estado (UF): '
                    return inputService.getEstadoInput()
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
