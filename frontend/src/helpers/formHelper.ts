import { handleFormCandidato, handleFormLoginCandidato, handleFormLoginEmpresa } from "../handlers/formHandler";
import { handleFormEmpresa } from "../handlers/formHandler";

export let competenciasSelecionadas: string[] = []

export function createFormListeners(){
    const formCadastroCandidato = document.getElementById("form-cadastro-candidato")
    const formCadastroEmpresa   = document.getElementById("form-cadastro-empresa")
    const formLoginCandidato    = document.getElementById("form-login-candidato")
    const formLoginEmpresa      = document.getElementById("form-login-empresa")

    if(formCadastroCandidato){

        formCadastroCandidato.addEventListener("submit", (e) => {
            e.preventDefault()

            handleFormCandidato(competenciasSelecionadas);
        })
        
    } else if(formCadastroEmpresa){

        formCadastroEmpresa.addEventListener("submit", (e) => {
            e.preventDefault()

            handleFormEmpresa(competenciasSelecionadas);
        })

    } else if(formLoginCandidato){
        
        formLoginCandidato.addEventListener("submit", (e) => {
            e.preventDefault()

            handleFormLoginCandidato()
        })

    } else if(formLoginEmpresa){
        
        formLoginEmpresa.addEventListener("submit", (e) => {
            e.preventDefault()

            handleFormLoginEmpresa()
        })

    }

    const competencias = document.querySelectorAll<HTMLInputElement>(".competencia")

    Array.from(competencias).forEach((c) => {
        c.addEventListener("change", function (){

            if (c.checked) {
                if (!competenciasSelecionadas.includes(c.value)) {
                    competenciasSelecionadas.push(c.value)
                }
            } else {
                const compIndex = competenciasSelecionadas.indexOf(c.value)
                if (competenciasSelecionadas.includes(c.value)) {
                    competenciasSelecionadas.splice(compIndex, 1)
                }
            }

        })
    })

}

export function generateCompetenciasText(): string{
    const competencias: string[] = [
        "Angular",
        "React",
        "SpringBoot",
        "Django",
        "Linux"
    ]

    let text:string = ""

    competencias.forEach((competencia) => {
        text += `
        <div id="competencia-div">
            <label for="${competencia}">${competencia}</label>
            <input type="checkbox" id="${competencia}" name="${competencia}" value="${competencia}" class="competencia">
          </div>
        `
    })

    return text
}