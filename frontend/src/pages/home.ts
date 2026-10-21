import { renderHomeCandidatoHTML } from "../components/homeGenerator"
import { renderHomeEmpresaHTML } from "../components/homeGenerator"

export function renderHome(app: HTMLDivElement, currentUser: any){

    if(currentUser.tipo === "empresa"){
        renderHomeEmpresaHTML(app, currentUser)
    } else {
        renderHomeCandidatoHTML(app, currentUser)
    }

}
