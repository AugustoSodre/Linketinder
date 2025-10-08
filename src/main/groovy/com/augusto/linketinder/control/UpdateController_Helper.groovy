package com.augusto.linketinder.control

import com.augusto.linketinder.DAO.DAO

class UpdateController_Helper {

    DAO dao = new DAO()
    CadastroController cadastroController = new CadastroController()


    void hadnleCompObjeto(String objeto) {
        int opcao = -1

        while (opcao != 0 && opcao != 1 && opcao != 2) {
            limpaTela()
            println("Gerência Competência-${objeto}")
            println("Opções:\n" +
                    "1. Adicionar Relacionamento\n" +
                    "2. Remover Relacionamento")
            print "Digite o número da opção [0 para cancelar]: "
            try {
                opcao = cadastroController.getIntInput()
            } catch (Exception e) {
                println "Operação cancelada ou input inválido."
                return
            }
        }

        if(opcao == 0){return}

        println(dao.listCompObject(objeto))
        int id_competencia = -1
        int id_objeto = -1
        try {
            print "Digite o ID da competência: "
            id_competencia = cadastroController.getIntInput()
            print "Digite o ID de ${objeto}: "
            id_objeto = cadastroController.getIntInput()
        }catch (Exception e) {
            println(e.stackTrace)
        }

        if (opcao == 1) {
            try{
                dao.insertCompObject(objeto, id_competencia, id_objeto)
            } catch (Exception e) {
                println(e.stackTrace)
            }

        } else if (opcao == 2) {
            try{
                dao.deleteCompObjeto(objeto, id_competencia, id_objeto)
            } catch (Exception e) {
                println(e.stackTrace)
            }

        }
    }


    void limpaTela(){
        for (i in 0..<50) {
            println()
        }
    }
}
