package com.augusto.linketinder.view.create

import com.augusto.linketinder.control.CadastroController
import com.augusto.linketinder.model.lista.EnumCompetencias
import com.augusto.linketinder.model.pessoa.Empresa

class CadastroEmpresaView {

    CadastroController cadastroController = new CadastroController()

    void show() {
        println "Cadastro da Pessoa Jurídica"
        println()

        Empresa pessoa = new Empresa()

        print("Digite o Nome da Empresa: ")
        pessoa.nome = cadastroController.getNomeInput()

        print("Digite o Email da Empresa: ")
        pessoa.email = cadastroController.getEmailInput()

        print("Digite o Estado da Empresa: ")
        pessoa.estado = cadastroController.getEstadoInput()

        print("Digite o CEP da Empresa: ")
        pessoa.cep = cadastroController.getCepInput()

        print("Digite a Descrição da Empresa: ")
        pessoa.descricao = cadastroController.getDescricaoInput()

        print("Digite o CNPJ da Empresa: ")
        pessoa.cnpj = cadastroController.getCnpjInput()

        print("Digite o País da Empresa: ")
        pessoa.pais = cadastroController.getPaisInput()

        println()
        println("Competências disponíveis:")
        EnumCompetencias.values().eachWithIndex { comp, idx ->
            println("${idx + 1} - ${comp}")
        }
        pessoa.competencias = cadastroController.getCompetenciasInput()

        // Adiciona pessoa ao final do processo
        cadastroController.insertPessoa(pessoa)

        println()
        println("Empresa adicionada com sucesso!")
    }
}
