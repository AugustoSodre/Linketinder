import { escapeHtml } from "./escapeHtml"
import { handleFormCandidato, handleFormEmpresa, handleFormLogin } from "../handlers/formHandler"
import { listCompetenciasDisponiveis } from "../storage/lists"

const handlersPorFormulario: Record<string, () => void> = {
    "form-cadastro-candidato": handleFormCandidato,
    "form-cadastro-empresa": handleFormEmpresa,
    "form-login-candidato": () => handleFormLogin("candidato"),
    "form-login-empresa": () => handleFormLogin("empresa"),
}

export function createFormListeners(){
    for (const [id, handler] of Object.entries(handlersPorFormulario)) {
        document.getElementById(id)?.addEventListener("submit", (e) => {
            e.preventDefault()
            handler()
        })
    }
}

export function generateCompetenciasText(sufixo: string, marcadas: string[] = []): string{
    return listCompetenciasDisponiveis.map((competencia) => {
        const id = escapeHtml(`competencia-${sufixo}-${competencia}`)
        return `
        <div class="competencia-div">
            <label for="${id}">${escapeHtml(competencia)}</label>
            <input type="checkbox" id="${id}" value="${escapeHtml(competencia)}" class="competencia-${sufixo}"${marcadas.includes(competencia) ? " checked" : ""}>
        </div>`
    }).join("")
}
