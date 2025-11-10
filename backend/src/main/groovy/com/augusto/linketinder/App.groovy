package com.augusto.linketinder

import com.augusto.linketinder.service.InputService
import com.augusto.linketinder.view.MenuView
import com.augusto.linketinder.control.MenuController

static void main(String[] args){
    final InputService inputService = new InputService()
    final MenuController menuController = new MenuController()
    final MenuView menuView = new MenuView(inputService, menuController)

    while (true) {
        if (menuView.showMainMenu()) {
            break
        }
    }

}