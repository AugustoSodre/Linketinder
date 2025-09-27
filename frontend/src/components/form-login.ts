import { createFormListeners } from "../helpers/formHelper"
import { renderMenu } from "../pages/menu"

export function renderFormLogin(app: HTMLDivElement , tipo: string){
    if(tipo == "Candidato"){
        renderLoginCandidatoHTML(app)

    } else if(tipo == "Empresa"){
        renderLoginEmpresaHTML(app)

    } else{
        renderMenu(app)

    }
}

function renderLoginCandidatoHTML(app: HTMLDivElement){
    let text: string = `
    <h2>Login Candidato</h2>
    <form action="" id="form-login-candidato" class="form-login">
        <label for="cpf">CPF:</label>
        <input type="text" id="cpf-login-candidato">
        <button type="submit">Login</button>
    </form>
    `

    app.innerHTML = text

    createFormListeners()

}

function renderLoginEmpresaHTML(app: HTMLDivElement){
    let text: string = `
    <h2>Login Empresa</h2>
    <form action="" id="form-login-empresa" class="form-login">
        <label for="cnpj">CNPJ:</label>
        <input type="text" id="cnpj-login-empresa">
        <button type="submit">Login</button>
    </form>
    `

    app.innerHTML = text

    createFormListeners()

}