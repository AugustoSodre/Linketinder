package com.augusto.linketinder.model.lista

enum EnumCompetencias {
    ANGULAR("Angular"),
    JAVA("Java"),
    PYTHON("Python"),
    SPRING("Spring Framework"),
    SPOCK("Spock Framework"),
    JAVASCRIPT("JavaScript"),
    TYPESCRIPT("TypeScript"),
    REACT("React")

    final String competencia

    //Construtor mapeando
    EnumCompetencias(String competencia){
        this.competencia = competencia
    }

    String toString(){
        return competencia
    }

}