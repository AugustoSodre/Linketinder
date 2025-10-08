package com.augusto.linketinder.model.pessoa

import com.augusto.linketinder.model.Competencia


class Candidato implements Pessoa {

    String cpf
    int idade

    Candidato(int id,
              String nome,
              String email,
              String estado,
              String cep,
              String cpf,
              int idade,
              String descricao,
              List<Competencia> competencias,
              String senha) {
        this.id = id
        this.nome = nome
        this.email = email
        this.estado = estado
        this.cep = cep
        this.cpf = cpf
        this.idade = idade
        this.descricao = descricao
        this.competencias = competencias
        this.senha = senha
    }

    Candidato(
            int id,
            String nome,
            String email,
            String estado,
            String cep,
            String cpf,
            int idade,
            String descricao,
            List<Competencia> competencias
    ) {
        this.id = id
        this.nome = nome
        this.email = email
        this.estado = estado
        this.cep = cep
        this.cpf = cpf
        this.idade = idade
        this.descricao = descricao
        this.competencias = competencias
    }

    @Override
    String toString() {
        return """\
        PessoaFisica {
            id          = ${id}
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
