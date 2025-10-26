package com.augusto.linketinder.control.update

import com.augusto.linketinder.DAO.DAO_Candidato
import com.augusto.linketinder.control.CadastroController
import com.augusto.linketinder.model.pessoa.Candidato

class UpdateControllerCandidato extends BaseUpdateController {

    private final DAO_Candidato candidatoDao = new DAO_Candidato()

    UpdateControllerCandidato() {
        super()
    }

    UpdateControllerCandidato(CadastroController cadastroController) {
        super(cadastroController)
    }

    void updateCandidato() {
        List<Candidato> candidatos = candidatoDao.listAll()
        int candidatoId = solicitarId(
                candidatos.id as List<Integer>,
                "Digite o número do candidato [0 para cancelar]: ",
                "Nenhum candidato cadastrado."
        )

        if (candidatoId == 0) {
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
            candidatoDao.updateField(candidatoId, coluna, novoValor)
            println "Candidato atualizado com sucesso."
        } catch (Exception e) {
            println "Erro ao atualizar candidato: ${e.message}"
        }
    }

    private Map<String, String> camposDisponiveis() {
        return [
                'nome'     : 'nome',
                'email'    : 'email',
                'estado'   : 'estado',
                'cep'      : 'cep',
                'idade'    : 'idade',
                'cpf'      : 'cpf',
                'descrição': 'descricao',
                'senha'    : 'senha'
        ]
    }

    private def obterNovoValor(String coluna) {
        try {
            switch (coluna) {
                case 'nome':
                    print "Digite o novo nome: "
                    return cadastroController.getNomeInput()
                case 'email':
                    print "Digite o novo email: "
                    return cadastroController.getEmailInput()
                case 'estado':
                    print "Digite o novo estado (UF): "
                    return cadastroController.getEstadoInput()
                case 'cep':
                    print "Digite o novo CEP: "
                    return cadastroController.getCepInput()
                case 'idade':
                    print "Digite a nova idade: "
                    return cadastroController.getIdadeInput()
                case 'cpf':
                    print "Digite o novo CPF: "
                    return cadastroController.getCpfInput()
                case 'descricao':
                    print "Digite a nova descrição: "
                    return cadastroController.getDescricaoInput()
                case 'senha':
                    print "Digite a nova senha: "
                    return cadastroController.getSenhaInput()
                default:
                    throw new IllegalStateException("Campo não suportado.")
            }
        } catch (Exception ignored) {
            throw new IllegalStateException("Limite de tentativas excedido ou input inválido.")
        }
    }
}
