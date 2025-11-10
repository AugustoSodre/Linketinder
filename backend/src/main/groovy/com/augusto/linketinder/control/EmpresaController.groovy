package com.augusto.linketinder.control

import com.augusto.linketinder.dao.CompetenciaDAO
import com.augusto.linketinder.dao.DaoFactory
import com.augusto.linketinder.model.Competencia
import com.augusto.linketinder.model.pessoa.Empresa
import com.augusto.linketinder.service.EmpresaService
import com.augusto.linketinder.service.InputService
import com.augusto.linketinder.view.helper.ViewHelper
import com.augusto.linketinder.view.EmpresaView

class EmpresaController {
    ViewHelper viewHelper
    CompetenciaDAO competenciaDAO
    EmpresaService empresaService
    EmpresaView empresaView
    InputService inputService

    EmpresaController(ViewHelper viewHelper,
                      CompetenciaDAO competenciaDAO,
                      EmpresaService empresaService,
                      EmpresaView empresaView,
                      InputService inputService){
        this.viewHelper = viewHelper
        this.competenciaDAO = competenciaDAO
        this.empresaService = empresaService
        this.empresaView = empresaView
        this.inputService = inputService
    }

    EmpresaController(){
        this(new ViewHelper(), DaoFactory.getCompetenciaDAO(), new EmpresaService(DaoFactory.getEmpresaDAO()), new EmpresaView(), new InputService())
    }

    void createEmpresa(){
        Empresa pessoa = new Empresa()

        println "Cadastro da Pessoa Jurídica"
        println()

        empresaView.printMessage("Digite o Nome da Empresa: ")
        pessoa.nome = inputService.getNomeInput()

        empresaView.printMessage("Digite o Email da Empresa: ")
        pessoa.email = inputService.getEmailInput()

        empresaView.printMessage("Digite o Estado da Empresa: ")
        pessoa.estado = inputService.getEstadoInput()

        empresaView.printMessage("Digite o CEP da Empresa: ")
        pessoa.cep = inputService.getCepInput()

        empresaView.printMessage("Digite a Descrição da Empresa: ")
        pessoa.descricao = inputService.getDescricaoInput()

        empresaView.printMessage("Digite o CNPJ da Empresa: ")
        pessoa.cnpj = inputService.getCnpjInput()

        empresaView.printMessage("Digite o País da Empresa: ")
        pessoa.pais = inputService.getPaisInput()

        println()
        empresaView.printlnMessage("Competências disponíveis:")
        List<Competencia> listaComp = competenciaDAO.listAll()
        viewHelper.printAllAvailableCompetencias(listaComp)
        pessoa.competencias = inputService.getCompetenciasInput(listaComp)

        empresaView.printMessage("Digite a senha da Empresa: ")
        pessoa.senha = inputService.getSenhaInput()

        empresaService.createEmpresa(pessoa)
    }

    void readEmpresa(){
        empresaView.showReadEmpresas(empresaService.getEmpresaList())
    }

    List listarEmpresas() {
        return empresaService.getEmpresaList()
    }

    Map<String, String> camposEmpresa() {
        return empresaService.getCamposEmpresa()
    }

    void updateEmpresa(){
        // keep interactive update in the view/controller pair if desired
        empresaView.printlnMessage('Atualizar Empresa via menu não implementada (use controller.updateEmpresa(id,col,valor))')
    }

    void atualizarEmpresaCampo(int id, String coluna, Object novoValor) {
        empresaService.updateEmpresa(id, coluna, novoValor)
    }

    void deleteEmpresa(){
        empresaView.showDeleteEmpresa()
        int id = inputService.getIntInput()
        empresaService.deleteEmpresa(id)
    }
}
 
