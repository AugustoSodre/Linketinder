package com.augusto.linketinder.service

import groovy.json.JsonSlurper

class ValidateService {

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
}
