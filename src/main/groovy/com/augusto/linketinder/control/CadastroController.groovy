package com.augusto.linketinder.control

import com.augusto.linketinder.model.lista.EnumCompetencias

class CadastroController {

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in))

    String getStringInput(){
        while (true){
            try{
                return br.readLine().trim()
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
            if (email ==~ /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/){
                return email
            } else{
                println "Email inválido! Tente novamente"
            }
        }
    }

    String getEstadoInput(){
        def estados = [
                "AC",
                "AL",
                "AP",
                "AM",
                "BA",
                "CE",
                "DF",
                "ES",
                "GO",
                "MA",
                "MT",
                "MS",
                "MG",
                "PA",
                "PB",
                "PR",
                "PE",
                "PI",
                "RJ",
                "RN",
                "RS",
                "RO",
                "RR",
                "SC",
                "SP",
                "SE",
                "TO"
        ]

        String estado

        while(true){
            estado = getStringInput().toUpperCase()

            if(estado in estados){
                break
            } else{
                println("Input inválido!")
            }

        }

        return estado
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
    List<EnumCompetencias> getCompetenciasInput() {
        List<EnumCompetencias> tempList = []

        while (true) {
            println()
            println("Competências selecionadas:")
            tempList.each {println("- " + it.toString())}
            println()
            print("Digite uma competência para adicionar [0 para sair]: ")

            int opcao = getIntInput()

            if (opcao == 0) break

            if (opcao > 0 && opcao <= EnumCompetencias.values().length) {
                EnumCompetencias comp = EnumCompetencias.values()[opcao - 1]
                if (!tempList.contains(comp)) {
                    tempList << comp
                } else {
                    println("Competência já foi selecionada.")
                }
            } else {
                println("Opção inválida, tente novamente.")
            }
        }

        return tempList
    }


    String getCpfInput(){
        String cpf

        while(true){
            cpf = getStringInput()
            if (cpf ==~ /^\d{3}(\.\d{3}){2}-\d{2}$|^\d{11}$/){
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
            if (cnpj ==~ /^\d{2}(\.\d{3}){2}\/\d{4}-\d{2}$|^\d{14}$/){
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

    int getIntInput(){
        while (true){
            try{
                return Integer.parseInt(br.readLine())
            } catch (Exception ignored){
                println("Input inválido")
            }
        }
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
