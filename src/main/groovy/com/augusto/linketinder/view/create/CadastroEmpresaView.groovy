package com.augusto.linketinder.view.create

import com.augusto.linketinder.DAO.DAO
import com.augusto.linketinder.control.CadastroController
import com.augusto.linketinder.model.Competencia
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

        // Adiciona pessoa ao final do processo
        try {
            dao.insert(pessoa)
            println()
            println("Empresa adicionada com sucesso!")
        } catch (Exception err){
            println("Erro ao adicionar empresa!")
        }


    }
}
