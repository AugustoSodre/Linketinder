package com.augusto.linketinder.control

import com.augusto.linketinder.DAO.DAO
import com.augusto.linketinder.model.Competencia
import com.augusto.linketinder.model.Vaga
import com.augusto.linketinder.model.lista.ListaCandidatoEstatica
import com.augusto.linketinder.model.lista.ListaEmpresaEstatica
import com.augusto.linketinder.model.pessoa.Empresa
import com.augusto.linketinder.model.pessoa.Pessoa
import com.augusto.linketinder.model.pessoa.Candidato

class DeleteController {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in))

    void delete(def o){
        DAO dao = new DAO()

        int id = getIdInput()

        if(o instanceof Candidato){
            dao.delete("candidato", id)

        } else if(o instanceof Empresa){
            dao.delete("empresa", id)

        } else if(o instanceof Vaga){
            dao.delete("vaga", id)

        } else if(o instanceof Competencia){
            dao.delete("competencia", id)

        }
    }

    int getIdInput(){
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
