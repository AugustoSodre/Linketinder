import { escapeHtml } from "./escapeHtml"
import { listarCandidatos, listarVagas } from "../components/listar"
import { clearSession, deleteCurrentUser, getSession } from "../api"
import { refresh } from "../router"
import { notify } from "./notify"

export function createHomeListeners(){

    document.getElementById("btn-logout")?.addEventListener("click", () => {
        clearSession()
        refresh()
    })

    document.getElementById("btn-delete")?.addEventListener("click", async () => {
        const session = getSession()
        if(!session || !confirm("Excluir seu perfil?")) return

        try {
            await deleteCurrentUser(session)
            clearSession()
            notify("Perfil excluído.", "ok")
            await refresh()
        } catch (e) {
            notify((e as Error).message)
        }
    })

}

export function renderHomeHeaderHTML(): string{
    const text = `
    <header id="profile-header" class="profile-header">
        <h2 id="profile-title">Meu perfil</h2>

        <div id="profile-actions" class="profile-actions">
            <button id="btn-delete" class="btn btn-danger" aria-label="Excluir perfil">Delete</button>
            <button id="btn-logout" class="btn" aria-label="Logout">Logout</button>
        </div>
    </header>
    `

    return text
}

const itemPerfil = (campo: string, label: string, valor: unknown) => `
        <div class="profile-item" id="item-${campo}">
            <label>${label}</label>
            <p id="${campo}">${escapeHtml(valor)}</p>
        </div>`

export function renderHomeLeftProfileHTML(currentUser: any): string{
    const empresa = currentUser.tipo === "empresa"
    return `
    <div id="profile-left" class="profile-column">
        ${itemPerfil("nome", empresa ? "Nome Empresa:" : "Nome:", currentUser.nome)}
        ${itemPerfil("email", "Email:", currentUser.email)}
        ${itemPerfil("estado", "Estado:", currentUser.estado)}
        ${itemPerfil("cep", "CEP:", currentUser.cep)}
        ${empresa ? itemPerfil("pais", "País:", currentUser.pais) : itemPerfil("idade", "Idade:", currentUser.idade)}
    </div>`
}

export function renderHomeRightProfileHTML(currentUser: any): string{
    const empresa = currentUser.tipo === "empresa"
    return `
    <div id="profile-right" class="profile-column">
        ${empresa ? itemPerfil("cpf", "CNPJ:", currentUser.cnpj) : itemPerfil("cpf", "CPF:", currentUser.cpf)}
        ${itemPerfil("descricao", "Descrição:", currentUser.descricao)}
        <div class="profile-item" id="profile-competencias">
            <label>Competências:</label>
            ${generateCompetenciasList(currentUser)}
        </div>
    </div>`
}

export function renderHomeListaVagas(currentUser: any): string{
    return `
    <div class="list-vagas" id="list-vagas-container">
        <h2>Vagas</h2>
        ${listarVagas(currentUser)}
    </div>`
}

export function renderHomeListaCandidatos(currentUser: any): string{
    return `
    <div class="list-candidatos" id="list-candidatos-container">
        <h2>Candidatos Disponíveis</h2>
        ${listarCandidatos(currentUser)}
    </div>`
}

export function generateCompetenciasList(pessoa: { competencias: string[] }): string{
    return `<ul class="skills">${pessoa.competencias.map(c => `<li class="skill">${escapeHtml(c)}</li>`).join("")}</ul>`
}
