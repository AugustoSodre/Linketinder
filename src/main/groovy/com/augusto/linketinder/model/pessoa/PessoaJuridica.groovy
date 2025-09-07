package com.augusto.linketinder.model.pessoa

import groovy.transform.ToString

@ToString
class PessoaJuridica implements Pessoa{

    String pais
    String cnpj

    PessoaJuridica(){
        setId(hashCode().toString())
    }

    @Override
    String toString() {
        return """\
        PessoaJuridica {
            id          = ${id}
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
