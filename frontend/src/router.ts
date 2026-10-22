import PageFactory from "./factories/PageFactory";
import { clearSession, getSession, loadData } from "./api";
import { listCandidatos, listEmpresas } from "./storage/lists";

export function router(){
    const app = document.querySelector<HTMLDivElement>("#app")!;

    const session = getSession()
    const lista: { id: number }[] = session?.tipo === "empresa" ? listEmpresas : listCandidatos
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

// Recarrega os dados da API e redesenha a tela, sem recarregar a página
export async function refresh(){
    await loadData()
    router()
}
