package com.augusto.linketinder

import com.augusto.linketinder.API.HTTPServerAPI
import com.augusto.linketinder.service.InputService
import com.augusto.linketinder.view.MenuView
import com.augusto.linketinder.control.MenuController

static void main(String[] args){
    final HTTPServerAPI httpServerAPI = new HTTPServerAPI(8080)
    final InputService inputService = new InputService()
    final MenuController menuController = new MenuController()
    final MenuView menuView = new MenuView(inputService, menuController)

    httpServerAPI.start()

    while (true) {
        if (menuView.showMainMenu()) {
            break
        }
    }

    httpServerAPI.stop()

}