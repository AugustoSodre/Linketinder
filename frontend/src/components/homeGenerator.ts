import { createHomeListeners, renderHomeHeaderHTML, renderHomeLeftProfileHTML, renderHomeRightProfileHTML, renderHomeListaVagas, renderHomeListaCandidatos } from "../helpers/homeHelper"
import { generateCandidatosCompetenciaGraphListener } from "./grafico"
import { createPerfilListeners, createVagasListeners, renderEditarPerfilHTML, renderMinhasVagasHTML } from "./perfil"


export function renderHomeCandidatoHTML(app: HTMLDivElement, currentUser: any){
    let text = `
    <main id="profile-page" class="profile-page container">
    `

    text += renderHomeHeaderHTML()

    text += `<div id="profile-body" class="profile-body">`

    text += renderHomeLeftProfileHTML(currentUser)

    text += renderHomeRightProfileHTML(currentUser)

    text += renderEditarPerfilHTML(currentUser)

    text += renderHomeListaVagas(currentUser)

    text +=
    `   </div>
    </main>
    `

    app.innerHTML = text

    createHomeListeners()
    createPerfilListeners(currentUser)
}

export function renderHomeEmpresaHTML(app: HTMLDivElement, currentUser: any){
    let text: string = `<main id="profile-page" class="profile-page container">`
    
    text += renderHomeHeaderHTML()

    text += `<div id="profile-body" class="profile-body">`

    text += renderHomeLeftProfileHTML(currentUser)

    text += renderHomeRightProfileHTML(currentUser)

    text += renderEditarPerfilHTML(currentUser)

    text += renderMinhasVagasHTML(currentUser)

    text += renderHomeListaCandidatos(currentUser)

    text += `<canvas id="chart-candidatos-competencias" style="width:100%;max-width:600px"></canvas>`
    
    text += `
      </div>
    </main>
    `

    app.innerHTML = text

    createHomeListeners()
    createPerfilListeners(currentUser)
    createVagasListeners(currentUser)
    generateCandidatosCompetenciaGraphListener()
}

