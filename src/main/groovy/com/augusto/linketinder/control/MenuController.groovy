package com.augusto.linketinder.control

class MenuController {

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in))

    int getIntInput(){
        print "Digite sua opção: "
        while (true){
            try{
                return Integer.parseInt(br.readLine())
            } catch (Exception ignored){
                println("Input inválido")
            }
        }

    }
}
