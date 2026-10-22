import { escapeHtml } from "../helpers/escapeHtml"
import { generateCompetenciasList } from "../helpers/homeHelper"
import { listCandidatos, listEmpresas } from "../storage/lists"

type ComCompetencias = { competencias: string[] }

export function listarVagas(currentUser: ComCompetencias): string{
    return listEmpresas.flatMap(empresa => empresa.vagas.map(vaga => `
        <div class="vaga-div">
            <label>Nome da Empresa:</label>
            <p id="nome-empresa">Anônimo</p>

            <label>Título da Vaga:</label>
            <p id="nome-vaga">${escapeHtml(vaga.nome)}</p>

            <label>Descrição da Vaga:</label>
            <p id="descricao-vaga">${escapeHtml(vaga.descricao)}</p>

            <label>Afinidade com a Empresa:</label>
            <p id="afinidade-vaga">${calcAfinidade(currentUser, empresa)}</p>

            <div class="profile-item" id="vaga-competencias">
                <label>Competências:</label>
                ${generateCompetenciasList(vaga)}
            </div>
        </div>`)).join("")
}

export function listarCandidatos(currentUser: ComCompetencias): string{
    return listCandidatos.map(candidato => `
        <div class="candidato-div">
            <label>Nome:</label>
            <p id="nome-candidato">Anônimo</p>

            <label>Descrição:</label>
            <p id="descricao-candidato">${escapeHtml(candidato.descricao)}</p>

            <div class="profile-item" id="candidato-competencias">
                <label>Competências:</label>
                ${generateCompetenciasList(candidato)}
            </div>

            <label>Afinidade com Candidato:</label>
            <p id="afinidade-candidato">${calcAfinidade(candidato, currentUser)}</p>
        </div>`).join("")
}

function calcAfinidade(base: ComCompetencias, alvo: ComCompetencias): string{
    if (alvo.competencias.length === 0) return "0.00%"
    const emComum = base.competencias.filter(c => alvo.competencias.includes(c)).length
    return `${(emComum / alvo.competencias.length * 100).toFixed(2)}%`
}
