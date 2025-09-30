import { listCandidatos, listEmpresas } from "../storage/lists"

export function getValidNome(): string | null {
	let nome: string = (document.getElementById("nome") as HTMLInputElement)?.value

	const regexCustom = /^[a-zA-ZÀ-ÿ\s]+$/

	if(!nome){
		alert("Nome Inválido! Tente novamente!")
		return null
	}

	if(regexCustom.test(nome)){
		return nome
	} else{
		alert("Nome com caractere Inválido! Tente novamente!")
		return null
	}
	
}

export function getValidEmail(): string | null{
	let email: string = (document.getElementById("email") as HTMLInputElement)?.value

	const regexCustom = /^[\w.]+@\w+\.\w+(\.\w{2,3})?$/

	if(regexCustom.test(email)){
		return email
	} else {
		alert("Email Inválido! Tente novamente!")
		return null
	}
	
}

export function getValidEstado(): string | null{
	let estado: string = (document.getElementById("estado") as HTMLInputElement)?.value

	const regexCustom = /^[A-Z]{2}$/

	if(regexCustom.test(estado)){
		return estado
	} else{
		alert("Estado Inválido! Tente novamente!")
		return null
	}
}

export function getValidCEP(): string | null{
	let cep: string = (document.getElementById("cep") as HTMLInputElement)?.value

	const regexCustom = /^\d{5}-?\d{3}$/

	if(regexCustom.test(cep)){
		cep = cep.replaceAll("-","")
		return cep
	} else{
		alert("CEP Inválido! Tente novamente!")
		return null
	}
}

export function getValidDescricao(): string | null{
	let descricao = (document.getElementById("descricao") as HTMLInputElement)?.value

	if(descricao?.length > 0){
		return descricao
	} else{
		alert("Descrição Inválida! Tente novamente!")
		return null
	}
}

export function getValidIdade(): number | null{
	let idade: number = parseInt((document.getElementById("idade") as HTMLInputElement)?.value ?? "")

	if( isNaN(idade) || idade > 120 || idade < 0){
		alert("Idade inválida! Tente novamente!")
		return null
	} else{
		return idade
	}

}

export function getValidCPF(): string | null{
	let cpf: string = (document.getElementById("cpf") as HTMLInputElement)?.value

	const regexCustom = /^\d{3}\.?\d{3}\.?\d{3}\-?\d{2}$/

	if(regexCustom.test(cpf)){
		cpf = cpf.replaceAll(".", "")
		cpf = cpf.replaceAll("-", "")
	} else {
		alert("CPF Inválido! Tente novamente!")
		return null
	}

	for(const c of listCandidatos){
		if((c as any).cpf == cpf){
			alert("CPF Já Cadastrado! Tente novamente!")
			return null
		}
	}

	return cpf

}

export function getValidPais(): string | null{
	let pais: string = (document.getElementById("pais") as HTMLInputElement)?.value

	const regexCustom = /^[A-Z]{2}$/

	if(regexCustom.test(pais)){
		return pais
	} else{
		alert("País Inválido! Tente novamente!")
		return null
	}
}

export function getValidCNPJ(): string | null{
	let cnpj: string = (document.getElementById("cnpj") as HTMLInputElement)?.value

	const regexCustom = /^\d{2}\.?\d{3}\.?\d{3}\/?\d{4}\-?\d{2}$/

	if(regexCustom.test(cnpj)){
		cnpj = cnpj.replaceAll(".", "")
		cnpj = cnpj.replaceAll("-", "")
		cnpj = cnpj.replaceAll("/", "")
	} else {
		alert("CNPJ Inválido! Tente novamente!")
		return null
	}

	for(const c of listEmpresas){
		if((c as any).cnpj == cnpj){
			alert("CNPJ Já Cadastrado! Tente novamente!")
			return null
		}
	}

	return cnpj
}

export function getValidNomeVaga(): string | null{
	let nome: string = (document.getElementById("nome-vaga") as HTMLInputElement)?.value

	const regexCustom = /^[a-zA-ZÀ-ÿ\s]+$/

	if(regexCustom.test(nome)){
		return nome
	} else{
		alert("Nome com caractere Inválido! Tente novamente!")
		return null
	}
}

export function getValidDescricaoVaga(): string | null{
	let descricao = (document.getElementById("descricao-vaga") as HTMLInputElement)?.value

	if(descricao?.length > 0){
		return descricao
	} else{
		alert("Descrição Inválida! Tente novamente!")
		return null
	}
}

export function getValidLoginIdentification(idHTML: string): string | null{
	let identification = (document.getElementById(idHTML) as HTMLInputElement)?.value

	if(idHTML == "cnpj-login-empresa"){
		const regexCustom = /^\d{2}\.?\d{3}\.?\d{3}\/?\d{4}\-?\d{2}$/
		if(regexCustom.test(identification)){
			identification = identification.replaceAll(".", "")
			identification = identification.replaceAll("-", "")
			identification = identification.replaceAll("/", "")
		} else {
			alert("CNPJ Inválido! Tente novamente!")
			return null
		}

	} 
	else if(idHTML == "cpf-login-candidato"){
		const regexCustom = /^\d{3}\.?\d{3}\.?\d{3}\-?\d{2}$/

		if(regexCustom.test(identification)){
			identification = identification.replaceAll(".", "")
			identification = identification.replaceAll("-", "")
		} else {
			alert("CPF Inválido! Tente novamente!")
			return null
		}
	}

	return identification

	
}