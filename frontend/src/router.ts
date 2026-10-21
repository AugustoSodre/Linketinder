import PageFactory from "./factories/PageFactory";
import { clearSession, getSession } from "./api";
import { listCandidatos, listEmpresas } from "./storage/lists";

export function router(){
    const app = document.querySelector<HTMLDivElement>("#app")!;

    const session = getSession()
    const lista = session?.tipo === "empresa" ? listEmpresas : listCandidatos
    const user = session ? lista.find(u => u.id === session.id) : undefined

    // Sessão de um usuário que não existe mais: volta ao menu
    if(session && !user) clearSession()

    const currentUser = user ? { ...user, tipo: session!.tipo } : undefined
    const page = currentUser ? PageFactory.create("home") : PageFactory.create("menu");

    if(page){
        if(currentUser) page(app, currentUser)
        else page(app)
    } else {
        window.location.reload()
    }
}
