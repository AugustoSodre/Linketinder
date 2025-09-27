import { renderFormCandidato, renderFormEmpresa } from "../components/form"
import { listCandidatos, listEmpresas } from "../storage/lists"

export function renderCadastro(app: HTMLDivElement, type: string){
    if(type == "Candidato"){
        renderCadastroCandidato(app)
    }
    else if (type == "Empresa"){
        renderCadastroEmpresa(app)
        
    } else{
        window.location.reload()
    }

    console.log(listCandidatos)
    console.log(listEmpresas)
}


function renderCadastroCandidato(app: HTMLDivElement){
    renderFormCandidato(app)

}


function renderCadastroEmpresa(app: HTMLDivElement){
    renderFormEmpresa(app)
}