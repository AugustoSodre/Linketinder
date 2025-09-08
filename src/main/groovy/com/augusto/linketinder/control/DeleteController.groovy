package com.augusto.linketinder.control

import com.augusto.linketinder.model.lista.ListaFisicaEstatica
import com.augusto.linketinder.model.lista.ListaJuridicaEstatica
import com.augusto.linketinder.model.pessoa.Pessoa
import com.augusto.linketinder.model.pessoa.PessoaFisica

class DeleteController {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in))

    void delete(Pessoa tipo){
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

        //Percorrendo a lista me busca da pessoa com ID
        if(tipo.getClass() == PessoaFisica){
            int index = -1

            ListaFisicaEstatica.getLista().eachWithIndex { it, i ->
                if(it.getId().equals(id) ){
                    index = i
                }
            }

            try{
                ListaFisicaEstatica.getLista().remove(index)
                println("Pessoa removida com sucesso!")
                return
            } catch (Exception ignored){
                println("Algo deu errado!")
            }


        } else{
            int index = -1

            ListaJuridicaEstatica.getLista().eachWithIndex { it, i ->
                if(it.getId().equals(id)){
                    index = i
                }
            }

            try{
                ListaJuridicaEstatica.getLista().remove(index)
                println("Pessoa removida com sucesso!")
                return
            } catch (Exception ignored){
                println("Algo deu errado!")
            }

        }

        println("ID não encontrado! Operação falhou!")
    }
}
