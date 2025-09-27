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
}


function renderCadastroCandidato(app: HTMLDivElement){

}


function renderCadastroEmpresa(app: HTMLDivElement){

}