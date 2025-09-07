package com.augusto.linketinder.model.pessoa

import groovy.transform.ToString

@ToString
class PessoaJuridica implements Pessoa{

    String cnpj
    String pais

    @Override
    String toString() {
        return """\
        PessoaJuridica {
            nome        = ${nome}
            email       = ${email}
            estado      = ${estado}
            cep         = ${cep}
            descricao   = ${descricao}
            cnpj        = ${cnpj}
            pais        = ${pais}
            competencias= ${competencias?.join(", ")}
        }""".stripIndent()
    }
}
