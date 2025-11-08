
import PageFactory from "./factories/PageFactory";

export function router(){
    const app = document.querySelector<HTMLDivElement>("#app")!;

    const currentUser = localStorage.getItem("currentUser")

    const page = currentUser ? PageFactory.create("home") : PageFactory.create("menu");

    if(page){
        if(currentUser) page(app, currentUser)
        else page(app)
    } else {
        window.location.reload()
    }
}