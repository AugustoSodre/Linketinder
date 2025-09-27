import { handleFormCandidato } from "../handlers/formHandler";
import { handleFormEmpresa } from "../handlers/formHandler";

export let competenciasSelecionadas: string[] = []

export function createListeners(){
    const forms = document.getElementsByClassName("form-cadastro");
    
    Array.from(forms).forEach(form => {
        form.addEventListener("submit", (e) => {
            e.preventDefault();
            
            if (form.id === "form-cadastro-candidato") {
                handleFormCandidato();
            } else if (form.id === "form-cadastro-empresa") {
                handleFormEmpresa(competenciasSelecionadas);
            }
        });
    });

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