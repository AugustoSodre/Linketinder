package com.augusto.linketinder.view

import com.augusto.linketinder.control.MenuController
import com.augusto.linketinder.service.InputService

class MenuView {

	private final InputService inputService
	private final MenuController menuController

	MenuView() {
		this(new InputService(), new MenuController())
	}

	MenuView(InputService inputService, MenuController menuController) {
		this.inputService = inputService
		this.menuController = menuController
	}

	/**
	 * Shows the main menu and delegates submenu choices to the controller.
	 * Returns true when the user chooses to exit (0).
	 */
	boolean showMainMenu() {
		printMainOptions()
		int option = inputService.getIntInput()
		switch (option) {
			case 1:
				limpaTela()
				showEmpresaMenu()
				break
			case 2:
				limpaTela()
				showCandidatoMenu()
				break
			case 3:
				limpaTela()
				showVagaMenu()
				break
			case 4:
				limpaTela()
				showCompetenciaMenu()
				break
			case 0:
				return true
			default:
				println "Input inválido!"
		}

		return false
	}

	void printMainOptions() {
		limpaTela()
		println()
		println("-" * 25)
		println "Linketinder!"
		println()
		println "Opções:"
		println "1. Gerenciar Empresas"
		println "2. Gerenciar Candidatos"
		println "3. Gerenciar Vagas"
		println "4. Gerenciar Competências"
		println "0. Sair"
		println()
		println("-" * 25)
	}

	void showEmpresaMenu() {
		println()
		println("-" * 25)
		println "Empresa"
		println()
		println "Opções:"
		println "1. Cadastrar Empresa"
		println "2. Mostrar Empresas"
		println "3. Alterar Empresa"
		println "4. Deletar Empresa"
		println "0. Sair"
		println()
		println("-" * 25)

		int option = inputService.getIntInput()
		menuController.handleEmpresaOption(option)
	}

	void showCandidatoMenu() {
		println()
		println("-" * 25)
		println "Candidatos"
		println()
		println "Opções:"
		println "1. Cadastrar Candidato"
		println "2. Mostrar Candidatos"
		println "3. Alterar Candidato"
		println "4. Deletar Candidato"
		println "0. Sair"
		println()
		println("-" * 25)

		int option = inputService.getIntInput()
		menuController.handleCandidatoOption(option)
	}

	void showVagaMenu() {
		println()
		println("-" * 25)
		println "Vagas"
		println()
		println "Opções:"
		println "1. Cadastrar Vaga"
		println "2. Mostrar Vagas"
		println "3. Alterar Vaga"
		println "4. Deletar Vaga"
		println "0. Sair"
		println()
		println("-" * 25)

		int option = inputService.getIntInput()
		menuController.handleVagaOption(option)
	}

	void showCompetenciaMenu() {
		println()
		println("-" * 25)
		println "Competências"
		println()
		println "Opções:"
		println "1. Cadastrar Competência"
		println "2. Mostrar Competências"
		println "3. Alterar Competência"
		println "4. Deletar Competência"
		println "0. Sair"
		println()
		println("-" * 25)

		int option = inputService.getIntInput()
		menuController.handleCompetenciaOption(option)
	}

	void limpaTela() {
		for (i in 0..<50) {
			println()
		}
	}

}
