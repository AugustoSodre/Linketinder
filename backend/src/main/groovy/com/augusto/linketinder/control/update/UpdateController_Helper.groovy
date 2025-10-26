package com.augusto.linketinder.control.update

import com.augusto.linketinder.DAO.DAO_Competencia
import com.augusto.linketinder.control.CadastroController

import java.sql.SQLException

class UpdateController_Helper {

    private final DAO_Competencia competenciaDao
    private final CadastroController cadastroController

    UpdateController_Helper() {
        this(new DAO_Competencia(), new CadastroController())
    }

    UpdateController_Helper(CadastroController cadastroController) {
        this(new DAO_Competencia(), cadastroController)
    }

    UpdateController_Helper(DAO_Competencia competenciaDao, CadastroController cadastroController) {
        this.competenciaDao = competenciaDao
        this.cadastroController = cadastroController
    }

    void handleCompObjeto(String objeto) {
        int opcao = getOpcao(objeto)
        if(opcao == 0){return}

        showRelacionamentos(objeto)

        int id_competencia = getIdCompetencia()
        int id_objeto = getIdObjeto(objeto)

        if (opcao == 1) {
            addRelacionamento(objeto, id_competencia, id_objeto)

        } else if (opcao == 2) {
            removeRelacionamento(objeto, id_competencia, id_objeto)
        }
    }

    void limpaTela(){
        for (int i = 0; i < 50; i++) {
            println()
        }
    }

    void printMenuCompObjeto(String objeto){
        limpaTela()
        println("Gerência Competência-${objeto}")
        println("Opções:\n" +
                "1. Adicionar Relacionamento\n" +
                "2. Remover Relacionamento")
        print "Digite o número da opção [0 para cancelar]: "
    }

    int getOpcao(String objeto){
        int opcao = -1

        while (opcao != 0 && opcao != 1 && opcao != 2) {
            printMenuCompObjeto(objeto)
            try {
                opcao = cadastroController.getIntInput()
            } catch (Exception e) {
                throw new ArithmeticException("Operação cancelada ou input inválido. {$e.message}")
            }
        }

        return opcao
    }

    void showRelacionamentos(String objeto){
        try {
            println(competenciaDao.listRelations(objeto))
        } catch (Exception e) {
            throw new SQLException("Erro ao listar relacionamentos: ${e.message}")
        }
    }

    int getIdCompetencia(){
        try {
            print "Digite o ID da competência: "
            return cadastroController.getIntInput()
        }catch (Exception e) {
            throw new ArithmeticException("Erro:" + e.stackTrace)
        }
    }

    int getIdObjeto(String objeto){
        try {
            print "Digite o ID de ${objeto}: "
            return cadastroController.getIntInput()
        }catch (Exception e) {
            throw new ArithmeticException("Erro:" + e.stackTrace)
        }
    }

    void addRelacionamento(String objeto, int id_competencia, int id_objeto){
        try{
            competenciaDao.addRelation(objeto, id_competencia, id_objeto)
        } catch (Exception e) {
            throw new SQLException("Erro: " + e.message)
        }
    }

    void removeRelacionamento(String objeto, int id_competencia, int id_objeto){
        try{
            competenciaDao.removeRelation(objeto, id_competencia, id_objeto)
        } catch (Exception e) {
            throw new SQLException("Erro: " + e.message)
        }
    }
}
