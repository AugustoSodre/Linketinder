import { escapeHtml } from "../helpers/escapeHtml"
import { generateCompetenciasText } from "../helpers/formHelper"
import { notify } from "../helpers/notify"
import { createVaga, deleteVaga, getSession, toCompetenciasApi, updateCurrentUser, updateVaga } from "../api"
import { refresh } from "../router"
import { FieldRequiredHandler } from "../validators/chain/FieldRequiredHandler"
import { RegexHandler } from "../validators/chain/RegexHandler"
import { NumberRangeHandler } from "../validators/chain/NumberRangeHandler"
import type { Vaga } from "../models/types"

const input = (id: string) => document.getElementById(id) as HTMLInputElement
const valor = (id: string) => input(id)?.value.trim() ?? ""

function campo(id: string, label: string, value: unknown, type: string = "text"){
    return `
        <div class="form-item">
            <label for="${id}">${label}</label>
            <input type="${type}" id="${id}" value="${escapeHtml(value)}">
        </div>`
}

export function renderEditarPerfilHTML(currentUser: any): string{
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
            <button type="submit" class="btn" id="perfil-submit">Salvar alterações</button>
        </form>
    </details>`
}

export function renderMinhasVagasHTML(currentUser: any): string{
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
                <div class="form-item" id="vaga-competencias-form">
                    <label>Competências da vaga:</label>
                    ${generateCompetenciasText("nova-vaga")}
                </div>
                <p id="vaga-competencias-aviso" hidden>As competências são definidas só na criação da vaga.</p>
                <button type="submit" class="btn" id="vaga-submit">Criar vaga</button>
            </form>
        </details>
    </section>`

    return text
}

export function createPerfilListeners(currentUser: any){
    document.getElementById("form-editar-perfil")?.addEventListener("submit", async (e) => {
        e.preventDefault()
        const empresa = currentUser.tipo === "empresa"
        const campos: Record<string, any> = {
            nome: valor("perfil-nome"),
            email: valor("perfil-email"),
            estado: valor("perfil-estado"),
            cep: valor("perfil-cep").replaceAll("-", ""),
            descricao: valor("perfil-descricao"),
        }
        if (empresa) campos.pais = valor("perfil-pais")
        else campos.idade = Number(valor("perfil-idade"))
        const senha = input("perfil-senha").value
        if (senha) campos.senha = senha

        const root = new FieldRequiredHandler("nome", "Nome inválido")
        root.setNext(new RegexHandler("nome", /^[a-zA-ZÀ-ÿ\s]+$/, "Nome com caractere inválido"))
            .setNext(new RegexHandler("email", /^[\w.]+@\w+\.\w+(\.\w{2,3})?$/, "Email inválido"))
            .setNext(new RegexHandler("estado", /^[A-Z]{2}$/, "Estado inválido"))
            .setNext(new RegexHandler("cep", /^\d{8}$/, "CEP inválido"))
            .setNext(new FieldRequiredHandler("descricao", "Descrição inválida"))
            .setNext(empresa
                ? new RegexHandler("pais", /^[A-Z]{2}$/, "País inválido")
                : new NumberRangeHandler("idade", 0, 120, "Idade inválida"))
            .setNext(new RegexHandler("senha", /^(.{6,})?$/, "A senha deve ter pelo menos 6 caracteres"))

        const res = await root.handle({ ...campos, senha })
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

export function createVagasListeners(currentUser: any){
    const form = document.getElementById("form-vaga")
    if (!form) return

    document.querySelectorAll<HTMLButtonElement>(".btn-editar-vaga").forEach(btn => {
        btn.addEventListener("click", () => {
            const vaga: Vaga = currentUser.vagas.find((v: Vaga) => v.id === Number(btn.dataset.id))
            input("vaga-id").value = String(vaga.id)
            input("vaga-nome").value = vaga.nome
            input("vaga-descricao").value = vaga.descricao
            input("vaga-cidade").value = vaga.cidade ?? ""
            input("vaga-estado").value = vaga.estado ?? ""
            document.getElementById("vaga-competencias-form")!.hidden = true
            document.getElementById("vaga-competencias-aviso")!.hidden = false
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

        const root = new FieldRequiredHandler("nome", "Título da vaga inválido")
        root.setNext(new FieldRequiredHandler("descricao", "Descrição da vaga inválida"))
            .setNext(new FieldRequiredHandler("cidade", "Cidade da vaga inválida"))
            .setNext(new RegexHandler("estado", /^[A-Z]{2}$/, "Estado inválido"))
        const res = await root.handle(campos)
        if (!res.ok) { notify(res.message); return }

        try {
            if (id) {
                await updateVaga(Number(id), campos)
            } else {
                const nomes = Array.from(document.querySelectorAll<HTMLInputElement>(".competencia-nova-vaga:checked")).map(c => c.value)
                await createVaga({ ...campos, id_empresa: currentUser.id, competencias: toCompetenciasApi(nomes) })
            }
            notify(id ? "Vaga atualizada." : "Vaga criada.", "ok")
            await refresh()
        } catch (err) {
            notify((err as Error).message)
        }
    })
}
