import { handleFormCandidato, handleFormLoginCandidato, handleFormLoginEmpresa } from "../handlers/formHandler";
import { handleFormEmpresa } from "../handlers/formHandler";

export let competenciasSelecionadas: string[] = []
export let competenciasSelecionadasVaga: string[] = []

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

            handleFormEmpresa(competenciasSelecionadas, competenciasSelecionadasVaga);
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

    let competencias = document.querySelectorAll<HTMLInputElement>(".competencia-empresa")

    if(competencias.length == 0){
        competencias = document.querySelectorAll<HTMLInputElement>(".competencia-candidato")
    }

    Array.from(competencias).forEach((c) => {
        c.addEventListener("change", function (){
            console.log("Clicou na vaga")
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

    const competenciasVaga = document.querySelectorAll<HTMLInputElement>(".competencia-vaga")

    if(competenciasVaga){
        Array.from(competenciasVaga).forEach((c) => {
        c.addEventListener("change", function (){

            if (c.checked) {
                if (!competenciasSelecionadasVaga.includes(c.value)) {
                    competenciasSelecionadasVaga.push(c.value)
                }
            } else {
                const compIndex = competenciasSelecionadas.indexOf(c.value)
                if (competenciasSelecionadasVaga.includes(c.value)) {
                    competenciasSelecionadasVaga.splice(compIndex, 1)
                }
            }

        })
    })
    }
    

}

export function generateCompetenciasText(sufixo: string): string{
    const competencias: string[] = [
        "Angular",
        "React",
        "SpringBoot",
        "Django",
        "Linux",
        "DevOps",
        "PowerBI"
    ]

    let text:string = ""

    competencias.forEach((competencia) => {
        text += `
        <div id="competencia-div">
            <label for="${competencia}">${competencia}</label>
            <input type="checkbox" id="${competencia}" name="${competencia}" value="${competencia}" class="competencia-${sufixo}">
          </div>
        `
    })

    return text
}