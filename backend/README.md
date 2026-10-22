# Backend - Linketinder API

API REST da aplicação Linketinder. A interface com o usuário é o frontend (`../frontend`).

## 🚀 Executar a Aplicação

```bash
# Jeito mais simples (porta 8080, ou PORT=9090 ./gradlew run)
./gradlew run

# Compila o projeto e copia as dependências para build/install
./gradlew installDist

# Inicia o servidor API REST (porta padrão: 8080)
java -cp "build/install/linketinder-backend/lib/*" com.augusto.linketinder.Server

# Ou especifica uma porta diferente
java -cp "build/install/linketinder-backend/lib/*" com.augusto.linketinder.Server 9090

# Ou usa variável de ambiente
PORT=9090 java -cp "build/install/linketinder-backend/lib/*" com.augusto.linketinder.Server
```

## 🔌 Endpoints Disponíveis

- **Candidatos**: `http://localhost:8080/candidatos`
- **Empresas**: `http://localhost:8080/empresas`
- **Vagas**: `http://localhost:8080/vagas`
- **Competências**: `http://localhost:8080/competencias` (qualquer usuário logado cria com `POST`; não há renomear nem excluir)
- **Login**: `POST http://localhost:8080/login`

Alterar e remover exigem o token do login (`Authorization: Bearer <token>`), e cada usuário só mexe nos próprios dados. Detalhes e códigos de resposta (400, 401, 403, 409, 429) no [README principal](../README.md#-autenticação).

## 🧪 Testar a API

```bash
# Listar todos os candidatos
curl http://localhost:8080/candidatos

# Criar um candidato (cpf com 11 dígitos, cep com 8, senha com 6+ caracteres)
curl -X POST http://localhost:8080/candidatos -H "Content-Type: application/json" \
  -d '{"nome":"Kevin","email":"kevin@office.com","estado":"SP","cep":"12345678","idade":40,"cpf":"77777777777","descricao":"Contador","senha":"chili123","competencias":[{"id":2,"nome":"React"}]}'

# Login (retorna o token)
curl -X POST http://localhost:8080/login -d '{"cpf":"77777777777","senha":"chili123"}'

# Atualizar e remover o próprio perfil
curl -X PUT http://localhost:8080/candidatos/<id> -H "Authorization: Bearer <token>" -d '{"descricao":"Contador sênior"}'
curl -X DELETE http://localhost:8080/candidatos/<id> -H "Authorization: Bearer <token>"
```

## 🛠️ Desenvolvimento

### Executar testes

```bash
# Os testes usam H2 em memória e não precisam do PostgreSQL
./gradlew test
```

### Limpar e recompilar

```bash
./gradlew clean installDist
```

## 📦 Estrutura do Código

```
src/main/groovy/com/augusto/linketinder/
├── API/                    # Controllers REST (HttpHandler)
│   ├── BaseControllerAPI.groovy   # CORS, respostas JSON e validação comuns
│   ├── CrudControllerAPI.groovy   # GET por id, PUT e DELETE comuns
│   ├── CandidatoControllerAPI.groovy
│   ├── EmpresaControllerAPI.groovy
│   ├── VagaControllerAPI.groovy
│   ├── CompetenciaControllerAPI.groovy
│   ├── LoginControllerAPI.groovy
│   └── HTTPServerAPI.groovy
├── dao/                    # Data Access Objects
├── model/                  # Modelos do domínio
├── service/                # Camada de serviço (AuthService: hash de senha, tokens e bloqueio de login)
└── Server.groovy           # Entrada da API REST
```

## ⚙️ Configuração

### Porta do Servidor

1. **Argumento de linha de comando:** `java -cp "..." com.augusto.linketinder.Server 9090`
2. **Variável de ambiente:** `PORT=9090 java -cp "..." com.augusto.linketinder.Server`
3. **Porta padrão:** 8080

### Banco de Dados

Variáveis de ambiente lidas por `JDBCConnectionProvider` (com os padrões entre parênteses):

- `JDBC_URL` (`jdbc:postgresql://localhost:5432/linketinder`)
- `JDBC_USER` (`postgres`)
- `JDBC_PASSWORD` (`postgres`)

`DB_PROVIDER=h2` troca para um banco H2 em memória.

## 🐛 Troubleshooting

### Porta já em uso

```bash
# Verifique processos usando a porta 8080
lsof -i :8080

# Ou inicie em outra porta
java -cp "build/install/linketinder-backend/lib/*" com.augusto.linketinder.Server 9090
```

### Erro "Could not find or load main class"

Rode `./gradlew installDist` antes e use o classpath `build/install/linketinder-backend/lib/*`.

## 📚 Documentação Completa

Para documentação completa da API, consulte o [README principal](../README.md) na raiz do projeto.
