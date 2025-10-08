package com.augusto.linketinder.view.create

import com.augusto.linketinder.DAO.DAO
import com.augusto.linketinder.control.CadastroController
import com.augusto.linketinder.model.Competencia
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
        DAO dao = new DAO()
        List<Competencia> listaComp = dao.listCompetencia()
        for(c in listaComp){
            println("${cont} " + c.nome)
            cont++
        }
        pessoa.competencias = cadastroController.getCompetenciasInput(listaComp)

        print("Digite a senha do Candidato: ")
        pessoa.senha = cadastroController.getSenhaInput()

        //Adiciona pessoa ao final do processo
        dao.insert(pessoa)

        println("Candidato adicionada com sucesso!")
    }
}
