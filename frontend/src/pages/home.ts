import { renderHomeCandidatoHTML } from "../components/homeGenerator"
import { renderHomeEmpresaHTML } from "../components/homeGenerator"

export function renderHome(app: HTMLDivElement, currentUser: any){

    const parsedUser = JSON.parse(currentUser)

    if(!parsedUser.vaga){
        renderHomeCandidatoHTML(app, parsedUser)
        
    } else if (parsedUser.vaga){
        renderHomeEmpresaHTML(app, parsedUser)

    } else{
        window.location.reload()
    }

}