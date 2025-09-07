package com.augusto.linketinder.model.pessoa

import groovy.transform.ToString

@ToString
class PessoaJuridica implements Pessoa{

    String cnpj
    String pais
}
