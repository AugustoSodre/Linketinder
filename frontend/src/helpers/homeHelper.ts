import { escapeHtml } from "./escapeHtml"
import { listarCandidatos, listarVagas } from "../components/listar"
import { clearSession, deleteCurrentUser, getSession } from "../api"

export function createHomeListeners(){

    document.getElementById("btn-logout")?.addEventListener("click", () => {
        clearSession()
        window.location.reload()
    })

    document.getElementById("btn-delete")?.addEventListener("click", async () => {
        const session = getSession()
        if(!session || !confirm("Excluir seu perfil?")) return

        try {
            await deleteCurrentUser(session)
            clearSession()
            window.location.reload()
        } catch (e) {
            alert((e as Error).message)
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

export function renderHomeLeftProfileHTML(currentUser: any): string{
    let text: string = ""
    if(currentUser.tipo === "empresa"){
        text = `
        <div id="profile-left" class="profile-column">
        <div class="profile-item" id="item-nome">
            <label for="nome">Nome Empresa:</label>
            <p id="nome">${escapeHtml(currentUser.nome)}</p>
        </div>

        <div class="profile-item" id="item-email">
            <label for="email">Email:</label>
            <p id="email">${escapeHtml(currentUser.email)}</p>
        </div>

        <div class="profile-item" id="item-estado">
            <label for="estado">Estado:</label>
            <p id="estado">${escapeHtml(currentUser.estado)}</p>
        </div>

        <div class="profile-item" id="item-cep">
            <label for="cep">CEP:</label>
            <p id="cep">${escapeHtml(currentUser.cep)}</p>
        </div>

        <div class="profile-item" id="item-pais">
            <label for="pais">País:</label>
            <p id="pais">${escapeHtml(currentUser.pais)}</p>
        </div>

        </div>
        `
    } 

    else{
        text = `
        <div id="profile-left" class="profile-column">
            <div class="profile-item" id="item-nome">
              <label for="nome">Nome:</label>
              <p id="nome">${escapeHtml(currentUser.nome)}</p>
            </div>

            <div class="profile-item" id="item-email">
              <label for="email">Email:</label>
              <p id="email">${escapeHtml(currentUser.email)}</p>
            </div>

            <div class="profile-item" id="item-estado">
              <label for="estado">Estado:</label>
              <p id="estado">${escapeHtml(currentUser.estado)}</p>
            </div>

            <div class="profile-item" id="item-cep">
              <label for="cep">CEP:</label>
              <p id="cep">${escapeHtml(currentUser.cep)}</p>
            </div>

            <div class="profile-item" id="item-idade">
              <label for="idade">Idade:</label>
              <p id="idade">${escapeHtml(currentUser.idade)}</p>
            </div>
            
          </div>
    `
    }
    

    return text
}

export function renderHomeRightProfileHTML(currentUser: any){
    let text = ""

    if(currentUser.tipo === "empresa"){
        text = `
        <div id="profile-right" class="profile-column">
            <div class="profile-item" id="item-cpf">
                <label for="cpf">CNPJ:</label>
                <p id="cpf">${escapeHtml(currentUser.cnpj)}</p>
            </div>

            <div class="profile-item" id="item-descricao">
                <label for="descricao">Descrição:</label>
                <p id="descricao">${escapeHtml(currentUser.descricao)}</p>
            </div>

            <div class="profile-item" id="profile-competencias">
                <label for="competencias">Competências:</label>
                <ul id="competencias" class="skills">
                `
        text += generateCompetenciasList(currentUser)

        text+=
                `
                </ul>
            </div>
        </div>
        `
        
        text +=
        `
        </div>
        `
    } 

    else{
        text = `
        <div id="profile-right" class="profile-column">
            <div class="profile-item" id="item-cpf">
              <label for="cpf">CPF:</label>
              <p id="cpf">${escapeHtml(currentUser.cpf)}</p>
            </div>

            <div class="profile-item" id="item-descricao">
              <label for="descricao">Descrição:</label>
              <p id="descricao">${escapeHtml(currentUser.descricao)}</p>
            </div>
        `
        text += `

            <div class="profile-item" id="profile-competencias">
              <label for="competencias">Competências:</label>
              `
        text += generateCompetenciasList(currentUser)

        text += `
            </div>
          </div>
        `
    }

    return text
}

export function renderHomeListaVagas(currentUser: any): string{
    let text = ""

    text += `
    <div class="list-vagas" id="list-vagas-container">
        <h2 for="vagas">Vagas</h2>
    `
    
    text += listarVagas(currentUser)
    
    return text

}

export function renderHomeListaCandidatos(currentUser: any): string{
    let text: string = `
    <div class="list-candidatos" id="list-candidatos-container">
    <h2 for="candidatos">Candidatos Disponíveis</h2>
    `;

    text += listarCandidatos(currentUser)

    text += `</div>`;

    return text;
}

export function generateCompetenciasList(currentUser: any): string{

    let text = `<ul id="competencias" class="skills">`

    currentUser.competencias.forEach((c: any) => {
        text += `<li class="skill" id="skill-${escapeHtml(c)}">${escapeHtml(c)}</li>`
    })

    text += `</ul>`

    return text
}