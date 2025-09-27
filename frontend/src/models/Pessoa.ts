
import SHA256 from "crypto-js/sha256";

export class Pessoa {
    private id: string;
    private nome: string;
    private email: string;
    private estado: string;
    private cep: string;
    private descricao: string;
    private competencias: string[] = []

    constructor(nome: string, email: string = '', estado: string = '', cep: string = '', descricao: string = '', competencias: string[] = []) {
        this.id = this.generateId()
        this.nome = nome;
        this.email = email;
        this.estado = estado;
        this.cep = cep;
        this.descricao = descricao;
        this.competencias = competencias
    }

    private generateId(): string{
        const currentDate = new Date().toUTCString()
        const hashId: string = SHA256(currentDate).toString()

        return hashId
    }

    public getId(): string {
        return this.id;
    }

    public getNome(): string {
        return this.nome;
    }

    public getEmail(): string {
        return this.email;
    }

    public getEstado(): string {
        return this.estado;
    }

    public getCep(): string {
        return this.cep;
    }

    public getDescricao(): string {
        return this.descricao;
    }

    public getCompetencias(): string[] {
        return this.competencias
    }

    public setNome(nome: string): void {
        this.nome = nome;
    }

    public setEmail(email: string): void {
        this.email = email;
    }

    public setEstado(estado: string): void {
        this.estado = estado;
    }

    public setCep(cep: string): void {
        this.cep = cep;
    }

    public setDescricao(descricao: string): void {
        this.descricao = descricao;
    }

    public setCompetencias(competencias: string[]): void {
        this.competencias = competencias
    }
}