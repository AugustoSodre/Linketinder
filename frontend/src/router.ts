
import { renderMenu } from "./pages/menu";

export function router(){
    const app = document.querySelector<HTMLDivElement>("#app")!;

    renderMenu(app)
}