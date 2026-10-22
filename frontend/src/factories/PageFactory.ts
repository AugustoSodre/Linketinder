import { renderMenu } from "../pages/menu";
import { renderHome } from "../pages/home";
import { renderCadastro } from "../pages/cadastro";
import { renderLogin } from "../pages/login";
import type { UsuarioLogado } from "../models/types";

export type PageName = "menu" | "home" | "cadastro" | "login";

export class PageFactory {
  static create(page: PageName): ((app: HTMLDivElement, payload?: unknown) => void) | null {
    switch (page) {
      case "menu":
        return (app: HTMLDivElement) => renderMenu(app);

      case "home":
        return (app: HTMLDivElement, payload?: unknown) => renderHome(app, payload as UsuarioLogado);

      case "cadastro":
        return (app: HTMLDivElement, payload?: unknown) => renderCadastro(app, payload as string);

      case "login":
        return (app: HTMLDivElement, payload?: unknown) => renderLogin(app, payload as string);

      default:
        return null;
    }
  }
}

export default PageFactory;
