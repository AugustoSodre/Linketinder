import { renderFormCandidato } from "../components/form"
import { Candidato } from "../models/Candidato"
import { listCandidatos, listEmpresas } from "../storage/lists"
import { renderMenu } from "./menu"

export function renderCadastro(app: HTMLDivElement, type: string){
    if(type == "Candidato"){
        renderCadastroCandidato(app)
    }
    else if (type == "Empresa"){
        renderCadastroEmpresa(app)
        
    } else{
        renderMenu(app)
    }

    console.log(listCandidatos)
    console.log(listEmpresas)
}


function renderCadastroCandidato(app: HTMLDivElement){
    console.log("Clicou Cadastro Candidato")

    renderFormCandidato(app)

}


function renderCadastroEmpresa(app: HTMLDivElement){
    console.log("Clicou Cadastro Empresa")
}