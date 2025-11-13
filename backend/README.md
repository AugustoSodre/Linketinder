# Backend - Linketinder API

Backend da aplicação Linketinder com suporte a CLI e API REST.

## 🚀 Executar a Aplicação

### API REST

```bash
# Compila o projeto
./gradlew build

# Inicia o servidor API REST (porta padrão: 8080)
java -cp "build/classes/groovy/main:build/libs/*" com.augusto.linketinder.Server

# Ou especifica uma porta diferente
java -cp "build/classes/groovy/main:build/libs/*" com.augusto.linketinder.Server 9090

# Ou usa variável de ambiente
PORT=9090 java -cp "build/classes/groovy/main:build/libs/*" com.augusto.linketinder.Server
```

### CLI (Interface de Linha de Comando)

```bash
./gradlew run
```

## 🔌 Endpoints Disponíveis

Após iniciar o servidor, os seguintes endpoints estarão disponíveis:

- **Candidatos**: `http://localhost:8080/candidatos`
- **Empresas**: `http://localhost:8080/empresas`
- **Vagas**: `http://localhost:8080/vagas`

## 🧪 Testar a API

### Usando cURL:

```bash
# Listar todos os candidatos
curl http://localhost:8080/candidatos

# Criar um novo candidato
curl -X POST http://localhost:8080/candidatos \
  -H "Content-Type: application/json" \
  -d @test-candidato.json

# Atualizar candidato
curl -X PUT http://localhost:8080/candidatos/1 \
  -H "Content-Type: application/json" \
  -d @test-update-candidato.json

# Deletar candidato
curl -X DELETE http://localhost:8080/candidatos/1
```

### Arquivos de teste inclusos:

- `test-candidato.json` - Exemplo para criar candidato
- `test-update-candidato.json` - Exemplo para atualizar candidato
- `test-empresa.json` - Exemplo para criar empresa com vagas
- `test-update-empresa.json` - Exemplo para atualizar empresa
- `test-vaga.json` - Exemplo para criar vaga
- `test-update-vaga.json` - Exemplo para atualizar vaga

## 🛠️ Desenvolvimento

### Executar testes:

```bash
# Testes com H2 in-memory
./gradlew test

# Ou configurando explicitamente
DB_PROVIDER=h2 ./gradlew test
```

### Limpar e recompilar:

```bash
./gradlew clean build
```

## 📦 Estrutura do Código

```
src/main/groovy/com/augusto/linketinder/
├── API/                    # Controllers REST (HttpHandler)
│   ├── CandidatoControllerAPI.groovy
│   ├── EmpresaControllerAPI.groovy
│   ├── VagaControllerAPI.groovy
│   └── HTTPServerAPI.groovy
├── control/                # Controllers CLI
├── dao/                    # Data Access Objects
├── model/                  # Modelos do domínio
├── service/                # Camada de serviço
├── view/                   # Views CLI
├── App.groovy              # Entrada da CLI
└── Server.groovy           # Entrada da API REST
```

## ⚙️ Configuração

### Porta do Servidor

Você pode configurar a porta do servidor de três formas:

1. **Argumento de linha de comando:**
   ```bash
   java -cp "..." com.augusto.linketinder.Server 9090
   ```

2. **Variável de ambiente:**
   ```bash
   PORT=9090 java -cp "..." com.augusto.linketinder.Server
   ```

3. **Porta padrão:** 8080 (se nenhuma configuração for fornecida)

### Banco de Dados

Configure a conexão com PostgreSQL no arquivo de propriedades ou variáveis de ambiente conforme necessário.

## 🐛 Troubleshooting

### Porta já em uso:

```bash
# Verifique processos usando a porta 8080
lsof -i :8080

# Mate o processo se necessário
kill -9 <PID>

# Ou inicie em outra porta
./start-server.sh 9090
```

### Erro de classpath:

Certifique-se de compilar o projeto antes de executar:
```bash
./gradlew clean build
```

## 📚 Documentação Completa

Para documentação completa da API, consulte o [README principal](../README.md) na raiz do projeto.
