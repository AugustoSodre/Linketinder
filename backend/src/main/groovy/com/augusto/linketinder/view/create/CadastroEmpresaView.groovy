package com.augusto.linketinder.view.create

import com.augusto.linketinder.dao.DAO_Competencia
import com.augusto.linketinder.dao.DAO_Empresa
import com.augusto.linketinder.service.InputService
import com.augusto.linketinder.model.Competencia
import com.augusto.linketinder.model.pessoa.Empresa
import com.augusto.linketinder.view.helper.ViewHelper

class CadastroEmpresaView {

    private final InputService inputService
    private final DAO_Empresa empresaDao
    private final DAO_Competencia competenciaDao
    private final ViewHelper viewHelper

    CadastroEmpresaView() {
        this(new InputService(), new DAO_Empresa(), new DAO_Competencia(), new ViewHelper())
    }

    CadastroEmpresaView(InputService inputService,
                         DAO_Empresa empresaDao,
                         DAO_Competencia competenciaDao,
                         ViewHelper viewHelper) {
        this.inputService = inputService
        this.empresaDao = empresaDao
        this.competenciaDao = competenciaDao
        this.viewHelper = viewHelper
    }

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
    viewHelper.printAllAvailableCompetencias(listaComp)
        pessoa.competencias = inputService.getCompetenciasInput(listaComp)

        print("Digite a senha da Empresa: ")
        pessoa.senha = inputService.getSenhaInput()

        return pessoa
    }
}
