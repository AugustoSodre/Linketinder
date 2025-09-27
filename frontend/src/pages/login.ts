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
    app.innerHTML = "<h2><strong>Login Candidato</strong></h2>"
    console.log("Clicou login")
}


function renderLoginEmpresa(app: HTMLDivElement){

}