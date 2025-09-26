
export class Vaga {
    private nome: string;
    private descricao: string;
    private competencias: string[];

    constructor(nome: string, descricao: string, competencias: string[]) {
        this.nome = nome;
        this.descricao = descricao;
        this.competencias = competencias;
    }

    public getNome(): string {
        return this.nome;
    }

    public setNome(nome: string): void {
        this.nome = nome;
    }

    public getDescricao(): string {
        return this.descricao;
    }

    public setDescricao(descricao: string): void {
        this.descricao = descricao;
    }

    public getCompetencias(): string[] {
        return this.competencias;
    }

    public setCompetencias(competencias: string[]): void {
        this.competencias = competencias;
    }
}