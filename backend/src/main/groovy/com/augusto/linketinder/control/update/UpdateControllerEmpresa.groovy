package com.augusto.linketinder.control.update

import com.augusto.linketinder.DAO.DAO_Empresa
import com.augusto.linketinder.model.pessoa.Empresa

class UpdateControllerEmpresa extends BaseUpdateController {

    private final DAO_Empresa empresaDao = new DAO_Empresa()

    void updateEmpresa() {
        List<Empresa> empresas = empresaDao.listAll()
        int empresaId = solicitarId(
                empresas.id as List<Integer>,
                "Digite o número da empresa [0 para cancelar]: ",
                "Nenhuma empresa cadastrada."
        )

        if (empresaId == 0) {
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
            empresaDao.updateField(empresaId, coluna, novoValor)
            println "Empresa atualizada com sucesso."
        } catch (Exception e) {
            println "Erro ao atualizar empresa: ${e.message}"
        }
    }

    private Map<String, String> camposDisponiveis() {
        return [
                'nome'     : 'nome',
                'email'    : 'email',
                'estado'   : 'estado',
                'cep'      : 'cep',
                'país'     : 'pais',
                'CNPJ'     : 'cnpj',
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
                case 'pais':
                    print "Digite o novo país: "
                    return cadastroController.getPaisInput()
                case 'cnpj':
                    print "Digite o novo CNPJ: "
                    return cadastroController.getCnpjInput()
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
