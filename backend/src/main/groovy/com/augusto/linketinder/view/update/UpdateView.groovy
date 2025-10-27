package com.augusto.linketinder.view.update

import com.augusto.linketinder.control.UpdateController
import com.augusto.linketinder.model.Competencia
import com.augusto.linketinder.model.Vaga
import com.augusto.linketinder.model.pessoa.Candidato
import com.augusto.linketinder.model.pessoa.Empresa
import com.augusto.linketinder.service.InputService

class UpdateView {

    private final UpdateController updateController = new UpdateController()
    private final InputService inputService = new InputService()

    void showUpdateCandidato() {
        println "Atualização de Candidato"
        println()

        List<Candidato> candidatos = updateController.listarCandidatos()
        if (!exibirListaOuAviso(candidatos, "Nenhum candidato cadastrado.")) {
            return
        }

        imprimirItens(candidatos) { Candidato candidato ->
            println "${candidato.id} - ${candidato.nome}"
        }

        int candidatoId = solicitarId(candidatos.collect { it.id }, "Digite o número do candidato [0 para cancelar]: ")
        if (candidatoId == 0) {
            return
        }

        Map.Entry<String, String> campo = selecionarCampo(updateController.camposCandidato())
        if (campo == null) {
            return
        }

        Object novoValor = lerValorCandidato(campo.value)
        if (novoValor == null) {
            return
        }

        try {
            updateController.atualizarCandidatoCampo(candidatoId, campo.value, novoValor)
            println "Candidato atualizado com sucesso."
        } catch (Exception e) {
            println "Erro ao atualizar candidato: ${e.message}"
        }
    }

    void showUpdateEmpresa() {
        println "Atualização de Empresa"
        println()

        List<Empresa> empresas = updateController.listarEmpresas()
        if (!exibirListaOuAviso(empresas, "Nenhuma empresa cadastrada.")) {
            return
        }

        imprimirItens(empresas) { Empresa empresa ->
            println "${empresa.id} - ${empresa.nome}"
        }

        int empresaId = solicitarId(empresas.collect { it.id }, "Digite o número da empresa [0 para cancelar]: ")
        if (empresaId == 0) {
            return
        }

        Map.Entry<String, String> campo = selecionarCampo(updateController.camposEmpresa())
        if (campo == null) {
            return
        }

        Object novoValor = lerValorEmpresa(campo.value)
        if (novoValor == null) {
            return
        }

        try {
            updateController.atualizarEmpresaCampo(empresaId, campo.value, novoValor)
            println "Empresa atualizada com sucesso."
        } catch (Exception e) {
            println "Erro ao atualizar empresa: ${e.message}"
        }
    }

    void showUpdateVaga() {
        println "Atualização de Vaga"
        println()

        List<Vaga> vagas = updateController.listarVagas()
        if (!exibirListaOuAviso(vagas, "Nenhuma vaga cadastrada.")) {
            return
        }

        imprimirItens(vagas) { Vaga vaga ->
            println "${vaga.id} - ${vaga.nome}".toString()
        }

        int vagaId = solicitarId(vagas.collect { it.id }, "Digite o número da vaga [0 para cancelar]: ")
        if (vagaId == 0) {
            return
        }

        Map.Entry<String, String> campo = selecionarCampo(updateController.camposVaga())
        if (campo == null) {
            return
        }

        Object novoValor = lerValorVaga(campo.value)
        if (novoValor == null) {
            return
        }

        try {
            updateController.atualizarVagaCampo(vagaId, campo.value, novoValor)
            println "Vaga atualizada com sucesso."
        } catch (Exception e) {
            println "Erro ao atualizar vaga: ${e.message}"
        }
    }

    void showUpdateComp() {
        println "Atualização de Competência"
        println()

        List<Competencia> competencias = updateController.listarCompetencias()
        if (!exibirListaOuAviso(competencias, "Nenhuma competência cadastrada.")) {
            return
        }

        List<String> opcoes = [
                'Atualizar nome',
                'Gerenciar relacionamento com candidato',
                'Gerenciar relacionamento com empresa',
                'Gerenciar relacionamento com vaga'
        ]

        Integer opcao = selecionarOpcao(opcoes, 'Opções de atualização:')
        if (opcao == null) {
            return
        }

        switch (opcao) {
            case 1:
                atualizarNomeCompetencia(competencias)
                break
            case 2:
                gerenciarRelacionamento('candidato')
                break
            case 3:
                gerenciarRelacionamento('empresa')
                break
            case 4:
                gerenciarRelacionamento('vaga')
                break
            default:
                println 'Opção inválida.'
        }
    }

    private boolean exibirListaOuAviso(List registros, String mensagemVazio) {
        if (!registros) {
            println mensagemVazio
            return false
        }
        return true
    }

    private <T> void imprimirItens(List<T> itens, Closure<?> formatter) {
        itens.each { formatter(it) }
        println()
    }

