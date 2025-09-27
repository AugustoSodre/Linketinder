import { renderFormLogin } from "../components/form-login"

export function renderLogin(app: HTMLDivElement, type: string){
    if(type == "Candidato"){
        renderFormLogin(app, "Candidato")
    }
    else if (type == "Empresa"){
        renderFormLogin(app, "Empresa")
        
    } else{
        window.location.reload()
    }
}