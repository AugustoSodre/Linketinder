import type { BaseHandler } from "./chain/BaseHandler"
import { FieldRequiredHandler } from "./chain/FieldRequiredHandler"
import { RegexHandler } from "./chain/RegexHandler"

export function encadear(handlers: BaseHandler[]): BaseHandler {
    handlers.reduce((anterior, proximo) => anterior.setNext(proximo))
    return handlers[0]
}

export function regrasPessoa(): BaseHandler[] {
    return [
        new FieldRequiredHandler("nome", "Nome inválido"),
        new RegexHandler("nome", /^[a-zA-ZÀ-ÿ\s]+$/, "Nome com caractere inválido"),
        new RegexHandler("email", /^[\w.]+@\w+\.\w+(\.\w{2,3})?$/, "Email inválido"),
        new RegexHandler("estado", /^[A-Z]{2}$/, "Estado inválido"),
        new RegexHandler("cep", /^\d{8}$/, "CEP inválido"),
        new FieldRequiredHandler("descricao", "Descrição inválida"),
    ]
}

export const regraPais = () => new RegexHandler("pais", /^[A-Z]{2}$/, "País inválido")

export const regraSenha = (opcional = false) =>
    new RegexHandler("senha", opcional ? /^(.{6,})?$/ : /^.{6,}$/, "A senha deve ter pelo menos 6 caracteres")
