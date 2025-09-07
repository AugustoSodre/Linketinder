package com.augusto.linketinder.model.pessoa

import com.augusto.linketinder.model.lista.EnumCompetencias

trait Pessoa {
    String id
    String nome
    String email
    String estado
    String cep
    String descricao
    List<EnumCompetencias> competencias

    //Implementar metodo 'curtir'
    def curtir(){}

}