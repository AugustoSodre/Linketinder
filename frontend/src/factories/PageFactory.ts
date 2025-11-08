import { renderMenu } from "../pages/menu";
import { renderHome } from "../pages/home";
import { renderCadastro } from "../pages/cadastro";
import { renderLogin } from "../pages/login";

export type PageName = "menu" | "home" | "cadastro" | "login";

// Returns a page renderer function with signature (app, payload?) => void
export class PageFactory {
  static create(page: PageName): ((app: HTMLDivElement, payload?: any) => void) | null {
    switch (page) {
      case "menu":
        return (app: HTMLDivElement) => renderMenu(app);

      case "home":
        return (app: HTMLDivElement, payload?: any) => renderHome(app, payload);

      case "cadastro":
        return (app: HTMLDivElement, payload?: any) => renderCadastro(app, payload);

      case "login":
        return (app: HTMLDivElement, payload?: any) => renderLogin(app, payload);

      default:
        return null;
    }
  }
}

export default PageFactory;
