package com.augusto.linketinder.view.create

import com.augusto.linketinder.DAO.DAO_Competencia
import com.augusto.linketinder.DAO.DAO_Empresa
import com.augusto.linketinder.service.InputService
import com.augusto.linketinder.model.Competencia
import com.augusto.linketinder.model.pessoa.Empresa

class CadastroEmpresaView {

    InputService inputService = new InputService()
    private final DAO_Empresa empresaDao = new DAO_Empresa()
    private final DAO_Competencia competenciaDao = new DAO_Competencia()

    void show() {
        println "Cadastro da Pessoa Jurídica"
        println()

        Empresa empresa = createEmpresa()

        try {
            empresaDao.insert(empresa)
            println()
            println("Empresa adicionada com sucesso!")
        } catch (Exception err){
            println("Erro ao adicionar empresa! " + err.message)
        }

    }

    Empresa createEmpresa(){
        Empresa pessoa = new Empresa()

        print("Digite o Nome da Empresa: ")
        pessoa.nome = inputService.getNomeInput()

        print("Digite o Email da Empresa: ")
        pessoa.email = inputService.getEmailInput()

        print("Digite o Estado da Empresa: ")
        pessoa.estado = inputService.getEstadoInput()

        print("Digite o CEP da Empresa: ")
        pessoa.cep = inputService.getCepInput()

        print("Digite a Descrição da Empresa: ")
        pessoa.descricao = inputService.getDescricaoInput()

        print("Digite o CNPJ da Empresa: ")
        pessoa.cnpj = inputService.getCnpjInput()

        print("Digite o País da Empresa: ")
        pessoa.pais = inputService.getPaisInput()

        println()
        println("Competências disponíveis:")
        List<Competencia> listaComp = competenciaDao.listAll()
        printCompetencia(listaComp)
        pessoa.competencias = inputService.getCompetenciasInput(listaComp)

        print("Digite a senha da Empresa: ")
        pessoa.senha = inputService.getSenhaInput()

        return pessoa
    }

    void printCompetencia(List<Competencia> listaComp){
        int cont = 1
        for(c in listaComp){
            println("${cont} " + c.nome)
            cont++
        }
    }
}
