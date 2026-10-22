export type Vaga = {
    id: number
    id_empresa: number
    nome: string
    descricao: string
    cidade: string | null
    estado: string | null
    competencias: string[]
}

type Pessoa = {
    id: number
    nome: string
    email: string
    estado: string
    cep: string
    descricao: string
    competencias: string[]
}

export type Candidato = Pessoa & { cpf: string, idade: number }
export type Empresa = Pessoa & { cnpj: string, pais: string, vagas: Vaga[] }

export type UsuarioLogado = (Candidato & { tipo: "candidato" }) | (Empresa & { tipo: "empresa" })
