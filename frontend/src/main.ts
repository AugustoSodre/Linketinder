import {router} from "./router"
import { listCandidatos, listEmpresas } from "./storage/lists"

const candidatosFromStorage = localStorage.getItem("listCandidatos")
const empresasFromStorage = localStorage.getItem("listEmpresas")

if (candidatosFromStorage) {
    listCandidatos.splice(0, listCandidatos.length, ...JSON.parse(candidatosFromStorage))
}
if (empresasFromStorage) {
    listEmpresas.splice(0, listEmpresas.length, ...JSON.parse(empresasFromStorage))
}

router()