package com.augusto.linketinder.control

import com.augusto.linketinder.service.InputService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import static org.mockito.Mockito.*

class MenuControllerTest {

    EmpresaController empresaController
    CandidatoController candidatoController
    VagaController vagaController
    CompetenciaController competenciaController

    @BeforeEach
    void setup() {
        empresaController = mock(EmpresaController.class)
        candidatoController = mock(CandidatoController.class)
        vagaController = mock(VagaController.class)
        competenciaController = mock(CompetenciaController.class)
    }

    @Test
    void "handleEmpresaOption delegates to EmpresaController"() {
        MenuController menuController = new MenuController(new InputService(), empresaController, candidatoController, vagaController, competenciaController)

        menuController.handleEmpresaOption(1)

        verify(empresaController).createEmpresa()
        verifyNoMoreInteractions(empresaController)
    }

    @Test
    void "handleCandidatoOption delegates to CandidatoController"() {
        MenuController menuController = new MenuController(new InputService(), empresaController, candidatoController, vagaController, competenciaController)

        menuController.handleCandidatoOption(2)

        verify(candidatoController).readCandidato()
        verifyNoMoreInteractions(candidatoController)
    }

    @Test
    void "handleCompetenciaOption delegates to CompetenciaController"() {
        MenuController menuController = new MenuController(new InputService(), empresaController, candidatoController, vagaController, competenciaController)

        menuController.handleCompetenciaOption(1)

        verify(competenciaController).createCompetencia()
        verifyNoMoreInteractions(competenciaController)
    }

}
