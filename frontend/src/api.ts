import { listCandidatos, listEmpresas, listCompetenciasDisponiveis } from "./storage/lists"
import { notify } from "./helpers/notify"
import { refresh } from "./router"

const API_URL = import.meta.env.VITE_API_URL ?? "http://localhost:8080"

export type Session = { token: string, tipo: "candidato" | "empresa", id: number }

// Competências chegam da API como {id, nome}; o resto do frontend trabalha só com os nomes
let competenciasApi: { id: number, nome: string }[] = []

export function getSession(): Session | null {
    const raw = sessionStorage.getItem("session")
    return raw ? JSON.parse(raw) : null
}

export function clearSession(){
    sessionStorage.removeItem("session")
}

async function request(path: string, method: string = "GET", body?: unknown): Promise<any> {
    const headers: Record<string, string> = { "Content-Type": "application/json" }
    const session = getSession()
    if (session) headers["Authorization"] = `Bearer ${session.token}`

    const res = await fetch(API_URL + path, { method, headers, body: body ? JSON.stringify(body) : undefined })
    const data = await res.json().catch(() => null)
    // Token vencido (ou servidor reiniciado): sai da conta em vez de deixar a tela num estado logado falso
    if (res.status === 401 && session) {
        clearSession()
        notify("Sua sessão expirou. Faça login novamente.")
        refresh()
        return new Promise(() => {}) // a tela volta ao menu; não deixa quem chamou mostrar outra mensagem
    }
    if (!res.ok) throw Object.assign(new Error(data?.error ?? `Erro ${res.status}`), { status: res.status })
    return data
}

const nomes = (competencias: { nome: string }[] = []) => competencias.map(c => c.nome)

export function toCompetenciasApi(nomesSelecionados: string[]){
    return competenciasApi.filter(c => nomesSelecionados.includes(c.nome))
}

export async function loadData(){
    const [candidatos, empresas, competencias] = await Promise.all([
        request("/candidatos"), request("/empresas"), request("/competencias")
    ])

    competenciasApi = competencias
    listCompetenciasDisponiveis.splice(0, listCompetenciasDisponiveis.length, ...nomes(competencias))

    listCandidatos.splice(0, listCandidatos.length,
        ...candidatos.map((c: any) => ({ ...c, competencias: nomes(c.competencias) })))

    listEmpresas.splice(0, listEmpresas.length,
        ...empresas.map((e: any) => ({
            ...e,
            competencias: nomes(e.competencias),
            vagas: e.vagas.map((v: any) => ({ ...v, competencias: nomes(v.competencias) }))
        })))
}

export async function login(credenciais: { cpf?: string, cnpj?: string, senha: string }){
    const session: Session = await request("/login", "POST", credenciais)
    sessionStorage.setItem("session", JSON.stringify(session))
}

export const createCandidato = (candidato: unknown) => request("/candidatos", "POST", candidato)
export const createEmpresa = (empresa: unknown) => request("/empresas", "POST", empresa)

export function deleteCurrentUser(session: Session){
    return request(`/${session.tipo}s/${session.id}`, "DELETE")
}

export function updateCurrentUser(session: Session, campos: Record<string, unknown>){
    return request(`/${session.tipo}s/${session.id}`, "PUT", campos)
}

export const createVaga = (vaga: unknown) => request("/vagas", "POST", vaga)
export const updateVaga = (id: number, campos: Record<string, unknown>) => request(`/vagas/${id}`, "PUT", campos)
export const deleteVaga = (id: number) => request(`/vagas/${id}`, "DELETE")
