package com.augusto.linketinder.model

class Vaga {
    int id
    int id_empresa
    String titulo
    String descricao
    String cidade
    String estado
    List<Competencia> listaCompetencias

    Vaga(int id, int id_empresa, String titulo, String descricao, String cidade, String estado, List<Competencia> listaComp){
        this.id = id
        this.id_empresa = id_empresa
        this.titulo = titulo
        this.descricao = descricao
        this.cidade = cidade
        this.estado = estado
        this.listaCompetencias = listaComp
    }

    Vaga(int id_empresa, String titulo, String descricao, String cidade, String estado){
        this.id_empresa = id_empresa
        this.titulo = titulo
        this.descricao = descricao
        this.cidade = cidade
        this.estado = estado
    }

    @Override
    String toString(){
        return """\
        Vaga {
            id           = ${id}
            titulo       = ${titulo}
            descricao    = ${descricao}
            cidade       = ${cidade}
            estado       = ${estado}
            competencias = ${listaCompetencias?.join(", \n")}
        }""".stripIndent()
    }
}
