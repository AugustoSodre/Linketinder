import { renderFormCandidato, renderFormEmpresa } from "../components/form"

export function renderCadastro(app: HTMLDivElement, type: string){
    if(type == "Candidato"){
        renderCadastroCandidato(app)
    }
    else if (type == "Empresa"){
        renderCadastroEmpresa(app)
        
    } else{
        window.location.reload()
    }
}


function renderCadastroCandidato(app: HTMLDivElement){
    renderFormCandidato(app)

}


function renderCadastroEmpresa(app: HTMLDivElement){
    renderFormEmpresa(app)
}