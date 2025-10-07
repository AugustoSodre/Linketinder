package com.augusto.linketinder.model.pessoa

import com.augusto.linketinder.model.Competencia
import com.augusto.linketinder.model.Vaga
import groovy.transform.ToString

@ToString
class Empresa implements Pessoa{

    String pais
    String cnpj
    List<Vaga> listaVaga

    Empresa(int id, String nome, String email, String estado, String cep, String pais, String cnpj,String descricao, List<Competencia> competencias, List<Vaga> listaVaga) {
        this.id = id
        this.nome = nome
        this.email = email
        this.estado = estado
        this.cep = cep
        this.pais = pais
        this.cnpj = cnpj
        this.descricao = descricao
        this.competencias = competencias
        this.listaVaga = listaVaga
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
            competencias= ${competencias?.join(", \n")}
            vagas       = ${listaVaga?.join(", ")}
        }""".stripIndent()
    }
}
