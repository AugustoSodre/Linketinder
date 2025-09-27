import { Candidato } from "../models/Candidato"
import { router } from "../router"
import { listCandidatos } from "../storage/lists"

export function handleFormCandidato(){
    const nome = (document.getElementById("nome") as HTMLInputElement)?.value
    const email = (document.getElementById("email") as HTMLInputElement)?.value
    const estado = (document.getElementById("estado") as HTMLInputElement)?.value
    const cep = (document.getElementById("cep") as HTMLInputElement)?.value
    const descricao = (document.getElementById("descricao") as HTMLInputElement)?.value
    const idade = parseInt((document.getElementById("idade") as HTMLInputElement)?.value ?? "")
    const cpf = (document.getElementById("cpf") as HTMLInputElement)?.value

    const newCandidato = new Candidato(nome, email, estado, cep, descricao, idade, cpf)

    listCandidatos.push(newCandidato)

    localStorage.setItem("listCandidatos", JSON.stringify(listCandidatos))

    window.location.reload()

}