package com.augusto.linketinder.view.create

import com.augusto.linketinder.DAO.DAO_Candidato
import com.augusto.linketinder.DAO.DAO_Competencia
import com.augusto.linketinder.service.InputService
import com.augusto.linketinder.model.Competencia
import com.augusto.linketinder.model.pessoa.Candidato

class CadastroCandidatoView {

    InputService inputService = new InputService()
    private final DAO_Candidato candidatoDao = new DAO_Candidato()
    private final DAO_Competencia competenciaDao = new DAO_Competencia()

    void show(){
        println "Cadastro do Candidato"
        println()

        Candidato pessoa = new Candidato()

        print("Digite o Nome do Candidato: ")
        pessoa.nome = inputService.getNomeInput()

        print("Digite o Email do Candidato: ")
        pessoa.email = inputService.getEmailInput()

        print("Digite o Estado do Candidato: ")
        pessoa.estado = inputService.getEstadoInput()

        print("Digite o CEP do Candidato: ")
        pessoa.cep = inputService.getCepInput()

        print("Digite a Descrição do Candidato: ")
        pessoa.descricao = inputService.getDescricaoInput()

        print("Digite o CPF do Candidato: ")
        pessoa.cpf = inputService.getCpfInput()

        print("Digite a idade do Candidato: ")
        pessoa.idade = inputService.getIdadeInput()

        println()
        println("Competências disponíveis:")
        int cont = 1
        List<Competencia> listaComp = competenciaDao.listAll()
        for(c in listaComp){
            println("${cont} " + c.nome)
            cont++
        }
        pessoa.competencias = inputService.getCompetenciasInput(listaComp)

        print("Digite a senha do Candidato: ")
        pessoa.senha = inputService.getSenhaInput()

        //Adiciona pessoa ao final do processo
        try {
            candidatoDao.insert(pessoa)
            println()
            println("Candidato adicionado com sucesso!")
        } catch (Exception e){
            println("Erro ao adicionar candidato!")
        }
    }
}
