import { Candidato } from "../models/Candidato"
import { Empresa } from "../models/Empresa"
import { Vaga } from "../models/Vaga"
import { listCandidatos, listEmpresas } from "../storage/lists"
import { getValidCEP, getValidCNPJ, getValidCPF, getValidDescricao, getValidDescricaoVaga, getValidEmail, getValidEstado, getValidIdade, getValidLoginIdentification, getValidNome, getValidNomeVaga, getValidPais } from "../validators/formValidator"

export function handleFormCandidato(listCompetenciasCandidato: string[]){
    const nome = getValidNome()
    const email = getValidEmail()
    const estado = getValidEstado()
    const cep = getValidCEP()
    const descricao = getValidDescricao()
    const competencias = listCompetenciasCandidato
    const idade = getValidIdade()
    const cpf = getValidCPF();
    
    if(nome && email && estado && cep && descricao && idade && cpf && competencias){
        const newCandidato = new Candidato(nome, email, estado, cep, descricao, competencias, idade, cpf)

        listCandidatos.push(newCandidato)

        localStorage.setItem("listCandidatos", JSON.stringify(listCandidatos))

        window.location.reload()
    }
    

}

export function handleFormEmpresa(listCompetenciasEmpresa: string[], listCompetenciasVaga: string[]){
    const nome = getValidNome()
    const email = getValidEmail()
    const estado = getValidEstado()
    const cep = getValidCEP()
    const descricao = getValidDescricao()
    const pais = getValidPais()
    const cnpj = getValidCNPJ()
    const competencias = listCompetenciasEmpresa

    const nomeVaga = getValidNomeVaga()
    const descricaoVaga = getValidDescricaoVaga()

    let vaga = null

    if(nomeVaga && descricaoVaga && listCompetenciasVaga){
        vaga = new Vaga(
        nomeVaga,
        descricaoVaga,
        listCompetenciasVaga
    )
    }

    if(nome && email && estado && cep && descricao && competencias && pais && cnpj && vaga){
        const newEmpresa = new Empresa(nome, email, estado, cep, descricao, competencias, pais, cnpj, vaga)

        listEmpresas.push(newEmpresa)

        localStorage.setItem("listEmpresas", JSON.stringify(listEmpresas))

        window.location.reload()
    }


}

export function handleFormLoginCandidato(){
    const cpfInput= getValidLoginIdentification("cpf-login-candidato")

    if(cpfInput){
        for (const c of listCandidatos) {
            // a lista nn possui instancias de vdd, elas são todos objetos JSON do Localstorage, 
            // logo cpf eh so um atributo do JSON

            const cpf = (c as any)?.cpf ?? ""

            if (cpf === cpfInput) {
                localStorage.setItem("currentUser", JSON.stringify(c))
                window.location.reload()
                return
            }
        }

        alert("CPF não encontrado!")
    }

    
    
}

export function handleFormLoginEmpresa(){
    const cnpjInput = getValidLoginIdentification("cnpj-login-empresa")

    if(cnpjInput){
        for (const c of listEmpresas) {
            const cnpj = (c as any)?.cnpj ?? ""

            if (cnpj === cnpjInput) {
                localStorage.setItem("currentUser", JSON.stringify(c))
                window.location.reload()
                return
            }
        }

        alert("CNPJ não encontrado!")
    }
    
}