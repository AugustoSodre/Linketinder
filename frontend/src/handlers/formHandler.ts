import { createCandidato, createEmpresa, login, toCompetenciasApi } from "../api"
import { refresh } from "../router"
import { notify } from "../helpers/notify"

import { FieldRequiredHandler } from "../validators/chain/FieldRequiredHandler"
import { RegexHandler } from "../validators/chain/RegexHandler"
import { NumberRangeHandler } from "../validators/chain/NumberRangeHandler"
import { UniqueHandler } from "../validators/chain/UniqueHandler"

export function handleFormCandidato(listCompetenciasCandidato: string[]){
    // read raw values from DOM
    const nome = (document.getElementById("nome") as HTMLInputElement)?.value ?? ''
    const email = (document.getElementById("email") as HTMLInputElement)?.value ?? ''
    const estado = (document.getElementById("estado") as HTMLInputElement)?.value ?? ''
    const cepRaw = (document.getElementById("cep") as HTMLInputElement)?.value ?? ''
    const descricao = (document.getElementById("descricao") as HTMLInputElement)?.value ?? ''
    const competencias = listCompetenciasCandidato
    const idadeRaw = (document.getElementById("idade") as HTMLInputElement)?.value ?? ''
    const cpfRaw = (document.getElementById("cpf") as HTMLInputElement)?.value ?? ''
    const senha = (document.getElementById("senha") as HTMLInputElement)?.value ?? ''

    // normalize
    const cep = cepRaw.replaceAll('-', '').trim()
    const cpf = cpfRaw.replaceAll('.', '').replaceAll('-', '').trim()
    const idade = Number(idadeRaw)

    const context: Record<string, any> = { nome, email, estado, cep, descricao, competencias, idade, cpf, senha }

    // build chain: nome -> email -> estado -> cep -> descricao -> idade -> cpf format -> cpf unique
    const nomeHandler = new FieldRequiredHandler('nome', 'Nome inválido')
    nomeHandler
        .setNext(new RegexHandler('nome', /^[a-zA-ZÀ-ÿ\s]+$/, 'Nome com caractere Inválido'))
        .setNext(new FieldRequiredHandler('email', 'Email inválido'))
        .setNext(new RegexHandler('email', /^[\w.]+@\w+\.\w+(\.\w{2,3})?$/, 'Email inválido'))
        .setNext(new RegexHandler('estado', /^[A-Z]{2}$/, 'Estado inválido'))
        .setNext(new RegexHandler('cep', /^\d{8}$/, 'CEP inválido'))
        .setNext(new FieldRequiredHandler('descricao', 'Descrição inválida'))
        .setNext(new NumberRangeHandler('idade', 0, 120, 'Idade inválida'))
        .setNext(new RegexHandler('cpf', /^\d{11}$/, 'CPF inválido'))
        .setNext(new UniqueHandler('cpf', 'candidato', 'CPF Já Cadastrado'))
        .setNext(new RegexHandler('senha', /^.{6,}$/, 'A senha deve ter pelo menos 6 caracteres'))

    nomeHandler.handle(context).then(async res => {
        if (!res.ok) {
            notify(res.message)
            return
        }

        // all good: create and persist
        try {
            await createCandidato({ nome, email, estado, cep, descricao, idade, cpf, senha, competencias: toCompetenciasApi(competencias) })
            notify("Cadastro realizado! Faça login com seu CPF e senha.", "ok")
            await refresh()
        } catch (e) {
            notify((e as Error).message)
        }
    })

}

