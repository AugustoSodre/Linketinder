package com.augusto.linketinder.view.menu;

import com.augusto.linketinder.control.MenuController;
import com.augusto.linketinder.service.InputService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmpresaMenuTest {

    private RecordingMenuController controller;
    private InputService inputService;
    private com.augusto.linketinder.view.MenuView menuView;

    @BeforeEach
    void setUp() {
        controller = new RecordingMenuController();
        inputService = new InputService();
        menuView = new com.augusto.linketinder.view.MenuView(inputService, controller);
    }

    @Test
    void option1_callsCreate() {
        inputService.setBr(new BufferedReader(new StringReader("1\n")));
        menuView.showEmpresaMenu();
        assertEquals(1, controller.createCalls);
    }

    @Test
    void option2_callsRead() {
        inputService.setBr(new BufferedReader(new StringReader("2\n")));
        menuView.showEmpresaMenu();
        assertEquals(1, controller.readCalls);
    }

    @Test
    void option4_callsDelete() {
        inputService.setBr(new BufferedReader(new StringReader("4\n")));
        menuView.showEmpresaMenu();
        assertEquals(1, controller.deleteCalls);
    }

    private static class RecordingMenuController extends MenuController {
        int createCalls = 0;
        int readCalls = 0;
        int deleteCalls = 0;

        @Override
        public void handleEmpresaOption(int option) {
            switch (option) {
                case 1: createCalls++; break;
                case 2: readCalls++; break;
                case 4: deleteCalls++; break;
            }
        }
    }
}
