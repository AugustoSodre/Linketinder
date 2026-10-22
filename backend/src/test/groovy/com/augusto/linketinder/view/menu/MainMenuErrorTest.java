package com.augusto.linketinder.view.menu;

import com.augusto.linketinder.control.MenuController;
import com.augusto.linketinder.service.InputService;
import com.augusto.linketinder.view.MenuView;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;

class MainMenuErrorTest {

    @Test
    void erroNaOpcaoNaoDerrubaOMenu() {
        MenuController controller = new MenuController() {
            @Override
            public void handleCandidatoOption(int option) {
                throw new RuntimeException("banco fora do ar");
            }
        };
        InputService inputService = new InputService();
        inputService.setBr(new BufferedReader(new StringReader("2\n2\n")));
        MenuView menuView = new MenuView(inputService, controller);

        assertDoesNotThrow(() -> assertFalse(menuView.showMainMenu()));
    }
}
