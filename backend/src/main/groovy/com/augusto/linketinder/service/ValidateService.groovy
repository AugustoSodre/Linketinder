package com.augusto.linketinder.service

import com.augusto.linketinder.model.Competencia

class ValidateService {

    boolean isEmailValid(String email) {
        if (!email) {
            return false
        }

        return email ==~ /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/
    }

    boolean isEstadoValid(String estado) {
        def estados = [
                "AC",
                "AL",
                "AP",
                "AM",
                "BA",
                "CE",
                "DF",
                "ES",
                "GO",
                "MA",
                "MT",
                "MS",
                "MG",
                "PA",
                "PB",
                "PR",
                "PE",
                "PI",
                "RJ",
                "RN",
                "RS",
                "RO",
                "RR",
                "SC",
                "SP",
                "SE",
                "TO"
        ]

        if (!estado) {
            return false
        }

        return estado in estados
    }

    boolean isCEPValid(String cep) {
        if (!cep) {
            return false
        }

        return cep ==~ /^\d{5}-?\d{3}$/
    }

    boolean isListCompetenciaValid(List<Competencia> competenciaList) {
        if (competenciaList == null) {
            return false
        }

        return competenciaList.every { it != null }
    }

    boolean isCompetenciaOpcaoValida(int opcao, int totalCompetencias) {
        return opcao > 0 && opcao <= totalCompetencias
    }

    boolean isCompetenciaSelecionavel(List<Competencia> selecionadas, Competencia competencia) {
        if (selecionadas == null || competencia == null) {
            return false
        }

        return !selecionadas.contains(competencia)
    }

    boolean isCPFValid(String cpf) {
        if (!cpf) {
            return false
        }
        return cpf ==~ /^\d{3}(\.?\d{3}){2}-?\d{2}$|^\d{11}$/
    }

    boolean isCNPJValid(String cnpj) {
        if (!cnpj) {
            return false
        }

        return cnpj ==~ /^\d{2}(\.?\d{3}){2}\/?\d{4}-?\d{2}$|^\d{14}$/
    }

    boolean isPaisValid(String pais) {
        if (!pais) {
            return false
        }
        return pais.trim().length() >= 2 && pais ==~ /^[a-zA-Z]+$/
    }

    boolean isIdadeValid(int idade) {
        return idade >= 0 && idade <= 120
    }

    boolean isNomeValid(String nome) {
        if (!nome) {
            return false
        }
        return nome.trim().length() >= 1
    }

    boolean isDescricaoValid(String descricao) {
        if (!descricao) {
            return false
        }
        return descricao.trim().length() >= 1
    }

    // Regras usadas pela API REST; os formatos batem com as colunas do banco (cpf 11, cnpj 14, cep 8 dígitos, siglas de 2 letras)
    // Retorna a mensagem de erro do campo, ou null se estiver ok (ou se o campo não tiver regra)
    String erroDoCampo(String campo, Object valor) {
        String texto = valor instanceof String ? valor : null
        switch (campo) {
            case 'nome': return isNomeValid(texto) ? null : 'Nome é obrigatório'
            case 'email': return isEmailValid(texto) ? null : 'Email inválido'
            case 'estado': return isEstadoValid(texto) ? null : 'Estado inválido (use a sigla, ex: SP)'
            case 'cep': return (texto ?: '') ==~ /^\d{8}$/ ? null : 'CEP deve ter 8 dígitos'
            case 'cpf': return (texto ?: '') ==~ /^\d{11}$/ ? null : 'CPF deve ter 11 dígitos'
            case 'cnpj': return (texto ?: '') ==~ /^\d{14}$/ ? null : 'CNPJ deve ter 14 dígitos'
            case 'pais': return (texto ?: '') ==~ /^[A-Z]{2}$/ ? null : 'País inválido (use a sigla, ex: BR)'
            case 'idade': return valor instanceof Integer && isIdadeValid(valor as int) ? null : 'Idade deve estar entre 0 e 120'
            case 'descricao': return isDescricaoValid(texto) ? null : 'Descrição é obrigatória'
            case 'senha': return texto?.length() >= 6 ? null : 'A senha deve ter pelo menos 6 caracteres'
            default: return null
        }
    }

    // Usado pela API: valida os obrigatórios e qualquer outro campo que veio preenchido; retorna as mensagens de erro
    List<String> validarCampos(Map data, List<String> obrigatorios) {
        def campos = (obrigatorios + data.keySet().findAll { data[it] != null }).unique()
        return campos.collect { erroDoCampo(it as String, data[it]) }.findAll { it != null }
    }

    boolean isSenhaValid(String senha) {
        if (!senha) {
            return false
        }
        return senha.trim().length() >= 1

    }
}
