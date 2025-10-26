package com.augusto.linketinder.control.update

import com.augusto.linketinder.DAO.DAO_Competencia
import com.augusto.linketinder.model.Competencia

class UpdateControllerCompetencia extends BaseUpdateController {

    private final DAO_Competencia competenciaDao = new DAO_Competencia()
    private final HelperUpdateController helper = new HelperUpdateController()

    void updateCompetencia() {
        List<String> opcoes = [
                'Atualizar nome',
                'Gerenciar relacionamento com candidato',
                'Gerenciar relacionamento com empresa',
                'Gerenciar relacionamento com vaga'
        ]

        println 'Opções de atualização:'
        opcoes.eachWithIndex { String texto, int idx -> println "${idx + 1} - ${texto}" }

        int opcao = -1
        while (opcao < 0 || opcao > opcoes.size()) {
            try {
                print 'Digite o número da opção [0 para cancelar]: '
                opcao = cadastroController.getIntInput()
            } catch (Exception ignored) {
                println 'Operação cancelada ou input inválido.'
                return
            }

            if (opcao < 0 || opcao > opcoes.size()) {
                println 'Opção inválida.'
            }
        }

        if (opcao == 0) {
            return
        }

        switch (opcao) {
            case 1:
                atualizarNome()
                break
            case 2:
                helper.handleCompObjeto('candidato')
                break
            case 3:
                helper.handleCompObjeto('empresa')
                break
            case 4:
                helper.handleCompObjeto('vaga')
                break
            default:
                println 'Opção inválida.'
        }
    }

    private void atualizarNome() {
        List<Competencia> competencias = competenciaDao.listAll()
        int competenciaId = solicitarId(
                competencias.id as List<Integer>,
                'Digite o ID da competência [0 para cancelar]: ',
                'Nenhuma competência cadastrada.'
        )

        if (competenciaId == 0) {
            return
        }

        def novoNome
        try {
            print("Digite o novo nome: ")
            novoNome = cadastroController.getNomeInput()
        } catch (IllegalStateException e) {
            println e.message
            return
        }

        try {
            competenciaDao.updateName(competenciaId, novoNome.toString())
            println 'Competência atualizada com sucesso.'
        } catch (Exception e) {
            println "Erro ao atualizar competência: ${e.message}"
        }
    }
}
