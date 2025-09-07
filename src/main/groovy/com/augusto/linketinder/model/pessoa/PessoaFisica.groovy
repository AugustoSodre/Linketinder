package com.augusto.linketinder.model.pessoa


class PessoaFisica implements Pessoa{

    String cpf
    int idade

    @Override
    String toString() {
        return """\
        PessoaFisica {
            nome        = ${nome}
            email       = ${email}
            estado      = ${estado}
            cep         = ${cep}
            descricao   = ${descricao}
            cpf         = ${cpf}
            idade       = ${idade}
            competencias= ${competencias?.join(", ")}
        }""".stripIndent()
    }

}
