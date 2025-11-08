import FormFactory from "../factories/FormFactory";

export function renderLogin(app: HTMLDivElement, type: string){
    const render = FormFactory.create('login', type as any);

    if(render){
        render(app)
    } else {
        window.location.reload()
    }
}