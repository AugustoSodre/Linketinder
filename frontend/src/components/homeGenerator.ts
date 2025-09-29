import { createHomeListeners, renderHomeHeaderHTML, renderHomeLeftProfileHTML, renderHomeRightProfileHTML, renderHomeListaVagas, renderHomeListaCandidatos } from "../helpers/homeHelpers"


export function renderHomeCandidatoHTML(app: HTMLDivElement, currentUser: any){
    let text = `
    <main id="profile-page" class="profile-page container">
    `

    text += renderHomeHeaderHTML()

    text += `<div id="profile-body" class="profile-body">`

    text += renderHomeLeftProfileHTML(currentUser)

    text += renderHomeRightProfileHTML(currentUser)

    text += renderHomeListaVagas(currentUser)

    text +=
    `   </div>
    </main>
    `

    app.innerHTML = text

    createHomeListeners(currentUser)
}

export function renderHomeEmpresaHTML(app: HTMLDivElement, currentUser: any){
    let text: string = `<main id="profile-page" class="profile-page container">`
    
    text += renderHomeHeaderHTML()

    text += `<div id="profile-body" class="profile-body">`

    text += renderHomeLeftProfileHTML(currentUser)

    text += renderHomeRightProfileHTML(currentUser)

    text += renderHomeListaCandidatos(currentUser)

    //Render Grafico()
    
    text += `
      </div>
    </main>
    `

    app.innerHTML = text

    createHomeListeners(currentUser)
}

