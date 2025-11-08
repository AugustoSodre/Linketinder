import { renderFormCandidato, renderFormEmpresa } from "../components/form-cadastro";
import { renderFormLogin } from "../components/form-login";

export type FormType = "Candidato" | "Empresa";
export type ActionType = "cadastro" | "login";

export class FormFactory {
  static create(action: ActionType, type: FormType): ((app: HTMLDivElement) => void) | null {
    if (action === "cadastro") {
      if (type === "Candidato") return (app: HTMLDivElement) => renderFormCandidato(app);
      if (type === "Empresa") return (app: HTMLDivElement) => renderFormEmpresa(app);
      return null;
    }

    if (action === "login") {
      
      if (type === "Candidato" || type === "Empresa")
        return (app: HTMLDivElement) => renderFormLogin(app, type);
      return null;
    }

    return null;
  }
}

export default FormFactory;
