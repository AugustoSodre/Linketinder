package com.augusto.linketinder.service

import com.augusto.linketinder.model.Competencia

class ValidateService {

    boolean isEmailValid(String email) {
        if (email ==~ /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/) {
            return true
        }

        println "Email inválido! Tente novamente"
        return false
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

        if (estado in estados) {
            return true
        }

        return false
    }

    boolean isCEPValid(String cep) {
        if (cep ==~ /^\d{5}-?\d{3}$/) {
            return true
        }

        return false
    }

    boolean isListCompetenciaValid(List<Competencia> competenciaList) {

    }

    boolean isCPFValid(String cpf) {

    }

    boolean isCNPJValid(String cnpj) {

    }

    boolean isPaisValid(String pais) {

    }

    boolean isIdadeValid(int idade) {

    }
}
