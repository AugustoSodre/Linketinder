
import { renderHome } from "./pages/home";
import { renderMenu } from "./pages/menu";

export function router(){
    const app = document.querySelector<HTMLDivElement>("#app")!;

    const currentUser = localStorage.getItem("currentUser")

    if(currentUser){
        const parsedUser = JSON.parse(currentUser)
        
        if(parsedUser.cpf){
            renderHome(app, "Candidato")
        } else{
            renderHome(app, "Empresa")
        }
        
    } else{
        renderMenu(app)
    }
}