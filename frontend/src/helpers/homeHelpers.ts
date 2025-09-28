import { listCandidatos, listEmpresas } from "../storage/lists"

export function createHomeListeners(currentUser: any){

    document.getElementById("btn-logout")?.addEventListener("click", () => {
        localStorage.removeItem("currentUser")
        window.location.reload()
    })

    document.getElementById("btn-delete")?.addEventListener("click", () => {
        if(currentUser.vaga){

        } else{
            
            for(let i = 0; i < listCandidatos.length; i++){
                const c: any = listCandidatos[i]

                console.log(c)

                if (c.cpf == currentUser.cpf){
                    listCandidatos.splice(i, 1)
                }
            }

        }

        localStorage.removeItem("currentUser")
        localStorage.setItem("listCandidatos", JSON.stringify(listCandidatos))
        window.location.reload()

    })

}

export function generateCompetenciasList(currentUser: any): string{

    let text = `<ul id="competencias" class="skills">`

    currentUser.competencias.forEach((c: any) => {
        text += `<li class="skill" id="skill-${c}">${c}</li>`
    })

    text += `</ul>`

    return text
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
    if(currentUser.vaga){
        text = ``
    } 

    else{
        text = `
        <div id="profile-left" class="profile-column">
            <div class="profile-item" id="item-nome">
              <label for="nome">Nome:</label>
              <p id="nome">${currentUser.nome}</p>
            </div>

            <div class="profile-item" id="item-email">
              <label for="email">Email:</label>
              <p id="email">${currentUser.email}</p>
            </div>

            <div class="profile-item" id="item-estado">
              <label for="estado">Estado:</label>
              <p id="estado">${currentUser.estado}</p>
            </div>

            <div class="profile-item" id="item-cep">
              <label for="cep">CEP:</label>
              <p id="cep">${currentUser.cep}</p>
            </div>

            <div class="profile-item" id="item-idade">
              <label for="idade">Idade:</label>
              <p id="idade">${currentUser.idade}</p>
            </div>
            
          </div>
    `
    }
    

    return text
}

export function renderHomeRightProfileHTML(currentUser: any){
    let text = ""

    if(currentUser.vaga){

    } 

    else{
        text = `
        <div id="profile-right" class="profile-column">
            <div class="profile-item" id="item-cpf">
              <label for="cpf">CPF:</label>
              <p id="cpf">${currentUser.cpf}</p>
            </div>

            <div class="profile-item" id="item-descricao">
              <label for="descricao">Descrição:</label>
              <p id="descricao">${currentUser.descricao}</p>
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

export function renderHomeVagas(): string{
    let text = ""
    let vagaAtual

    text += `
    <div class="list-vagas" id="list-vagas-container">
        <h2 for="vagas">Vagas</h2>
    `
    
    for(const empresa of listEmpresas){
        vagaAtual = (empresa as any).vaga

        text += `<div class="vaga-div" id="vaga-empresa">`

        text += `
        <label for="nome-empresa">Nome da Empresa:</label>
        <p id="nome-empresa">Anônimo</p>
        `

        text += `
        <label for="nome-vaga">Título da Vaga:</label>
        <p id="nome-vaga">${vagaAtual.nome}</p>
        `

        text +=`
        <label for="descricao-vaga">Descrição da Vaga:</label>
        <p id="descricao-vaga">${vagaAtual.descricao}</p>
        `

        text += `
            <div class="profile-item" id="vaga-competencias">
                <label for="competencias">Competências:</label>
                <ul id="competencias-vaga" class="skills">
        `

        vagaAtual.competencias.forEach((c: any) => {
            text += `<li class="skill" id="skill-${c}">${c}</li>`
        })

        text += `
                </ul>
            </div>
        </div>
        
    
        `


    }
    
    return text

}