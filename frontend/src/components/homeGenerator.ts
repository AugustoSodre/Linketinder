import { createHomeListeners, renderHomeHeaderHTML, renderHomeLeftProfileHTML, renderHomeRightProfileHTML, renderHomeVagas } from "../helpers/homeHelpers"


export function renderHomeCandidatoHTML(app: HTMLDivElement, currentUser: any){
    console.log("Home candidato")

    let text = `
    <main id="profile-page" class="profile-page container">
    `

    text += renderHomeHeaderHTML()

    text += `<div id="profile-body" class="profile-body">`

    text += renderHomeLeftProfileHTML(currentUser)

    text += renderHomeRightProfileHTML(currentUser)

    text += renderHomeVagas()

    text +=
    `   </div>
    </main>
    `

    app.innerHTML = text

    createHomeListeners(currentUser)
}

export function renderHomeEmpresaHTML(app: HTMLDivElement, currentUser: any){
    let text = ""
    console.log("Home empresa")

    app.innerHTML = text
}

