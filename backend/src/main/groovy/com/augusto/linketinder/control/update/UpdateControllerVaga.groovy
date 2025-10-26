package com.augusto.linketinder.control.update

import com.augusto.linketinder.DAO.DAO_Vaga
import com.augusto.linketinder.control.CadastroController
import com.augusto.linketinder.model.Vaga

class UpdateControllerVaga extends BaseUpdateController {

    private final DAO_Vaga vagaDao = new DAO_Vaga()

    UpdateControllerVaga() {
        super()
    }

    UpdateControllerVaga(CadastroController cadastroController) {
        super(cadastroController)
    }

    void updateVaga() {
        List<Vaga> vagas = vagaDao.listAll()
        int vagaId = solicitarId(
                vagas.id as List<Integer>,
                "Digite o número da vaga [0 para cancelar]: ",
                "Nenhuma vaga cadastrada."
        )

        if (vagaId == 0) {
            return
        }

        Map<String, String> campos = camposDisponiveis()
        int opcaoCampo = solicitarOpcaoCampo(campos, "Campos disponíveis para atualização:")
        if (opcaoCampo == 0) {
            return
        }

        String coluna = campos.values().toList()[opcaoCampo - 1]

        def novoValor
        try {
            novoValor = obterNovoValor(coluna)
        } catch (IllegalStateException e) {
            println e.message
            return
        }

        try {
            vagaDao.updateField(vagaId, coluna, novoValor)
            println "Vaga atualizada com sucesso."
        } catch (Exception e) {
            println "Erro ao atualizar vaga: ${e.message}"
        }
    }

    private Map<String, String> camposDisponiveis() {
        return [
                'ID da empresa': 'id_empresa',
                'nome'        : 'nome',
                'descrição'   : 'descricao',
                'cidade'      : 'cidade',
                'estado'      : 'estado'
        ]
    }

    private def obterNovoValor(String coluna) {
        try {
            switch (coluna) {
                case 'id_empresa':
                    print "Digite o novo ID da empresa: "
                    return cadastroController.getIntInput()
                case 'nome':
                    print "Digite o novo título: "
                    return cadastroController.getNomeInput()
                case 'descricao':
                    print "Digite a nova descrição: "
                    return cadastroController.getDescricaoInput()
                case 'cidade':
                    print "Digite a nova cidade: "
                    return cadastroController.getNomeInput()
                case 'estado':
                    print "Digite o novo estado (UF): "
                    return cadastroController.getEstadoInput()
                default:
                    throw new IllegalStateException("Campo não suportado.")
            }
        } catch (Exception ignored) {
            throw new IllegalStateException("Limite de tentativas excedido ou input inválido.")
        }
    }
}
