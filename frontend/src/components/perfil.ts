import { escapeHtml } from "../helpers/escapeHtml"
import { generateCompetenciasText } from "../helpers/formHelper"
import { notify } from "../helpers/notify"
import { createCompetencia, createVaga, deleteVaga, getSession, toCompetenciasApi, updateCurrentUser, updateVaga } from "../api"
import { refresh } from "../router"
import { marcadas, semPontuacao, valor } from "../helpers/dom"
import { FieldRequiredHandler } from "../validators/chain/FieldRequiredHandler"
import { encadear, regra, regraSenha, regrasPessoa } from "../validators/regras"
import type { Empresa, UsuarioLogado, Vaga } from "../models/types"

const input = (id: string) => document.getElementById(id) as HTMLInputElement

function campo(id: string, label: string, value: unknown, type: string = "text"){
    return `
        <div class="form-item">
            <label for="${id}">${label}</label>
            <input type="${type}" id="${id}" value="${escapeHtml(value)}">
        </div>`
}

export function renderEditarPerfilHTML(currentUser: UsuarioLogado): string{
    const empresa = currentUser.tipo === "empresa"
    return `
    <details class="perfil-section" id="editar-perfil">
        <summary>Editar perfil</summary>
        <form id="form-editar-perfil">
            ${campo("perfil-nome", "Nome:", currentUser.nome)}
            ${campo("perfil-email", "Email:", currentUser.email, "email")}
            ${campo("perfil-estado", "Estado (UF):", currentUser.estado)}
            ${campo("perfil-cep", "CEP:", currentUser.cep)}
            ${empresa ? campo("perfil-pais", "País:", currentUser.pais) : campo("perfil-idade", "Idade:", currentUser.idade, "number")}
            <div class="form-item">
                <label for="perfil-descricao">Descrição:</label>
                <textarea id="perfil-descricao" rows="3">${escapeHtml(currentUser.descricao)}</textarea>
            </div>
            <div class="form-item">
                <label for="perfil-senha">Nova senha (deixe em branco para manter):</label>
                <input type="password" id="perfil-senha" minlength="6" autocomplete="new-password">
            </div>
            <div class="form-item">
                <label>Competências:</label>
                ${generateCompetenciasText("perfil", currentUser.competencias)}
            </div>
            <button type="submit" class="btn" id="perfil-submit">Salvar alterações</button>
        </form>
    </details>`
}

export function renderNovaCompetenciaHTML(): string{
    return `
    <details class="perfil-section" id="nova-competencia">
        <summary>Nova competência</summary>
        <form id="form-nova-competencia">
            ${campo("nova-competencia-nome", "Nome da competência:", "")}
            <button type="submit" class="btn" id="nova-competencia-submit">Adicionar competência</button>
        </form>
    </details>`
}

export function createNovaCompetenciaListener(){
    document.getElementById("form-nova-competencia")?.addEventListener("submit", async (e) => {
        e.preventDefault()
        const res = await regra("nome").handle({ nome: valor("nova-competencia-nome") })
        if (!res.ok) { notify(res.message); return }

        try {
            await createCompetencia(valor("nova-competencia-nome"))
            notify("Competência criada.", "ok")
            await refresh()
        } catch (err) {
            notify((err as Error).message)
        }
    })
}

export function renderMinhasVagasHTML(currentUser: Empresa): string{
    let text = `
    <section class="perfil-section" id="minhas-vagas">
        <h2>Minhas vagas</h2>`

    currentUser.vagas.forEach((v: Vaga) => {
        text += `
        <div class="vaga-div minha-vaga">
            <label>Título:</label><p class="minha-vaga-nome">${escapeHtml(v.nome)}</p>
            <label>Local:</label><p>${escapeHtml(v.cidade ?? "-")} / ${escapeHtml(v.estado ?? "-")}</p>
            <label>Descrição:</label><p>${escapeHtml(v.descricao)}</p>
            <ul class="skills">${v.competencias.map(c => `<li class="skill">${escapeHtml(c)}</li>`).join("")}</ul>
            <div class="profile-actions">
                <button class="btn btn-editar-vaga" data-id="${v.id}">Editar</button>
                <button class="btn btn-danger btn-excluir-vaga" data-id="${v.id}">Excluir</button>
            </div>
        </div>`
    })

    text += `
        <details id="vaga-form-container">
            <summary id="vaga-form-titulo">Nova vaga</summary>
            <form id="form-vaga">
                <input type="hidden" id="vaga-id">
                ${campo("vaga-nome", "Título da vaga:", "")}
                ${campo("vaga-descricao", "Descrição:", "")}
                ${campo("vaga-cidade", "Cidade:", "")}
                ${campo("vaga-estado", "Estado (UF):", currentUser.estado)}
                <div class="form-item">
                    <label>Competências da vaga:</label>
                    ${generateCompetenciasText("nova-vaga")}
                </div>
                <button type="submit" class="btn" id="vaga-submit">Criar vaga</button>
            </form>
        </details>
    </section>`

    return text
}

