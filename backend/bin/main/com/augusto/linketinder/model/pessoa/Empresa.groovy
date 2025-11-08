package com.augusto.linketinder.model.pessoa

import com.augusto.linketinder.model.Competencia
import com.augusto.linketinder.model.Vaga
import groovy.transform.ToString

@ToString
class Empresa implements Pessoa{

    String pais
    String cnpj
    List<Vaga> listaVaga

    Empresa(){}

    Empresa(int id, String nome, String email, String estado, String cep, String pais, String cnpj,String descricao, List<Competencia> competencias, List<Vaga> listaVaga, String senha) {
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
        this.senha = senha
    }

    Empresa(String nome, String email, String estado, String cep, String pais, String cnpj,String descricao, List<Competencia> competencias, String senha) {
        this.nome = nome
        this.email = email
        this.estado = estado
        this.cep = cep
        this.pais = pais
        this.cnpj = cnpj
        this.descricao = descricao
        this.competencias = competencias
        this.senha = senha
    }

    @Override
    String toString() {
        def competenciasStr = competencias?.collect {
            "                ${it.toString().stripIndent()}"
        }?.join(",\n") ?: "                Nenhuma"

        def vagasStr = listaVaga?.collect {
            "                ${it.toString().stripIndent()}"
        }?.join(",\n") ?: "                Nenhuma"

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
            competencias= [
${competenciasStr}
            ]
            vagas       = [
${vagasStr}
            ]
        }""".stripIndent()
    }



}
