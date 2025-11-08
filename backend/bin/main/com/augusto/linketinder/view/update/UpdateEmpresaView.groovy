package com.augusto.linketinder.view.update

import com.augusto.linketinder.control.UpdateController
import com.augusto.linketinder.model.pessoa.Empresa
import com.augusto.linketinder.service.InputService

class UpdateEmpresaView extends BaseUpdateView {

    UpdateEmpresaView() {
        super()
    }

    UpdateEmpresaView(UpdateController updateController, InputService inputService) {
        super(updateController, inputService)
    }

    void show() {
        println 'Atualização de Empresa'
        println()

        List<Empresa> empresas = updateController.listarEmpresas()
        if (!exibirListaOuAviso(empresas, 'Nenhuma empresa cadastrada.')) {
            return
        }

        imprimirItens(empresas, 'id', 'nome')

        int empresaId = solicitarId(empresas.collect { it.id }, 'Digite o número da empresa [0 para cancelar]: ')
        if (empresaId == 0) {
            return
        }

        Map.Entry<String, String> campo = selecionarCampo(updateController.camposEmpresa())
        if (campo == null) {
            return
        }

        Object novoValor = lerValor(campo.value)
        if (novoValor == null) {
            return
        }

        try {
            updateController.atualizarEmpresaCampo(empresaId, campo.value, novoValor)
            println 'Empresa atualizada com sucesso.'
        } catch (Exception e) {
            println "Erro ao atualizar empresa: ${e.message}"
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
                case 'pais':
                    print 'Digite o novo país: '
                    return inputService.getPaisInput()
                case 'cnpj':
                    print 'Digite o novo CNPJ: '
                    return inputService.getCnpjInput()
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
