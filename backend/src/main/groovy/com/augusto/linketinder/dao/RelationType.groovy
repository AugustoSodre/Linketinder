package com.augusto.linketinder.dao

enum RelationType {
    CANDIDATO("competencia_candidato", "id_candidato"),
    EMPRESA("competencia_empresa", "id_empresa"),
    VAGA("competencia_vaga", "id_vaga")

    final String table
    final String column

    RelationType(String table, String column) {
        this.table = table
        this.column = column
    }
}
