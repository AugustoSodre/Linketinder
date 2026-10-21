import {router} from "./router"
import { loadData } from "./api"

loadData()
    .then(router)
    .catch((e) => {
        document.querySelector<HTMLDivElement>("#app")!.innerHTML =
            `<p>Não foi possível conectar à API (${e.message}). Verifique se o backend está rodando.</p>`
    })
