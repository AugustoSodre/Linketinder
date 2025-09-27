import { renderMenu } from "./menu"

export function renderLogin(app: HTMLDivElement, type: string){
    if(type == "Candidato"){
        renderLoginCandidato(app)
    }
    else if (type == "Empresa"){
        renderLoginEmpresa(app)
        
    } else{
        renderMenu(app)
    }
}


function renderLoginCandidato(app: HTMLDivElement){
    console.log("Clicou Login Candidato")
}


function renderLoginEmpresa(app: HTMLDivElement){
    console.log("Clicou Login Empresa")
}