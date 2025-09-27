import { renderFormCandidato, renderFormEmpresa } from "../components/form-cadastro"

export function renderCadastro(app: HTMLDivElement, type: string){
    if(type == "Candidato"){
        renderFormCandidato(app)
    }
    else if (type == "Empresa"){
        renderFormEmpresa(app)
        
    } else{
        window.location.reload()
    }
}