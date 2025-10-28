package com.augusto.linketinder.view.create

import com.augusto.linketinder.DAO.DAO_Competencia
import com.augusto.linketinder.DAO.DAO_Vaga
import com.augusto.linketinder.service.InputService
import com.augusto.linketinder.model.Competencia
import com.augusto.linketinder.model.Vaga

class CadastroVagaView {

    InputService inputService = new InputService()
    private final DAO_Vaga vagaDao = new DAO_Vaga()
    private final DAO_Competencia competenciaDao = new DAO_Competencia()

    void show() {
        println "Cadastro da Vaga"
        println()

        Vaga vaga = createVaga()

        try {
            vagaDao.insert(vaga)
            println()
            println("Vaga adicionada com sucesso!")
        } catch (Exception err){
            println("Erro ao adicionar vaga! " + err.message)
        }

    }

    Vaga createVaga(){
        Vaga vaga = new Vaga()

        print("Digite o ID da Empresa: ")
        vaga.id_empresa = inputService.getIntInput()

        print("Digite o Título da Vaga: ")
        vaga.nome = inputService.getNomeInput()

        print("Digite a Descrição da Vaga: ")
        vaga.descricao = inputService.getDescricaoInput()

        print("Digite a Cidade da Vaga: ")
        vaga.cidade = inputService.getNomeInput()

        print("Digite o Estado da Vaga: ")
        vaga.estado = inputService.getEstadoInput()

        println()
        println("Competências disponíveis:")

        List<Competencia> listaComp = competenciaDao.listAll()
        printCompetencia(listaComp)
        vaga.competencias = inputService.getCompetenciasInput(listaComp)

        return vaga
    }

    void printCompetencia(List<Competencia> listaComp){
        int cont = 1
        for(c in listaComp){
            println("${cont} " + c.nome)
            cont++
        }
    }
}
