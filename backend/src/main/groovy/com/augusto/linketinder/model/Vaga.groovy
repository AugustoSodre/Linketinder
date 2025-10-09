package com.augusto.linketinder.model

class Vaga {
    int id
    int id_empresa
    String nome
    String descricao
    String cidade
    String estado
    List<Competencia> competencias

    Vaga(){}

    Vaga(int id, int id_empresa, String nome, String descricao, String cidade, String estado, List<Competencia> listaComp) {
        this.id = id
        this.id_empresa = id_empresa
        this.nome = nome
        this.descricao = descricao
        this.cidade = cidade
        this.estado = estado
        this.competencias = listaComp
    }

    Vaga(int id_empresa, String nome, String descricao, String cidade, String estado, List<Competencia> competencias) {
        this.id_empresa = id_empresa
        this.nome = nome
        this.descricao = descricao
        this.cidade = cidade
        this.estado = estado
        this.competencias = competencias
    }

    @Override
    String toString() {
        def competenciasStr = competencias?.collect {
            "                    ${it.toString().stripIndent()}"
        }?.join(",\n") ?: "                    Nenhuma"

        return """\
                Vaga {
                    id           = ${id}
                    id_empresa   = ${id_empresa}
                    nome         = ${nome}
                    descricao    = ${descricao}
                    cidade       = ${cidade}
                    estado       = ${estado}
                    competencias = [
${competenciasStr}
                    ]
                }""".stripIndent()
    }


}
