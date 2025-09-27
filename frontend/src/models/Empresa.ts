import { Pessoa } from "./Pessoa";
import { Vaga } from "./Vaga";

export class Empresa extends Pessoa {
    private pais: string;
    private cnpj: string;
    private vaga: Vaga;

    constructor(
        nome: string,
        email: string = '',
        estado: string = '',
        cep: string = '',
        descricao: string = '',
        competencias: string[] = [],
        pais: string = '',
        cnpj: string = '',
        vaga: Vaga
    ) {
        super(nome, email, estado, cep, descricao, competencias);
        this.pais = pais;
        this.cnpj = cnpj;
        this.vaga = vaga
    }

    public getPais(){
        return this.pais
    }

    public getCnpj(){
        return this.cnpj
    }

    public getVaga(){
        return this.vaga
    }

    public setPais(pais: string){
        this.pais = pais
    }

    public setCnpj(cnpj: string){
        this.cnpj = cnpj
    }

    public setVaga(vaga: Vaga){
        this.vaga = vaga
    }
}