export function createPerfilListeners(currentUser: UsuarioLogado){
    document.getElementById("form-editar-perfil")?.addEventListener("submit", async (e) => {
        e.preventDefault()
        const empresa = currentUser.tipo === "empresa"
        const campos: Record<string, any> = {
            nome: valor("perfil-nome"),
            email: valor("perfil-email"),
            estado: valor("perfil-estado"),
            cep: semPontuacao(valor("perfil-cep")),
            descricao: valor("perfil-descricao"),
        }
        if (empresa) campos.pais = valor("perfil-pais")
        else campos.idade = Number(valor("perfil-idade"))
        const senha = input("perfil-senha").value
        if (senha) campos.senha = senha
        campos.competencias = toCompetenciasApi(marcadas("competencia-perfil"))

        const res = await encadear([
            ...regrasPessoa(),
            regra(empresa ? "pais" : "idade"),
            regraSenha(true),
        ]).handle({ ...campos, senha })
        if (!res.ok) { notify(res.message); return }

        try {
            await updateCurrentUser(getSession()!, campos)
            notify("Perfil atualizado.", "ok")
            await refresh()
        } catch (err) {
            notify((err as Error).message)
        }
    })
}

export function createVagasListeners(currentUser: Empresa){
    const form = document.getElementById("form-vaga")
    if (!form) return

    document.querySelectorAll<HTMLButtonElement>(".btn-editar-vaga").forEach(btn => {
        btn.addEventListener("click", () => {
            const vaga = currentUser.vagas.find(v => v.id === Number(btn.dataset.id))!
            input("vaga-id").value = String(vaga.id)
            input("vaga-nome").value = vaga.nome
            input("vaga-descricao").value = vaga.descricao
            input("vaga-cidade").value = vaga.cidade ?? ""
            input("vaga-estado").value = vaga.estado ?? ""
            document.querySelectorAll<HTMLInputElement>(".competencia-nova-vaga").forEach(c => c.checked = vaga.competencias.includes(c.value))
            document.getElementById("vaga-form-titulo")!.textContent = "Editar vaga"
            document.getElementById("vaga-submit")!.textContent = "Salvar vaga"
            ;(document.getElementById("vaga-form-container") as HTMLDetailsElement).open = true
        })
    })

    document.querySelectorAll<HTMLButtonElement>(".btn-excluir-vaga").forEach(btn => {
        btn.addEventListener("click", async () => {
            if (!confirm("Excluir esta vaga?")) return
            try {
                await deleteVaga(Number(btn.dataset.id))
                notify("Vaga excluída.", "ok")
                await refresh()
            } catch (err) {
                notify((err as Error).message)
            }
        })
    })

    form.addEventListener("submit", async (e) => {
        e.preventDefault()
        const id = valor("vaga-id")
        const campos = { nome: valor("vaga-nome"), descricao: valor("vaga-descricao"), cidade: valor("vaga-cidade"), estado: valor("vaga-estado") }
        const competencias = toCompetenciasApi(marcadas("competencia-nova-vaga"))

        const res = await encadear([
            regra("nome"),
            regra("descricao"),
            new FieldRequiredHandler("cidade", "Cidade da vaga inválida"),
            regra("estado"),
        ]).handle(campos)
        if (!res.ok) { notify(res.message); return }

        try {
            if (id) {
                await updateVaga(Number(id), { ...campos, competencias })
            } else {
                await createVaga({ ...campos, id_empresa: currentUser.id, competencias })
            }
            notify(id ? "Vaga atualizada." : "Vaga criada.", "ok")
            await refresh()
        } catch (err) {
            notify((err as Error).message)
        }
    })
}
