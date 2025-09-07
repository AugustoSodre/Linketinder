package com.augusto.linketinder

import com.augusto.linketinder.model.lista.EnumCompetencias
import com.augusto.linketinder.model.lista.ListaFisicaEstatica
import com.augusto.linketinder.model.lista.ListaJuridicaEstatica
import com.augusto.linketinder.model.pessoa.PessoaFisica
import com.augusto.linketinder.view.menu.MenuMain

static void main(String[] args){

    new ScriptPopularDadosInicial().start()

    MenuMain menuMain = new MenuMain()

    while (true){
        if(menuMain.showMainMenu()){
            break
        }
    }

}