// Mensagem na tela no lugar de alert(); fica fora do #app para sobreviver às re-renderizações
export function notify(mensagem: string, tipo: "erro" | "ok" = "erro"){
    let el = document.getElementById("notify")
    if(!el){
        el = document.createElement("div")
        el.id = "notify"
        el.setAttribute("role", "status")
        el.setAttribute("aria-live", "polite")
        document.body.appendChild(el)
    }
    el.textContent = mensagem
    el.className = `notify notify-${tipo}`
    clearTimeout(Number(el.dataset.timer))
    el.dataset.timer = String(setTimeout(() => { el!.className = "notify" }, 5000))
}
