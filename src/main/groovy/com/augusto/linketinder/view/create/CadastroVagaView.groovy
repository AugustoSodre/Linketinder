package com.augusto.linketinder.view.create

import com.augusto.linketinder.DAO.DAO
import com.augusto.linketinder.control.CadastroController
import com.augusto.linketinder.model.Competencia
import com.augusto.linketinder.model.Vaga
import com.augusto.linketinder.model.pessoa.Empresa

class CadastroVagaView {

    CadastroController cadastroController = new CadastroController()

    void show() {
        println "Cadastro da Vaga"
        println()

        Vaga vaga = new Vaga()

        print("Digite o ID da Empresa: ")
        vaga.id_empresa = cadastroController.getIntInput()

        print("Digite o Título da Vaga: ")
        vaga.titulo = cadastroController.getNomeInput()

        print("Digite a Descrição da Vaga: ")
        vaga.descricao = cadastroController.getDescricaoInput()

        print("Digite a Cidade da Vaga: ")
        vaga.cidade = cadastroController.getNomeInput()

        print("Digite o Estado da Vaga: ")
        vaga.estado = cadastroController.getEstadoInput()

        println()
        println("Competências disponíveis:")
        int cont = 1
        DAO dao = new DAO()
        List<Competencia> listaComp = dao.listCompetencia()
        for(c in listaComp){
            println("${cont} " + c.nome)
            cont++
        }
        vaga.competencias = cadastroController.getCompetenciasInput(listaComp)

        // Adiciona pessoa ao final do processo
        try {
            dao.insert(vaga)
            println()
            println("Vaga adicionada com sucesso!")
        } catch (Exception err){
            println("Erro ao adicionar vaga!")
        }


    }
}
