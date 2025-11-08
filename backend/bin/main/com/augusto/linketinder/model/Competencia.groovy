package com.augusto.linketinder.model

import groovy.transform.ToString

class Competencia {
    int id
    String nome

    Competencia(){}

    Competencia(int id, String nome){
        this.id = id
        this.nome = nome
    }

    Competencia(String nome){
        this.nome = nome
    }

    @Override
    String toString() {
        return """\
            Competencia {
                id    = ${id}
                nome  = ${nome}
            }""".stripIndent()
    }

}
