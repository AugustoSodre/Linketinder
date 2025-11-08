import { Candidato } from "../models/Candidato"
import { Empresa } from "../models/Empresa"
import { Vaga } from "../models/Vaga"
import { listCandidatos, listEmpresas } from "../storage/lists"

import { FieldRequiredHandler } from "../validators/chain/FieldRequiredHandler"
import { RegexHandler } from "../validators/chain/RegexHandler"
import { NumberRangeHandler } from "../validators/chain/NumberRangeHandler"
import { UniqueHandler } from "../validators/chain/UniqueHandler"
import { ExistenceHandler } from "../validators/chain/ExistenceHandler"

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

    // normalize
    const cep = cepRaw.replaceAll('-', '').trim()
    const cpf = cpfRaw.replaceAll('.', '').replaceAll('-', '').trim()
    const idade = Number(idadeRaw)

    const context: Record<string, any> = { nome, email, estado, cep, descricao, competencias, idade, cpf }

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

    nomeHandler.handle(context).then(res => {
        if (!res.ok) {
            alert(res.message)
            return
        }

        // all good: create and persist
        const newCandidato = new Candidato(nome, email, estado, cep, descricao, competencias, idade, cpf)
        listCandidatos.push(newCandidato)
        localStorage.setItem("listCandidatos", JSON.stringify(listCandidatos))
        window.location.reload()
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
    const competencias = listCompetenciasEmpresa

    const nomeVaga = (document.getElementById("nome-vaga") as HTMLInputElement)?.value ?? ''
    const descricaoVaga = (document.getElementById("descricao-vaga") as HTMLInputElement)?.value ?? ''

    const cep = cepRaw.replaceAll('-', '').trim()
    const cnpj = cnpjRaw.replaceAll('.', '').replaceAll('-', '').replaceAll('/', '').trim()

    const context: Record<string, any> = { nome, email, estado, cep, descricao, pais, cnpj, nomeVaga, descricaoVaga }

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
        .setNext(new FieldRequiredHandler('nomeVaga', 'Nome da vaga inválido'))
        .setNext(new FieldRequiredHandler('descricaoVaga', 'Descrição da vaga inválida'))

    root.handle(context).then(res => {
        if (!res.ok) { alert(res.message); return }

        const vaga = new Vaga(nomeVaga, descricaoVaga, listCompetenciasVaga)
        const newEmpresa = new Empresa(nome, email, estado, cep, descricao, competencias, pais, cnpj, vaga)

        listEmpresas.push(newEmpresa)
        localStorage.setItem("listEmpresas", JSON.stringify(listEmpresas))
        window.location.reload()
    })

}

export function handleFormLoginCandidato(){
    const cpfRaw = (document.getElementById("cpf-login-candidato") as HTMLInputElement)?.value ?? ''
    const cpf = cpfRaw.replaceAll('.', '').replaceAll('-', '').trim()
    const context: Record<string, any> = { cpf }

    const root = new RegexHandler('cpf', /^\d{11}$/, 'CPF inválido')
    root.setNext(new ExistenceHandler('cpf', 'candidato', 'CPF não encontrado'))

    root.handle(context).then(res => {
        if (!res.ok) { alert(res.message); return }
        // success: context.currentUser populated by ExistenceHandler
        localStorage.setItem('currentUser', JSON.stringify(context.currentUser))
        window.location.reload()
    })

}


export function handleFormLoginEmpresa(){
    const cnpjRaw = (document.getElementById("cnpj-login-empresa") as HTMLInputElement)?.value ?? ''
    const cnpj = cnpjRaw.replaceAll('.', '').replaceAll('-', '').replaceAll('/', '').trim()
    const context: Record<string, any> = { cnpj }

    const root = new RegexHandler('cnpj', /^\d{14}$/, 'CNPJ inválido')
    root.setNext(new ExistenceHandler('cnpj', 'empresa', 'CNPJ não encontrado'))

    root.handle(context).then(res => {
        if (!res.ok) { alert(res.message); return }
        localStorage.setItem('currentUser', JSON.stringify(context.currentUser))
        window.location.reload()
    })

}