package com.augusto.linketinder.dao

enum RelationType {
    CANDIDATO("candidato", "competencia_candidato", "id_candidato"),
    EMPRESA("empresa", "competencia_empresa", "id_empresa"),
    VAGA("vaga", "competencia_vaga", "id_vaga")

    final String objectName
    final String table
    final String column

    RelationType(String objectName, String table, String column) {
        this.objectName = objectName
        this.table = table
        this.column = column
    }

    static RelationType fromObjectName(String objeto) {
        if (objeto == null) return null
        switch (objeto.toLowerCase()) {
            case 'candidato': return CANDIDATO
            case 'empresa': return EMPRESA
            case 'vaga': return VAGA
            default: return null
        }
    }
}
