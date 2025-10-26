package com.augusto.linketinder.control

import com.augusto.linketinder.DAO.DAO_Candidato
import com.augusto.linketinder.DAO.DAO_Competencia
import com.augusto.linketinder.DAO.DAO_Empresa
import com.augusto.linketinder.DAO.DAO_Vaga
import com.augusto.linketinder.model.Competencia
import com.augusto.linketinder.model.Vaga
import com.augusto.linketinder.model.pessoa.Empresa
import com.augusto.linketinder.model.pessoa.Candidato

class DeleteController {

    private final DAO_Candidato candidatoDao = new DAO_Candidato()
    private final DAO_Empresa empresaDao = new DAO_Empresa()
    private final DAO_Vaga vagaDao = new DAO_Vaga()
    private final DAO_Competencia competenciaDao = new DAO_Competencia()

    void delete(def o){
        int id = getIdInput()

        try {
            if(o instanceof Candidato){
                candidatoDao.delete(id)

            } else if(o instanceof Empresa){
                empresaDao.delete(id)

            } else if(o instanceof Vaga){
                vagaDao.delete(id)

            } else if(o instanceof Competencia){
                competenciaDao.removeAllRelationsFromCompetencia(id)
                competenciaDao.delete(id)

            }

            println("Operação concluída.")
        } catch (Exception e) {
            println("Erro ao realizar exclusão: ${e.message}")
        }
    }

    int getIdInput(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in))
        String id = ""
        while(true){
            try{
                id = br.readLine()

                if(!id.isEmpty()){
                    break
                } else{
                    println("Input inválido!")
                }

            }catch (Exception ignored){
                println("Input inválido!")
            }
        }
        try{
            return Integer.parseInt(id)
        } catch (Exception err){
            println(err.stackTrace)
            return -1
        }

    }
}
