import { createHomeListeners, renderHomeHeaderHTML, renderHomeLeftProfileHTML, renderHomeRightProfileHTML, renderHomeListaVagas, renderHomeListaCandidatos } from "../helpers/homeHelper"
import { generateCandidatosCompetenciaGraphListener } from "../components/grafico"
import { createNovaCompetenciaListener, createPerfilListeners, createVagasListeners, renderEditarPerfilHTML, renderMinhasVagasHTML, renderNovaCompetenciaHTML } from "../components/perfil"
import type { UsuarioLogado } from "../models/types"

export function renderHome(app: HTMLDivElement, currentUser: UsuarioLogado){
    const conteudo = currentUser.tipo === "empresa"
        ? renderMinhasVagasHTML(currentUser) + renderHomeListaCandidatos(currentUser)
          + `<canvas id="chart-candidatos-competencias" style="width:100%;max-width:600px"></canvas>`
        : renderHomeListaVagas(currentUser)

    app.innerHTML = `
    <main id="profile-page" class="profile-page container">
        ${renderHomeHeaderHTML()}
        <div id="profile-body" class="profile-body">
            ${renderHomeLeftProfileHTML(currentUser)}
            ${renderHomeRightProfileHTML(currentUser)}
            ${renderEditarPerfilHTML(currentUser)}
            ${renderNovaCompetenciaHTML()}
            ${conteudo}
        </div>
    </main>`

    createHomeListeners()
    createPerfilListeners(currentUser)
    createNovaCompetenciaListener()
    if (currentUser.tipo === "empresa") {
        createVagasListeners(currentUser)
        generateCandidatosCompetenciaGraphListener()
    }
}