export function handleFormEmpresa(listCompetenciasEmpresa: string[], listCompetenciasVaga: string[]){
    const nome = (document.getElementById("nome") as HTMLInputElement)?.value ?? ''
    const email = (document.getElementById("email") as HTMLInputElement)?.value ?? ''
    const estado = (document.getElementById("estado") as HTMLInputElement)?.value ?? ''
    const cepRaw = (document.getElementById("cep") as HTMLInputElement)?.value ?? ''
    const descricao = (document.getElementById("descricao") as HTMLInputElement)?.value ?? ''
    const pais = (document.getElementById("pais") as HTMLInputElement)?.value ?? ''
    const cnpjRaw = (document.getElementById("cnpj") as HTMLInputElement)?.value ?? ''
    const senha = (document.getElementById("senha") as HTMLInputElement)?.value ?? ''
    const competencias = listCompetenciasEmpresa

    const nomeVaga = (document.getElementById("nome-vaga") as HTMLInputElement)?.value ?? ''
    const descricaoVaga = (document.getElementById("descricao-vaga") as HTMLInputElement)?.value ?? ''
    const cidadeVaga = (document.getElementById("cidade-vaga") as HTMLInputElement)?.value ?? ''

    const cep = cepRaw.replaceAll('-', '').trim()
    const cnpj = cnpjRaw.replaceAll('.', '').replaceAll('-', '').replaceAll('/', '').trim()

    const context: Record<string, any> = { nome, email, estado, cep, descricao, pais, cnpj, senha, nomeVaga, descricaoVaga, cidadeVaga }

    // chain: nome -> email -> estado -> cep -> descricao -> pais -> cnpj format -> cnpj unique -> vaga fields
    const root = new FieldRequiredHandler('nome', 'Nome inválido')
    root
        .setNext(new RegexHandler('nome', /^[a-zA-ZÀ-ÿ\s]+$/, 'Nome com caractere Inválido'))
        .setNext(new FieldRequiredHandler('email', 'Email inválido'))
        .setNext(new RegexHandler('email', /^[\w.]+@\w+\.\w+(\.\w{2,3})?$/, 'Email inválido'))
        .setNext(new RegexHandler('estado', /^[A-Z]{2}$/, 'Estado inválido'))
        .setNext(new RegexHandler('cep', /^\d{8}$/, 'CEP inválido'))
        .setNext(new FieldRequiredHandler('descricao', 'Descrição inválida'))
        .setNext(new RegexHandler('pais', /^[A-Z]{2}$/, 'País inválido'))
        .setNext(new RegexHandler('cnpj', /^\d{14}$/, 'CNPJ inválido'))
        .setNext(new UniqueHandler('cnpj', 'empresa', 'CNPJ Já Cadastrado'))
        .setNext(new RegexHandler('senha', /^.{6,}$/, 'A senha deve ter pelo menos 6 caracteres'))
        .setNext(new FieldRequiredHandler('nomeVaga', 'Nome da vaga inválido'))
        .setNext(new FieldRequiredHandler('descricaoVaga', 'Descrição da vaga inválida'))
        .setNext(new FieldRequiredHandler('cidadeVaga', 'Cidade da vaga inválida'))

    root.handle(context).then(async res => {
        if (!res.ok) { notify(res.message); return }

        try {
            // A API cria a empresa e suas vagas numa única requisição
            await createEmpresa({
                nome, email, estado, cep, descricao, pais, cnpj, senha,
                competencias: toCompetenciasApi(competencias),
                vagas: [{ nome: nomeVaga, descricao: descricaoVaga, cidade: cidadeVaga, estado, competencias: toCompetenciasApi(listCompetenciasVaga) }]
            })
            notify("Cadastro realizado! Faça login com seu CNPJ e senha.", "ok")
            await refresh()
        } catch (e) {
            notify((e as Error).message)
        }
    })

}

export function handleFormLoginCandidato(){
    const cpfRaw = (document.getElementById("cpf-login-candidato") as HTMLInputElement)?.value ?? ''
    const cpf = cpfRaw.replaceAll('.', '').replaceAll('-', '').trim()
    const senha = (document.getElementById("senha-login") as HTMLInputElement)?.value ?? ''
    const context: Record<string, any> = { cpf, senha }

    const root = new RegexHandler('cpf', /^\d{11}$/, 'CPF inválido')
    root.setNext(new FieldRequiredHandler('senha', 'Informe a senha'))

    root.handle(context).then(async res => {
        if (!res.ok) { notify(res.message); return }
        try {
            await login({ cpf, senha })
            await refresh()
        } catch (e) {
            // 401 = senha errada; outros (ex.: 429 muitas tentativas) mostram a mensagem da API
            notify((e as any).status === 401 ? 'CPF ou senha inválidos' : (e as Error).message)
        }
    })

}


export function handleFormLoginEmpresa(){
    const cnpjRaw = (document.getElementById("cnpj-login-empresa") as HTMLInputElement)?.value ?? ''
    const cnpj = cnpjRaw.replaceAll('.', '').replaceAll('-', '').replaceAll('/', '').trim()
    const senha = (document.getElementById("senha-login") as HTMLInputElement)?.value ?? ''
    const context: Record<string, any> = { cnpj, senha }

    const root = new RegexHandler('cnpj', /^\d{14}$/, 'CNPJ inválido')
    root.setNext(new FieldRequiredHandler('senha', 'Informe a senha'))

    root.handle(context).then(async res => {
        if (!res.ok) { notify(res.message); return }
        try {
            await login({ cnpj, senha })
            await refresh()
        } catch (e) {
            // 401 = senha errada; outros (ex.: 429 muitas tentativas) mostram a mensagem da API
            notify((e as any).status === 401 ? 'CNPJ ou senha inválidos' : (e as Error).message)
        }
    })

}