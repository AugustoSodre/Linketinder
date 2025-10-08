package com.augusto.linketinder.model

class Vaga {
    int id
    int id_empresa
    String titulo
    String descricao
    String cidade
    String estado
    List<Competencia> competencias

    Vaga(){}

    Vaga(int id, int id_empresa, String titulo, String descricao, String cidade, String estado, List<Competencia> listaComp) {
        this.id = id
        this.id_empresa = id_empresa
        this.titulo = titulo
        this.descricao = descricao
        this.cidade = cidade
        this.estado = estado
        this.competencias = listaComp
    }

    Vaga(int id_empresa, String titulo, String descricao, String cidade, String estado, List<Competencia> competencias) {
        this.id_empresa = id_empresa
        this.titulo = titulo
        this.descricao = descricao
        this.cidade = cidade
        this.estado = estado
        this.competencias = competencias
    }

    @Override
    String toString() {
        return """\
        Vaga {
            id           = ${id}
            id_empresa   = ${id_empresa}
            titulo       = ${titulo}
            descricao    = ${descricao}
            cidade       = ${cidade}
            estado       = ${estado}
            competencias = ${competencias?.join(", \n")}
        }""".stripIndent()
    }
}
