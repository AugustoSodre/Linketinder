package com.augusto.linketinder.model.pessoa

import com.augusto.linketinder.model.Competencia

trait Pessoa {
    String id
    String nome
    String email
    String estado
    String cep
    String descricao
    List<Competencia> competencias
    String senha

    //Implementar metodo 'curtir'
    def curtir(){}

}