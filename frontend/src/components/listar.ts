import { escapeHtml } from "../helpers/escapeHtml"
import { generateCompetenciasList } from "../helpers/homeHelper"
import { listCandidatos, listEmpresas } from "../storage/lists"

export function listarVagas(currentUser: any): string{
    let text: string = ""

    for(const empresaAtual of listEmpresas){
        for(const vagaAtual of empresaAtual.vagas){

            text += `<div class="vaga-div" id="vaga-empresa">`
    
            text += `
            <label for="nome-empresa">Nome da Empresa:</label>
            <p id="nome-empresa">Anônimo</p>
            `
    
            text += `
            <label for="nome-vaga">Título da Vaga:</label>
            <p id="nome-vaga">${escapeHtml(vagaAtual.nome)}</p>
            `
    
            text +=`
            <label for="descricao-vaga">Descrição da Vaga:</label>
            <p id="descricao-vaga">${escapeHtml(vagaAtual.descricao)}</p>
            `

            text += `
            <label for="afinidade-vaga">Afinidade com a Empresa:</label>
            <p id="afinidade-vaga">${calcAfinidade(currentUser, empresaAtual)}</p>
            `
    
            text += `
                <div class="profile-item" id="vaga-competencias">
                    <label for="competencias">Competências:</label>
                    <ul id="competencias-vaga" class="skills">
            `
    
            vagaAtual.competencias.forEach((c: any) => {
                text += `<li class="skill" id="skill-${escapeHtml(c)}">${escapeHtml(c)}</li>`
            })
    
            text += `
                    </ul>
                </div>
            </div>
            
        
            `
        }
    }

    return text
}

export function listarCandidatos(currentUser: any): string{
    let text = ""

    for(const c of listCandidatos){
        const candidato = (c as any)
        text += `
        <div class="candidato-div">
            <label for="nome">Nome:</label>
            <p id="nome-candidato">Anonimo</p>

            <label for="descricao">Descrição:</label>
            <p id="descricao-candidato">${escapeHtml(candidato.descricao)}</p>

            <div class="profile-item" id="candidato-competencias">
                <label for="competencias">Competências:</label>
                <ul id="competencias-candidato" class="skills">
            `
        text+= generateCompetenciasList(candidato)

        text +=
                `
                </ul>
            </div>

            <label for="afinidade">Afinidade com Candidato:</label>
            <p id="afinidade-candidato">${calcAfinidade(candidato, currentUser)}</p>

        </div>
        `

    }

    return text
}

function calcAfinidade(objBase: any, objAlvo: any){
    let competenciasBase: string[] = objBase.competencias
    let competenciasAlvo: string[] = objAlvo.competencias

    let numTotalCompetencias = competenciasAlvo.length
    let numCompetenciasComum = 0

    // Evita "NaN%" quando o alvo não tem competências
    if(numTotalCompetencias === 0) return "0.00%"

    competenciasBase.forEach((c) => {
        if(competenciasAlvo.includes(c)){
            numCompetenciasComum += 1
        }
    })
    
    let afinidade: string = ((numCompetenciasComum / numTotalCompetencias * 100)).toFixed(2).toString()
    afinidade += "%"
    console.log(afinidade)

    return afinidade



}