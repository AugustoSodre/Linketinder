import PageFactory from "./factories/PageFactory";
import { clearSession, getSession, loadData } from "./api";
import { listCandidatos, listEmpresas } from "./storage/lists";
import type { UsuarioLogado } from "./models/types";

export function router(){
    const app = document.querySelector<HTMLDivElement>("#app")!;

    const session = getSession()
    const empresa = session?.tipo === "empresa" ? listEmpresas.find(u => u.id === session.id) : undefined
    const candidato = session?.tipo === "candidato" ? listCandidatos.find(u => u.id === session.id) : undefined
    const currentUser: UsuarioLogado | undefined =
        empresa ? { ...empresa, tipo: "empresa" } : candidato ? { ...candidato, tipo: "candidato" } : undefined

    if(session && !currentUser) clearSession()

    const page = currentUser ? PageFactory.create("home") : PageFactory.create("menu");

    if(page){
        if(currentUser) page(app, currentUser)
        else page(app)
    } else {
        window.location.reload()
    }
}

export async function refresh(){
    await loadData()
    router()
}
