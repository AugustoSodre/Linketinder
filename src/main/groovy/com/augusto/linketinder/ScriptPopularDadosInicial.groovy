package com.augusto.linketinder

import com.augusto.linketinder.model.lista.EnumCompetencias
import com.augusto.linketinder.model.lista.ListaCandidatoEstatica
import com.augusto.linketinder.model.lista.ListaEmpresaEstatica
import com.augusto.linketinder.model.pessoa.Candidato
import com.augusto.linketinder.model.pessoa.Empresa

class ScriptPopularDadosInicial {

    //Dados pessoas Fisicas (Candidatos)
    Candidato p1 = new Candidato(
            nome: "Augusto",
            email: "augusto@hotmail.com",
            estado: "DF",
            cep: "70670-666",
            descricao: "Aluno de CC",
            competencias: [
                    EnumCompetencias.PYTHON,
                    EnumCompetencias.JAVASCRIPT,
                    EnumCompetencias.JAVA
            ],
            cpf: "123.456.654-30",
            idade: 21
    )

    Candidato p2 = new Candidato(
            nome: "Mariana",
            email: "mariana.silva@gmail.com",
            estado: "SP",
            cep: "01001-000",
            descricao: "Desenvolvedora Fullstack",
            competencias: [
                    EnumCompetencias.ANGULAR,
                    EnumCompetencias.TYPESCRIPT,
                    EnumCompetencias.SPRING
            ],
            cpf: "321.654.987-00",
            idade: 28
    )

    Candidato p3 = new Candidato(
            nome: "Carlos",
            email: "carlos.dev@yahoo.com",
            estado: "RJ",
            cep: "22041-001",
            descricao: "Engenheiro de Software",
            competencias: [
                    EnumCompetencias.JAVA,
                    EnumCompetencias.SPRING,
                    EnumCompetencias.SPOCK
            ],
            cpf: "789.123.456-77",
            idade: 35
    )

    Candidato p4 = new Candidato(
            nome: "Fernanda",
            email: "fernanda.teste@outlook.com",
            estado: "MG",
            cep: "30140-071",
            descricao: "QA Specialist",
            competencias: [
                    EnumCompetencias.SPOCK,
                    EnumCompetencias.JAVASCRIPT,
                    EnumCompetencias.ANGULAR
            ],
            cpf: "555.444.333-22",
            idade: 26
    )

    Candidato p5 = new Candidato(
            nome: "Rafael",
            email: "rafael.react@protonmail.com",
            estado: "RS",
            cep: "90035-001",
            descricao: "Frontend Developer",
            competencias: [
                    EnumCompetencias.REACT,
                    EnumCompetencias.TYPESCRIPT,
                    EnumCompetencias.JAVASCRIPT
            ],
            cpf: "999.888.777-66",
            idade: 23
    )

    // Dados pessoas Juridicas (Empresas)
    Empresa pj1 = new Empresa(
            nome: "Império do Boliche",
            email: "imperio.boliche@gmail.com",
            estado: "RS",
            cep: "90435-021",
            descricao: "Maior boliche em Porto Alegre",
            competencias: [
                    EnumCompetencias.REACT,
                    EnumCompetencias.TYPESCRIPT,
                    EnumCompetencias.JAVASCRIPT
            ],
            cnpj: "12.345.678/0001-90",
            pais: "Brasil"
    )

    Empresa pj2 = new Empresa(
            nome: "Tech Solutions LTDA",
            email: "contato@techsolutions.com",
            estado: "SP",
            cep: "04567-010",
            descricao: "Consultoria em sistemas corporativos",
            competencias: [
                    EnumCompetencias.JAVA,
                    EnumCompetencias.SPRING,
                    EnumCompetencias.PYTHON
            ],
            cnpj: "98.765.432/0001-12",
            pais: "Brasil"
    )

    Empresa pj3 = new Empresa(
            nome: "AgroDigital",
            email: "suporte@agrodigital.com.br",
            estado: "GO",
            cep: "74000-000",
            descricao: "Soluções digitais para o agronegócio",
            competencias: [
                    EnumCompetencias.ANGULAR,
                    EnumCompetencias.TYPESCRIPT,
                    EnumCompetencias.SPRING
            ],
            cnpj: "55.444.333/0001-99",
            pais: "Brasil"
    )

    Empresa pj4 = new Empresa(
            nome: "EducaTech",
            email: "contato@educatech.org",
            estado: "DF",
            cep: "70000-000",
            descricao: "Plataforma de ensino online",
            competencias: [
                    EnumCompetencias.REACT,
                    EnumCompetencias.JAVASCRIPT,
                    EnumCompetencias.PYTHON
            ],
            cnpj: "77.888.999/0001-55",
            pais: "Brasil"
    )

    Empresa pj5 = new Empresa(
            nome: "FinanBank",
            email: "rh@finanbank.com",
            estado: "RJ",
            cep: "20040-010",
            descricao: "Banco digital com soluções inovadoras",
            competencias: [
                    EnumCompetencias.JAVA,
                    EnumCompetencias.SPRING,
                    EnumCompetencias.SPOCK
            ],
            cnpj: "22.111.333/0001-44",
            pais: "Brasil"
    )

    void start(){
        //ListaFisica
        ListaCandidatoEstatica.lista.add(p1)
        ListaCandidatoEstatica.lista.add(p2)
        ListaCandidatoEstatica.lista.add(p3)
        ListaCandidatoEstatica.lista.add(p4)
        ListaCandidatoEstatica.lista.add(p5)

        //ListaJuridica
        ListaEmpresaEstatica.lista.add(pj1)
        ListaEmpresaEstatica.lista.add(pj2)
        ListaEmpresaEstatica.lista.add(pj3)
        ListaEmpresaEstatica.lista.add(pj4)
        ListaEmpresaEstatica.lista.add(pj5)
    }
}
