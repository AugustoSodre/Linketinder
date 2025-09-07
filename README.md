# Linketinder 

**Desenvolvido por:** Augusto S. Lima

## 📋 Sobre o Projeto

O Linketinder é uma aplicação desenvolvida em Groovy que conecta candidatos e empresas, facilitando o processo de recrutamento. A aplicação permite o cadastro e visualização de candidatos e empresas através de uma interface de linha de comando interativa.

## 🚀 Tecnologias Utilizadas

- **Groovy** - Linguagem principal do projeto
- **Gradle** - Gerenciamento de dependências e build
- **IntelliJ IDEA** - IDE de desenvolvimento

## 📁 Estrutura do Projeto

```
Linketinder/
├── src/
│   ├── main/
│   │   └── groovy/
│   │       └── com/augusto/linketinder/
│   │           ├── controller/
│   │           │   ├── CadastroController.groovy
│   │           │   └── MenuController.groovy
│   │           ├── model/
│   │           │   ├── lista/
│   │           │   │   ├── EnumCompetencias.groovy
│   │           │   │   ├── ListaFisicaEstatica.groovy
│   │           │   │   └── ListaJuridicaEstatica.groovy
│   │           │   └── pessoa/
│   │           │       ├── Pessoa.groovy
│   │           │       ├── PessoaFisica.groovy
│   │           │       └── PessoaJuridica.groovy
│   │           └── view/
│   │               ├── create/
│   │               │   ├── CadastroFisicoView.groovy
│   │               │   └── CadastroJuridicoView.groovy
│   │               ├── delete/
│   │               │   └── DeleteView.groovy
│   │               ├── menu/
│   │               │   ├── MenuFisico.groovy
│   │               │   ├── MenuJuridico.groovy
│   │               │   └── MenuMain.groovy
│   │               ├── read/
│   │               │   ├── ReadFisicoView.groovy
│   │               │   └── ReadJuridicoView.groovy
│   │               └── update/
│   │                   ├── UpdateFisicoView.groovy
│   │                   └── UpdateJuridicoView.groovy
│   │       └── App.groovy
│   │   └── resources/
│   └── test/
│       └── groovy/
├── build.gradle
└── README.md
```

## 🏗️ Arquitetura MVC

O projeto segue o padrão arquitetural MVC (Model-View-Controller):

- **Model**: Contém as classes de domínio (`Pessoa`, `PessoaFisica`, `PessoaJuridica`) e estruturas de dados
- **View**: Responsável pela interface com o usuário e apresentação dos dados
- **Controller**: Gerencia a lógica de negócio e comunicação entre Model e View

## ⚙️ Funcionalidades Implementadas

### ✅ Funcionalidades Disponíveis:
- **Cadastro de Candidatos (Pessoa Física)**
    - Nome, email, CPF, idade
    - Descrição pessoal
    - Lista de competências

- **Cadastro de Empresas (Pessoa Jurídica)**
    - Nome da empresa, email, CNPJ
    - País e CEP
    - Descrição da empresa

- **Visualização de Candidatos**
    - Lista todos os candidatos cadastrados

- **Visualização de Empresas**
    - Lista todas as empresas cadastradas

### 🚧 Funcionalidades Futuras:
- Edição de candidatos e empresas
- Exclusão de registros
- Sistema de matching entre candidatos e vagas
- Persistência de dados em banco
- Interface web

## 🛠️ Como Executar o Projeto

### Pré-requisitos:
- Java 8 ou superior
- Gradle instalado
- IntelliJ IDEA (recomendado)

### Passos para execução:

1. **Clone o repositório:**
   ```bash
   git clone git@github.com:AugustoSodre/Linketinder.git
   cd linketinder
   ```

2. **Abra o projeto no IntelliJ IDEA:**
    - File → Open → Selecione a pasta do projeto
    - O IntelliJ detectará automaticamente o projeto Gradle

3. **Execute via IntelliJ:**
    - Navegue até `src/main/groovy/App.groovy`
    - Clique com botão direito → Run 'App'

4. **Ou execute via linha de comando:**
   ```bash
   gradle run
   ```

5. **Ou compile e execute manualmente:**
   ```bash
   gradle build
   gradle installDist
   ./build/install/Linketinder/bin/Linketinder
   ```

## 📖 Como Usar

1. **Ao executar o programa, você verá o menu principal:**
   ```
   Linketinder!
   
   Opções:
   1. Gerenciar Empregadores
   2. Gerenciar Candidatos
   0. Sair
   ```

2. **Para cadastrar um candidato:**
    - Digite `2` para acessar o menu de candidatos
    - Digite `1` para cadastrar um novo candidato
    - Preencha as informações solicitadas

3. **Para cadastrar uma empresa:**
    - Digite `1` para acessar o menu de empregadores
    - Digite `1` para cadastrar uma nova empresa
    - Preencha as informações solicitadas

4. **Para visualizar os registros:**
    - Use a opção `2` nos menus específicos para listar candidatos ou empresas

## 🎯 Objetivo do Projeto

O Linketinder visa criar uma ponte entre candidatos em busca de oportunidades e empresas procurando talentos, automatizando e facilitando o processo de recrutamento através de um sistema de matching baseado em competências e requisitos.

## 📝 Notas de Desenvolvimento

- O projeto utiliza listas estáticas em memória para armazenamento temporário
- A arquitetura foi pensada para facilitar futuras implementações de persistência
- Interface de linha de comando para simplicidade inicial

## 🤝 Contribuições

Sinta-se à vontade para contribuir com melhorias, correções de bugs ou novas funcionalidades através de pull requests.

## 📄 Licença

Este projeto foi desenvolvido para fins educacionais.

---

**Desenvolvido por Augusto S. Lima**