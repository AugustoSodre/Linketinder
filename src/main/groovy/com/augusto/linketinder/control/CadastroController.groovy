package com.augusto.linketinder.control

import com.augusto.linketinder.model.lista.EnumCompetencias

class CadastroController {

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in))

    String getStringInput(){
        while (true){
            try{
                return br.readLine()
            } catch (Exception ignored){
                println("Input inválido")
            }
        }
    }

    //Nome nao checado
    String getNomeInput(){
        return getStringInput()
    }

    String getEmailInput(){
        String email

        while(true){
            email = getStringInput()
            if (email ==~ /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/){
                return email
            } else{
                println "Email inválido! Tente novamente"
            }
        }
    }

    //Estados nao checados
    String getEstadoInput(){
        return getStringInput()
    }

    String getCepInput(){
        String cep

        while(true){
            cep = getStringInput()
            if (cep ==~ /^\d{5}-?\d{3}$/){
                return cep
            } else{
                println "CEP inválido! Tente novamente"
            }
        }
    }

    //Descricao nao checada
    String getDescricaoInput(){
        return getStringInput()
    }

    //TODO fazer getCompetencias
    List<EnumCompetencias> getCompetenciasInput(){
        while (true){
            return null
        }
    }

    String getCpfInput(){
        String cpf

        while(true){
            cpf = getStringInput()
            if (cpf ==~ /^\d{3}\.?\d{3}\.?\d{3}-?\d{2}$/){
                return cpf
            } else{
                println "CPF inválido! Tente novamente"
            }
        }
    }

    String getCnpjInput(){
        String cnpj

        while(true){
            cnpj = getStringInput()
            if (cnpj ==~ /^\d{2}\.?\d{3}\.?\d{3}\/?\d{4}-?\d{2}$/){
                return cnpj
            } else{
                println "CNPJ inválido! Tente novamente"
            }
        }
    }

    //Pais nao checado
    String getPaisInput(){
        return getStringInput()
    }

    int getIdadeInput(){
        while (true){
            try{
                return Integer.parseInt(br.readLine())
            } catch (Exception ignored){
                println("Input inválido")
            }
        }
    }

}
