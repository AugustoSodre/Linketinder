import { Pessoa } from "./Pessoa";

export class Candidato extends Pessoa {
    private idade: number;
    private cpf: string;
    private competencias: string[];

    constructor(
        nome: string = '',
        email: string = '',
        estado: string = '',
        cep: string = '',
        descricao: string = '',
        idade: number = 0,
        cpf: string = '',
        competencias: string[] = []
    ) {
        super(nome, email, estado, cep, descricao);
        this.idade = idade;
        this.cpf = cpf;
        this.competencias = competencias
    }

    public getIdade(){
        return this.idade
    }

    public getCpf(){
        return this.cpf
    }

    public getCompetencias(){
        return this.competencias
    }

    public setIdade(idade: number){
        this.idade = idade
    }

    public setCpf(cpf: string){
        this.cpf = cpf
    }

    public setCompetencias(competencias: string[]){
        this.competencias = competencias
    }
}