import { renderHomeCandidatoHTML } from "../components/homeGenerator"
import { renderHomeEmpresaHTML } from "../components/homeGenerator"
import type { UsuarioLogado } from "../models/types"

export function renderHome(app: HTMLDivElement, currentUser: UsuarioLogado){

    if(currentUser.tipo === "empresa"){
        renderHomeEmpresaHTML(app, currentUser)
    } else {
        renderHomeCandidatoHTML(app, currentUser)
    }

}
