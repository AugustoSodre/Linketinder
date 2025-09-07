package com.augusto.linketinder.model

trait Pessoa {
    String nome
    String email
    String estado
    String cep
    String descricao
    List<String> competencias

    //Implementar metodo 'curtir'
    def curtir(){}


}