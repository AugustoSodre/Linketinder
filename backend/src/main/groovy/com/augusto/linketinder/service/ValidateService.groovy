package com.augusto.linketinder.service

import com.augusto.linketinder.model.Competencia
import groovy.json.JsonSlurper

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

    private static final Map<String, Map> REGRAS = new JsonSlurper().parse(ValidateService.getResource('/regras.json')) as Map

    String erroDoCampo(String campo, Object valor) {
        Map regra = REGRAS[campo]
        if (!regra) {
            return null
        }
        boolean ok = campo == 'idade'
                ? valor instanceof Integer && valor >= regra.min && valor <= regra.max
                : valor instanceof String && valor =~ regra.regex
        return ok ? null : regra.mensagem
    }

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
