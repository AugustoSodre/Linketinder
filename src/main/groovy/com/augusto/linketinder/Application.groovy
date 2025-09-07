package com.augusto.linketinder

import com.augusto.linketinder.model.Pessoa
import com.augusto.linketinder.model.PessoaFisica
import com.augusto.linketinder.model.PessoaJuridica

PessoaFisica p = new PessoaFisica(nome:"Jose")
PessoaJuridica pj = new PessoaJuridica(nome:"Arroz-Gostoso")

println p.toString()
println pj.toString()