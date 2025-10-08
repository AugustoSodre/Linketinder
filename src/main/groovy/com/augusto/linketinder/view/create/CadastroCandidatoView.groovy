package com.augusto.linketinder.view.create

import com.augusto.linketinder.control.CadastroController
import com.augusto.linketinder.model.lista.EnumCompetencias
import com.augusto.linketinder.model.pessoa.Candidato

class CadastroCandidatoView {

    CadastroController cadastroController = new CadastroController()

    void show(){
        println "Cadastro do Candidato"
        println()

        Candidato pessoa = new Candidato()

        print("Digite o Nome do Candidato: ")
        pessoa.nome = cadastroController.getNomeInput()

        print("Digite o Email do Candidato: ")
        pessoa.email = cadastroController.getEmailInput()

        print("Digite o Estado do Candidato: ")
        pessoa.estado = cadastroController.getEstadoInput()

        print("Digite o CEP do Candidato: ")
        pessoa.cep = cadastroController.getCepInput()

        print("Digite a Descrição do Candidato: ")
        pessoa.descricao = cadastroController.getDescricaoInput()

        print("Digite o CPF do Candidato: ")
        pessoa.cpf = cadastroController.getCpfInput()

        print("Digite a idade do Candidato: ")
        pessoa.idade = cadastroController.getIdadeInput()

        println()
        println("Competências disponíveis:")
        int cont = 1
        for(c in EnumCompetencias.values()){
            println("${cont} " + c)
            cont++
        }
        pessoa.competencias = cadastroController.getCompetenciasInput()

        //Adiciona pessoa ao final do processo
        cadastroController.insertPessoa(pessoa)

        println("Candidato adicionada com sucesso!")
    }
}
