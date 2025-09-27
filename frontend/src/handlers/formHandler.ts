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

    const newCandidato = new Candidato(nome, email, estado, cep, descricao, idade, cpf, listCompetencias)

    listCandidatos.push(newCandidato)

    localStorage.setItem("listCandidatos", JSON.stringify(listCandidatos))

    window.location.reload()

}

export function handleFormEmpresa(listCompetencias: string[]){
    const nome = (document.getElementById("nome") as HTMLInputElement)?.value
    const email = (document.getElementById("email") as HTMLInputElement)?.value
    const estado = (document.getElementById("estado") as HTMLInputElement)?.value
    const cep = (document.getElementById("cep") as HTMLInputElement)?.value
    const descricao = (document.getElementById("descricao") as HTMLInputElement)?.value
    const pais = (document.getElementById("pais") as HTMLInputElement)?.value
    const cnpj = (document.getElementById("cnpj") as HTMLInputElement)?.value

    const vaga = new Vaga(
        (document.getElementById("nome-vaga") as HTMLInputElement)?.value,
        (document.getElementById("descricao-vaga") as HTMLInputElement)?.value,
        listCompetencias,
    )

    const newEmpresa = new Empresa(nome, email, estado, cep, descricao, pais, cnpj, vaga)

    listEmpresas.push(newEmpresa)

    localStorage.setItem("listEmpresas", JSON.stringify(listEmpresas))

    window.location.reload()

}