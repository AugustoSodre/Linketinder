package com.augusto.linketinder.service

import com.augusto.linketinder.model.Competencia

class ValidateService {

    boolean isEmailValid(String email){
        if (email ==~ /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/){
            return true
        }

        println "Email inválido! Tente novamente"
        return false
    }

    boolean isEstadoValid(String estado){

    }

    boolean isCEPValid(String cep){

    }

    boolean isListCompetenciaValid(List<Competencia> competenciaList){

    }

    boolean isCPFValid(String cpf){

    }

    boolean isCNPJValid(String cnpj){

    }

    boolean isPaisValid(String pais){

    }

    boolean isIdadeValid(int idade){

    }
}
