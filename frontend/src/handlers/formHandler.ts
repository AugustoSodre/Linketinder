import { Candidato } from "../models/Candidato"
import { Empresa } from "../models/Empresa"
import { Vaga } from "../models/Vaga"
import { listCandidatos, listEmpresas } from "../storage/lists"

export function handleFormCandidato(listCompetencias: string[]){
    const nome = (document.getElementById("nome") as HTMLInputElement)?.value
    const email = (document.getElementById("email") as HTMLInputElement)?.value
    const estado = (document.getElementById("estado") as HTMLInputElement)?.value
    const cep = (document.getElementById("cep") as HTMLInputElement)?.value
    const descricao = (document.getElementById("descricao") as HTMLInputElement)?.value
    const idade = parseInt((document.getElementById("idade") as HTMLInputElement)?.value ?? "")
    const cpf = (document.getElementById("cpf") as HTMLInputElement)?.value

    const newCandidato = new Candidato(nome, email, estado, cep, descricao, listCompetencias, idade, cpf)

    listCandidatos.push(newCandidato)

    localStorage.setItem("listCandidatos", JSON.stringify(listCandidatos))

    window.location.reload()

}

export function handleFormEmpresa(listCompetenciasEmpresa: string[], listCompetenciasVaga: string[]){
    const nome = (document.getElementById("nome") as HTMLInputElement)?.value
    const email = (document.getElementById("email") as HTMLInputElement)?.value
    const estado = (document.getElementById("estado") as HTMLInputElement)?.value
    const cep = (document.getElementById("cep") as HTMLInputElement)?.value
    const descricao = (document.getElementById("descricao") as HTMLInputElement)?.value
    const pais = (document.getElementById("pais") as HTMLInputElement)?.value
    const cnpj = (document.getElementById("cnpj") as HTMLInputElement)?.value
    const competencias = listCompetenciasEmpresa

    const vaga = new Vaga(
        (document.getElementById("nome-vaga") as HTMLInputElement)?.value,
        (document.getElementById("descricao-vaga") as HTMLInputElement)?.value,
        listCompetenciasVaga
    )

    const newEmpresa = new Empresa(nome, email, estado, cep, descricao, competencias, pais, cnpj, vaga)

    listEmpresas.push(newEmpresa)

    localStorage.setItem("listEmpresas", JSON.stringify(listEmpresas))

    window.location.reload()

}

export function handleFormLoginCandidato(){
    const cpfInput: string = (document.getElementById("cpf-login-candidato") as HTMLInputElement)?.value ?? ""

    console.log(cpfInput)

    for (const c of listCandidatos) {
        // checa instancia real de Candidato ou objetos JSON do localstorage
        const cpf = (c as any)?.getCpf?.() ?? (c as any)?.cpf ?? ""

        if (cpf === cpfInput) {
            localStorage.setItem("currentUser", JSON.stringify(c))
            window.location.reload()
            return
        }
    }

    alert("CPF não encontrado!")
    
}

export function handleFormLoginEmpresa(){
    const cnpjInput: string = (document.getElementById("cnpj-login-empresa") as HTMLInputElement)?.value ?? ""

    console.log(cnpjInput)

    for (const c of listEmpresas) {
        // checa instancia real de Candidato ou objetos JSON do localstorage
        const cnpj = (c as any)?.getCnpj?.() ?? (c as any)?.cnpj ?? ""

        if (cnpj === cnpjInput) {
            localStorage.setItem("currentUser", JSON.stringify(c))
            window.location.reload()
            return
        }
    }

    alert("CNPJ não encontrado!")
}