export const valor = (id: string) => (document.getElementById(id) as HTMLInputElement | null)?.value.trim() ?? ""

export const semPontuacao = (texto: string) => texto.replace(/[.\-\/\s]/g, "")

export const marcadas = (classe: string) =>
    Array.from(document.querySelectorAll<HTMLInputElement>(`.${classe}:checked`)).map(c => c.value)
