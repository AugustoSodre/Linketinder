import type { BaseHandler } from "./chain/BaseHandler"
import { NumberRangeHandler } from "./chain/NumberRangeHandler"
import { RegexHandler } from "./chain/RegexHandler"
import regras from "../../../shared/regras.json"

type Campo = keyof typeof regras

export function encadear(handlers: BaseHandler[]): BaseHandler {
    handlers.reduce((anterior, proximo) => anterior.setNext(proximo))
    return handlers[0]
}

export function regra(campo: Campo): BaseHandler {
    const r = regras[campo]
    return "regex" in r
        ? new RegexHandler(campo, new RegExp(r.regex), r.mensagem)
        : new NumberRangeHandler(campo, r.min, r.max, r.mensagem)
}

export const regrasPessoa = () => (["nome", "email", "estado", "cep", "descricao"] as Campo[]).map(regra)

export const regraSenha = (opcional = false) =>
    opcional ? new RegexHandler("senha", new RegExp(`^$|${regras.senha.regex}`), regras.senha.mensagem) : regra("senha")
