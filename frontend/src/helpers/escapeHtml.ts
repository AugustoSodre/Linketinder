// Todo texto vindo da API passa por aqui antes de ir para innerHTML, senão um cadastro com "<script>" roda no navegador de quem visualizar
export function escapeHtml(value: unknown): string {
    return String(value ?? "")
        .replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;")
        .replaceAll('"', "&quot;")
        .replaceAll("'", "&#39;")
}
