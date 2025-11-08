
import PageFactory from "../factories/PageFactory";

export function renderMenu(app: HTMLDivElement){
    app.innerHTML = `
        <div id="menu-btn-container">
            <button class="btn-menu" id="btn-cadastrar-candidato">Cadastrar Candidato</button>
            <button class="btn-menu" id="btn-cadastrar-empresa">Cadastrar Empresa</button>
            <button class="btn-menu" id="btn-login-candidato">Login Candidato</button>
            <button class="btn-menu" id="btn-login-empresa">Login Empresa</button>
        </div>
    `
    
    const actions: Record<string, { page: any, payload?: any }> = {
        "btn-cadastrar-candidato": { page: "cadastro", payload: "Candidato" },
        "btn-cadastrar-empresa": { page: "cadastro", payload: "Empresa" },
        "btn-login-candidato": { page: "login", payload: "Candidato" },
        "btn-login-empresa": { page: "login", payload: "Empresa" }
    };

    Object.entries(actions).forEach(([id, info]) => {
        const el = document.getElementById(id);
        if (!el) return;
        el.addEventListener("click", () => {
            const page = PageFactory.create(info.page as any);
            if (page) {
                if (info.payload !== undefined) page(app, info.payload);
                else page(app);
            } else {
                window.location.reload();
            }
        });
    });
}
