import FormFactory from "../factories/FormFactory";

export function renderCadastro(app: HTMLDivElement, type: string){
    const render = FormFactory.create('cadastro', type as any);

    if(render){
        render(app)
    } else {
        window.location.reload()
    }
}