    private int solicitarId(List<Integer> idsValidos, String prompt) {
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

    private Map.Entry<String, String> selecionarCampo(Map<String, String> campos) {
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

    private Integer selecionarOpcao(List<String> opcoes, String titulo) {
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

    private Object lerValorCandidato(String coluna) {
        try {
            switch (coluna) {
                case 'nome':
                    print "Digite o novo nome: "
                    return inputService.getNomeInput()
                case 'email':
                    print "Digite o novo email: "
                    return inputService.getEmailInput()
                case 'estado':
                    print "Digite o novo estado (UF): "
                    return inputService.getEstadoInput()
                case 'cep':
                    print "Digite o novo CEP: "
                    return inputService.getCepInput()
                case 'idade':
                    print "Digite a nova idade: "
                    return inputService.getIdadeInput()
                case 'cpf':
                    print "Digite o novo CPF: "
                    return inputService.getCpfInput()
                case 'descricao':
                    print "Digite a nova descrição: "
                    return inputService.getDescricaoInput()
                case 'senha':
                    print "Digite a nova senha: "
                    return inputService.getSenhaInput()
                default:
                    println "Campo não suportado."
                    return null
            }
        } catch (Exception ignored) {
            println "Limite de tentativas excedido ou input inválido."
            return null
        }
    }

    private Object lerValorEmpresa(String coluna) {
        try {
            switch (coluna) {
                case 'nome':
                    print "Digite o novo nome: "
                    return inputService.getNomeInput()
                case 'email':
                    print "Digite o novo email: "
                    return inputService.getEmailInput()
                case 'estado':
                    print "Digite o novo estado (UF): "
                    return inputService.getEstadoInput()
                case 'cep':
                    print "Digite o novo CEP: "
                    return inputService.getCepInput()
                case 'pais':
                    print "Digite o novo país: "
                    return inputService.getPaisInput()
                case 'cnpj':
                    print "Digite o novo CNPJ: "
                    return inputService.getCnpjInput()
                case 'descricao':
                    print "Digite a nova descrição: "
                    return inputService.getDescricaoInput()
                case 'senha':
                    print "Digite a nova senha: "
                    return inputService.getSenhaInput()
                default:
                    println "Campo não suportado."
                    return null
            }
        } catch (Exception ignored) {
            println "Limite de tentativas excedido ou input inválido."
            return null
        }
    }

    private Object lerValorVaga(String coluna) {
        try {
            switch (coluna) {
                case 'id_empresa':
                    print "Digite o novo ID da empresa: "
                    return inputService.getIntInput()
                case 'nome':
                    print "Digite o novo título: "
                    return inputService.getNomeInput()
                case 'descricao':
                    print "Digite a nova descrição: "
                    return inputService.getDescricaoInput()
                case 'cidade':
                    print "Digite a nova cidade: "
                    return inputService.getNomeInput()
                case 'estado':
                    print "Digite o novo estado (UF): "
                    return inputService.getEstadoInput()
                default:
                    println "Campo não suportado."
                    return null
            }
        } catch (Exception ignored) {
            println "Limite de tentativas excedido ou input inválido."
            return null
        }
    }

    private void atualizarNomeCompetencia(List<Competencia> competencias) {
        imprimirItens(competencias) { Competencia competencia ->
            println "${competencia.id} - ${competencia.nome}"
        }

        int competenciaId = solicitarId(competencias.collect { it.id }, 'Digite o ID da competência [0 para cancelar]: ')
        if (competenciaId == 0) {
            return
        }

        try {
            print 'Digite o novo nome: '
            String novoNome = inputService.getNomeInput()
            updateController.atualizarCompetenciaNome(competenciaId, novoNome)
            println 'Competência atualizada com sucesso.'
        } catch (Exception e) {
            println "Erro ao atualizar competência: ${e.message}"
        }
    }

    private void gerenciarRelacionamento(String objeto) {
        List<String> opcoes = ['Adicionar relacionamento', 'Remover relacionamento']
        Integer opcao = selecionarOpcao(opcoes, 'Opções:')
        if (opcao == null) {
            return
        }

        try {
            println updateController.listarRelacionamentos(objeto)
        } catch (Exception e) {
            println "Erro ao listar relacionamentos: ${e.message}"
            return
        }

        int idCompetencia = solicitarValorInteiro('Digite o ID da competência [0 para cancelar]: ')
        if (idCompetencia == 0) {
            return
        }

        int idObjeto = solicitarValorInteiro("Digite o ID de ${objeto} [0 para cancelar]: ")
        if (idObjeto == 0) {
            return
        }

        try {
            if (opcao == 1) {
                updateController.adicionarRelacionamento(objeto, idCompetencia, idObjeto)
            } else {
                updateController.removerRelacionamento(objeto, idCompetencia, idObjeto)
            }
            println 'Relacionamento atualizado com sucesso.'
        } catch (Exception e) {
            println "Erro ao atualizar relacionamento: ${e.message}"
        }
    }

    private int solicitarValorInteiro(String prompt) {
        try {
            print prompt
            return inputService.getIntInput()
        } catch (Exception ignored) {
            println 'Operação cancelada ou input inválido.'
            return 0
        }
    }
}
