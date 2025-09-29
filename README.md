# Linketinder 

**Desenvolvido por:** Augusto S. Lima

## 📋 Sobre o Projeto

O Linketinder é uma aplicação desenvolvida em Groovy e TypeScript que conecta candidatos e empresas, facilitando o processo de recrutamento. O projeto possui duas implementações distintas:
- **Backend/CLI em Groovy**: Interface de linha de comando para cadastro e gerenciamento
- **Frontend em TypeScript**: Interface web moderna e interativa

*Nota: Atualmente as duas implementações funcionam de forma independente e não estão integradas.*

## 🚀 Tecnologias Utilizadas

### Backend (Groovy)
- **Groovy** - Linguagem principal do backend
- **Gradle** - Gerenciamento de dependências e build

### Frontend (TypeScript)
- **TypeScript** - Linguagem principal do frontend
- **Vite** - Build tool e servidor de desenvolvimento
- **HTML5/CSS3** - Interface e estilização
- **JavaScript ES6+** - Funcionalidades interativas

### Ferramentas de Desenvolvimento
- **IntelliJ IDEA** - IDE de desenvolvimento

## 📁 Estrutura do Projeto

```
Linketinder/
├── src/                          # Backend Groovy
│   ├── main/
│   │   └── groovy/
│   │       └── com/augusto/linketinder/
│   │           ├── control/
│   │           │   ├── CadastroController.groovy
│   │           │   ├── DeleteController.groovy
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
│   │               ├── delete/
│   │               ├── menu/
│   │               ├── read/
│   │               └── update/
│   │       ├── App.groovy
│   │       └── ScriptPopularDadosInicial.groovy
│   └── test/
├── frontend/                     # Frontend TypeScript
│   ├── src/
│   │   ├── components/
│   │   │   ├── form-cadastro.ts
│   │   │   ├── form-login.ts
│   │   │   ├── grafico.ts
│   │   │   ├── homeGenerator.ts
│   │   │   └── listar.ts
│   │   ├── handlers/
│   │   │   └── formHandler.ts
│   │   ├── helpers/
│   │   │   ├── formHelper.ts
│   │   │   └── homeHelpers.ts
│   │   ├── models/
│   │   │   ├── Candidato.ts
│   │   │   ├── Empresa.ts
│   │   │   ├── Pessoa.ts
│   │   │   └── Vaga.ts
│   │   ├── pages/
│   │   │   ├── cadastro.ts
│   │   │   ├── home.ts
│   │   │   ├── login.ts
│   │   │   └── menu.ts
│   │   ├── storage/
│   │   │   └── lists.ts
│   │   ├── styles/
│   │   │   ├── style.css
│   │   │   ├── style-cadastro.css
│   │   │   ├── style-home.css
│   │   │   ├── style-login.css
│   │   │   └── style-menu.css
│   │   ├── templates/
│   │   │   ├── cadastroCandidato.html
│   │   │   ├── cadastroEmpresa.html
│   │   │   ├── homeCandidato.html
│   │   │   ├── homeEmpresa.html
│   │   │   ├── login.html
│   │   │   └── menu.html
│   │   ├── main.ts
│   │   └── router.ts
│   ├── public/
│   ├── index.html
│   ├── package.json
│   └── tsconfig.json
├── build.gradle
└── README.md
```

## 🏗️ Arquitetura

### Backend (MVC em Groovy)
O backend segue o padrão arquitetural MVC (Model-View-Controller):
- **Model**: Classes de domínio e estruturas de dados
- **View**: Interface de linha de comando
- **Controller**: Lógica de negócio e comunicação entre camadas

### Frontend (SPA em TypeScript)
O frontend utiliza uma arquitetura de Single Page Application (SPA):
- **Components**: Componentes reutilizáveis da interface
- **Pages**: Páginas da aplicação
- **Models**: Definições de tipos e interfaces TypeScript
- **Handlers**: Lógica de manipulação de eventos
- **Storage**: Gerenciamento de dados em localStorage

## ⚙️ Funcionalidades

### Backend (CLI Groovy)
- ✅ Cadastro de candidatos e empresas via linha de comando
- ✅ Visualização de registros
- ✅ Gerenciamento CRUD básico
- ✅ Sistema de competências

### Frontend (Web TypeScript)
- ✅ Interface web responsiva
- ✅ Sistema de login/cadastro
- ✅ Páginas dedicadas para candidatos e empresas
- ✅ Formulários interativos
- ✅ Gráficos e visualizações
- ✅ Armazenamento local (localStorage)
- ✅ Roteamento SPA

## 🛠️ Como Executar

### Backend (Groovy)

**Pré-requisitos:**
- Java 8 ou superior
- Gradle instalado

**Execução:**
```bash
# Via Gradle
gradle run

# Via IntelliJ IDEA
# Navegue até App.groovy e execute
```

### Frontend (TypeScript)

**Pré-requisitos:**
- Node.js 16+ instalado
- npm ou yarn

**Execução:**
```bash
# Navegue até a pasta frontend
cd frontend

# Instale as dependências
npm install

# Execute em modo de desenvolvimento
npm run dev

# Build para produção
npm run build

# Preview da build de produção
npm run preview
```

## 🌐 Acesso à Aplicação

### Backend
- **Tipo**: Interface de linha de comando (CLI)
- **Execução**: Via terminal após executar `gradle run`

### Frontend
- **Tipo**: Aplicação web (SPA)
- **URL de desenvolvimento**: `http://localhost:5173` (após `npm run dev`)
- **Funcionalidades**: Interface completa com login, cadastros e visualizações

## 📋 Funcionalidades Detalhadas

### Recursos do Frontend Web:
1. **Página de Login**: Autenticação de candidatos e empresas
2. **Cadastro Duplo**: Formulários específicos para candidatos e empresas
3. **Dashboard Candidatos**: 
   - Visualização de perfil
   - Listagem de vagas disponíveis
   - Gráficos de competências
4. **Dashboard Empresas**:
   - Gestão de vagas
   - Visualização de candidatos
   - Análises e métricas
5. **Navegação SPA**: Roteamento sem recarregamento de página

## 🚧 Próximos Passos

- [ ] **Integração Backend-Frontend**: Conectar as duas implementações
- [ ] **API REST**: Criar endpoints para comunicação
- [ ] **Banco de Dados**: Implementar persistência real
- [ ] **Sistema de Matching**: Algoritmo de compatibilidade
- [ ] **Autenticação JWT**: Sistema de autenticação robusto
- [ ] **Deploy**: Preparar para produção

## 🎯 Objetivo do Projeto

O Linketinder visa criar uma ponte entre candidatos e empresas através de:
- Interface intuitiva e moderna (frontend web)
- Sistema robusto de gerenciamento (backend CLI)
- Futuro sistema de matching inteligente
- Experiência completa de recrutamento

## 📝 Notas Técnicas

- **Backend**: Utiliza listas estáticas em memória
- **Frontend**: Usa localStorage para persistência local
- **Arquitetura**: Preparada para futura integração via API
- **Compatibilidade**: Frontend responsivo para diferentes dispositivos

## 🤝 Contribuições

Sinta-se à vontade para contribuir com:
- Melhorias na interface
- Novas funcionalidades
- Correções de bugs
- Integração backend-frontend

## 📄 Licença

Este projeto foi desenvolvido para fins educacionais.

---

**Desenvolvido por Augusto S. Lima**