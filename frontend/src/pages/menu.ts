
import { renderLogin } from "./login"
import { renderCadastro } from "./cadastro"

export function renderMenu(app: HTMLDivElement){
    app.innerHTML = `
        <div id="menu-btn-container">
            <button class="btn-menu" id="btn-cadastrar-candidato">Cadastrar Candidato</button>
            <button class="btn-menu" id="btn-cadastrar-empresa">Cadastrar Empresa</button>
            <button class="btn-menu" id="btn-login-candidato">Login Candidato</button>
            <button class="btn-menu" id="btn-login-empresa">Login Empresa</button>
        </div>
    `

    document.getElementById("btn-cadastrar-candidato")?.addEventListener("click",
        () => renderCadastro(app, "Candidato")
    )

    document.getElementById("btn-cadastrar-empresa")?.addEventListener("click",
        () => renderCadastro(app, "Empresa")
    )

    document.getElementById("btn-login-candidato")?.addEventListener("click",
        () => renderLogin(app, "Candidato")
    )

    document.getElementById("btn-login-empresa")?.addEventListener("click",
        () => renderLogin(app, "Empresa")
    )
}
