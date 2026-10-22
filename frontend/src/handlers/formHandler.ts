import { createCandidato, createEmpresa, login, toCompetenciasApi } from "../api"
import { refresh } from "../router"
import { notify } from "../helpers/notify"
import { marcadas, semPontuacao, valor } from "../helpers/dom"
import type { BaseHandler } from "../validators/chain/BaseHandler"
import { FieldRequiredHandler } from "../validators/chain/FieldRequiredHandler"
import { UniqueHandler } from "../validators/chain/UniqueHandler"
import { encadear, regra, regraSenha, regrasPessoa } from "../validators/regras"

async function validarEEnviar(regras: BaseHandler[], context: Record<string, any>, enviar: () => Promise<unknown>, sucesso: string){
    const res = await encadear(regras).handle(context)
    if (!res.ok) { notify(res.message); return }

    try {
        await enviar()
        notify(sucesso, "ok")
        await refresh()
    } catch (e) {
        notify((e as Error).message)
    }
}

function dadosPessoa(){
    return {
        nome: valor("nome"),
        email: valor("email"),
        estado: valor("estado"),
        cep: semPontuacao(valor("cep")),
        descricao: valor("descricao"),
        senha: (document.getElementById("senha") as HTMLInputElement).value,
    }
}

export function handleFormCandidato(){
    const candidato = { ...dadosPessoa(), idade: Number(valor("idade")), cpf: semPontuacao(valor("cpf")) }
    const competencias = marcadas("competencia-candidato")

    validarEEnviar([
            ...regrasPessoa(),
            regra("idade"),
            regra("cpf"),
            new UniqueHandler("cpf", "candidato", "CPF Já Cadastrado"),
            regraSenha(),
        ],
        candidato,
        () => createCandidato({ ...candidato, competencias: toCompetenciasApi(competencias) }),
        "Cadastro realizado! Faça login com seu CPF e senha.")
}

export function handleFormEmpresa(){
    const empresa = { ...dadosPessoa(), pais: valor("pais"), cnpj: semPontuacao(valor("cnpj")) }
    const vaga = { nome: valor("nome-vaga"), descricao: valor("descricao-vaga"), cidade: valor("cidade-vaga"), estado: empresa.estado }

    validarEEnviar([
            ...regrasPessoa(),
            regra("pais"),
            regra("cnpj"),
            new UniqueHandler("cnpj", "empresa", "CNPJ Já Cadastrado"),
            regraSenha(),
            new FieldRequiredHandler("nomeVaga", "Nome da vaga inválido"),
            new FieldRequiredHandler("descricaoVaga", "Descrição da vaga inválida"),
            new FieldRequiredHandler("cidadeVaga", "Cidade da vaga inválida"),
        ],
        { ...empresa, nomeVaga: vaga.nome, descricaoVaga: vaga.descricao, cidadeVaga: vaga.cidade },
        () => createEmpresa({
            ...empresa,
            competencias: toCompetenciasApi(marcadas("competencia-empresa")),
            vagas: [{ ...vaga, competencias: toCompetenciasApi(marcadas("competencia-vaga")) }]
        }),
        "Cadastro realizado! Faça login com seu CNPJ e senha.")
}

export async function handleFormLogin(tipo: "candidato" | "empresa"){
    const candidato = tipo === "candidato"
    const documento = candidato ? "cpf" : "cnpj"
    const credenciais = {
        [documento]: semPontuacao(valor(`${documento}-login-${tipo}`)),
        senha: (document.getElementById("senha-login") as HTMLInputElement).value,
    }
    const rotulo = documento.toUpperCase()

    const res = await encadear([
        regra(documento),
        new FieldRequiredHandler("senha", "Informe a senha"),
    ]).handle(credenciais)
    if (!res.ok) { notify(res.message); return }

    try {
        await login(credenciais as { cpf?: string, cnpj?: string, senha: string })
        await refresh()
    } catch (e) {
        notify((e as any).status === 401 ? `${rotulo} ou senha inválidos` : (e as Error).message)
    }
}
