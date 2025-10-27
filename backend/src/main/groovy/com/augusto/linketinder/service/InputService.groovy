package com.augusto.linketinder.service

import com.augusto.linketinder.model.Competencia

class InputService {

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in))
    private static final int MAX_TENTATIVAS_PADRAO = 5

    String getStringInput(int maxTentativas = MAX_TENTATIVAS_PADRAO){
        int tentativas = 0
        while (tentativas < maxTentativas){
            try{
                String input = br.readLine()
                if (input != null && !input.isEmpty()) {
                    return input.trim()
                } else {
                    println("Input inválido")
                    tentativas++
                }
            } catch (Exception ignored){
                println("Input inválido")
                tentativas++
            }
        }
        throw new RuntimeException("Máximo de tentativas excedido para input de string")
    }

    //Nome nao checado
    String getNomeInput(int maxTentativas = MAX_TENTATIVAS_PADRAO){
        return getStringInput(maxTentativas)
    }

    String getEmailInput(int maxTentativas = MAX_TENTATIVAS_PADRAO){
        String email
        int tentativas = 0

        while(tentativas < maxTentativas){
            email = getStringInput()
            if (email ==~ /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/){
                return email
            } else{
                println "Email inválido! Tente novamente"
                tentativas++
            }
        }
        throw new RuntimeException("Máximo de tentativas excedido para email")
    }

    String getEstadoInput(int maxTentativas = MAX_TENTATIVAS_PADRAO){
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
        int tentativas = 0

        while(tentativas < maxTentativas){
            estado = getStringInput().toUpperCase()

            if(estado in estados){
                return estado
            } else{
                println("Input inválido!")
                tentativas++
            }
        }

        throw new RuntimeException("Máximo de tentativas excedido para estado")
    }

    String getCepInput(int maxTentativas = MAX_TENTATIVAS_PADRAO){
        String cep
        int tentativas = 0

        while(tentativas < maxTentativas){
            cep = getStringInput()
            if (cep ==~ /^\d{5}-?\d{3}$/){
                return cep
            } else{
                println "CEP inválido! Tente novamente"
                tentativas++
            }
        }
        throw new RuntimeException("Máximo de tentativas excedido para CEP")
    }

    //Descricao nao checada
    String getDescricaoInput(int maxTentativas = MAX_TENTATIVAS_PADRAO){
        return getStringInput(maxTentativas)
    }

    List<Competencia> getCompetenciasInput(List<Competencia> listaComp, int maxTentativas = MAX_TENTATIVAS_PADRAO) {
        List<Competencia> tempList = []
        int tentativasInvalidas = 0

        while (tentativasInvalidas < maxTentativas) {
            println()
            println("Competências selecionadas:")
            tempList.each {println("- " + it.nome)}
            println()
            print("Digite uma competência para adicionar [0 para sair]: ")

            int opcao
            try {
                opcao = getIntInput(maxTentativas - tentativasInvalidas)
            } catch (RuntimeException e) {
                throw new RuntimeException("Máximo de tentativas excedido para competências")
            }

            if (opcao == 0) break

            if (opcao > 0 && opcao <= listaComp.size()) {
                Competencia comp = listaComp[opcao - 1]
                if (!tempList.contains(comp)) {
                    tempList.add(comp)
                    tentativasInvalidas = 0 // Reset contador após sucesso
                } else {
                    println("Competência já foi selecionada.")
                    tentativasInvalidas++
                }
            } else {
                println("Opção inválida, tente novamente.")
                tentativasInvalidas++
            }
        }

        if (tentativasInvalidas >= maxTentativas) {
            throw new RuntimeException("Máximo de tentativas excedido para competências")
        }

        return tempList
    }

    String getCpfInput(int maxTentativas = MAX_TENTATIVAS_PADRAO){
        String cpf
        int tentativas = 0

        while(tentativas < maxTentativas){
            cpf = getStringInput()
            if (cpf ==~ /^\d{3}(\.\d{3}){2}-\d{2}$|^\d{11}$/){
                return cpf
            } else{
                println "CPF inválido! Tente novamente"
                tentativas++
            }
        }
        throw new RuntimeException("Máximo de tentativas excedido para CPF")
    }

    String getCnpjInput(int maxTentativas = MAX_TENTATIVAS_PADRAO){
        String cnpj
        int tentativas = 0

        while(tentativas < maxTentativas){
            cnpj = getStringInput()
            if (cnpj ==~ /^\d{2}(\.\d{3}){2}\/\d{4}-\d{2}$|^\d{14}$/){
                return cnpj
            } else{
                println "CNPJ inválido! Tente novamente"
                tentativas++
            }
        }
        throw new RuntimeException("Máximo de tentativas excedido para CNPJ")
    }

    //Pais nao checado
    String getPaisInput(int maxTentativas = MAX_TENTATIVAS_PADRAO){
        return getStringInput(maxTentativas)
    }

    int getIntInput(int maxTentativas = MAX_TENTATIVAS_PADRAO){
        int tentativas = 0
        while (tentativas < maxTentativas){
            try{
                return Integer.parseInt(br.readLine())
            } catch (Exception ignored){
                println("Input inválido")
                tentativas++
            }
        }
        throw new RuntimeException("Máximo de tentativas excedido para input inteiro")
    }

    int getIdadeInput(int maxTentativas = MAX_TENTATIVAS_PADRAO){
        int tentativas = 0
        while (tentativas < maxTentativas){
            try{
                return Integer.parseInt(br.readLine())
            } catch (Exception ignored){
                println("Input inválido")
                tentativas++
            }
        }
        throw new RuntimeException("Máximo de tentativas excedido para idade")
    }

    String getSenhaInput(int maxTentativas = MAX_TENTATIVAS_PADRAO) {
        return getStringInput();
    }

}