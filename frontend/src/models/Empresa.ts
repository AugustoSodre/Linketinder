import { Pessoa } from "./Pessoa";

export class Empresa extends Pessoa {
    private pais: string;
    private cnpj: string;

    constructor(
        id: string,
        nome: string,
        email: string = '',
        estado: string = '',
        cep: string = '',
        descricao: string = '',
        pais: string = '',
        cnpj: string = ''
    ) {
        super(id, nome, email, estado, cep, descricao);
        this.pais = pais;
        this.cnpj = cnpj;
    }

    public getPais(){
        return this.pais
    }

    public getCnpj(){
        return this.cnpj
    }

    public setPais(pais: string){
        this.pais = pais
    }

    public setCnpj(cnpj: string){
        this.cnpj = cnpj
    }
}