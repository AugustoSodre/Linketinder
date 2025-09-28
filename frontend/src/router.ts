
import { renderHome } from "./pages/home";
import { renderMenu } from "./pages/menu";

export function router(){
    const app = document.querySelector<HTMLDivElement>("#app")!;

    const currentUser = localStorage.getItem("currentUser")

    if(currentUser){
        renderHome(app, currentUser)
        
    } else{
        renderMenu(app)
    }
}