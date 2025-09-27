
export function renderHome(app: HTMLDivElement, tipo: string){

    if(tipo == "Candidato"){
        renderHomeCandidatoHTML(app)
    } else if(tipo == "Empresa"){
        renderHomeEmpresaHTML(app)
    } else{
        window.location.reload()
    }
}

function renderHomeCandidatoHTML(app: HTMLDivElement){
    console.log("Home candidato")
}

function renderHomeEmpresaHTML(app: HTMLDivElement){
    console.log("Home empresa")
}