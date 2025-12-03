# 📸 Como Capturar Screenshots dos Testes WireMock

## 🎯 Objetivo
Este arquivo contém instruções detalhadas sobre como executar os testes WireMock e capturar screenshots para demonstrar o funcionamento da API simulada.

## 🚀 Executando os Testes WireMock

### 1. Pré-requisitos
- Java 17 instalado
- Maven instalado
- Terminal/prompt de comando

### 2. Comandos para Executar os Testes

```bash
# Navegar até o diretório do projeto
cd users-openapi-wiremock-demo

# Executar todos os testes da classe WireMock
mvn test -Dtest=WireMockUsersApiTest

# Executar um teste específico
mvn test -Dtest=WireMockUsersApiTest#testGetAllUsers

# Executar testes com log detalhado
mvn test -Dtest=WireMockUsersApiTest -X
```

### 3. O que Você Verá no Console

Quando executar os testes, você verá saídas como:

```
🚀 WireMock Server iniciado em: http://localhost:9999

📋 Testando GET /users - Listar usuários
✅ Status: 200
📄 Resposta: [{"id":1,"name":"João Silva","email":"joao@email.com"...}]

🔍 Testando GET /users/1 - Buscar usuário por ID
✅ Status: 200
📄 Resposta: {"id":1,"name":"João Silva","email":"joao@email.com"...}

➕ Testando POST /users - Criar usuário
✅ Status: 201
📤 Request: {"name":"Pedro Oliveira","email":"pedro@email.com"...}
📥 Response: {"id":3,"name":"Pedro Oliveira","email":"pedro@email.com"...}

✏️ Testando PUT /users/1 - Atualizar usuário
✅ Status: 200
📤 Request: {"name":"João Silva Santos","email":"joao.santos@email.com"...}
📥 Response: {"id":1,"name":"João Silva Santos","email":"joao.santos@email.com"...}

🗑️ Testando DELETE /users/1 - Remover usuário
✅ Status: 204
📄 Response: (vazio - esperado para DELETE)

❌ Testando cenário de erro - Usuário não encontrado
✅ Status: 404
📄 Response: {"timestamp":"2024-01-15T12:00:00","status":404,"error":"Not Found"...}

🔴 WireMock Server parado
```

## 📸 Como Capturar Screenshots

### Opção 1: Screenshot do Terminal
1. Execute o teste: `mvn test -Dtest=WireMockUsersApiTest`
2. Quando o teste estiver executando, pressione `Print Screen` ou use uma ferramenta de captura
3. Salve a imagem como `wiremock-test-terminal.png` na pasta `/docs/`

### Opção 2: Screenshot do IDE
1. Abra o projeto em IntelliJ IDEA ou Eclipse
2. Execute a classe de teste `WireMockUsersApiTest`
3. Capture o console/output do IDE
4. Salve como `wiremock-test-ide.png` na pasta `/docs/`

### Opção 3: Usando Postman (Demonstração Manual)
1. Execute apenas um teste específico para manter o servidor ativo:
   ```java
   // Modifique temporariamente o teste para não parar o servidor
   @Test
   void keepServerRunningForDemo() throws InterruptedException {
       Thread.sleep(60000); // Mantém por 1 minuto
   }
   ```
2. Abra o Postman
3. Faça requisições para `http://localhost:9999/users`
4. Capture screenshots das requisições e respostas

## 🔍 Testes Implementados

### 1. GET /users - Listar usuários
- **Endpoint**: `GET http://localhost:9999/users`
- **Resposta**: Lista de usuários simulados
- **Status**: 200 OK

### 2. GET /users/{id} - Buscar usuário
- **Endpoint**: `GET http://localhost:9999/users/1`
- **Resposta**: Dados de um usuário específico
- **Status**: 200 OK

### 3. POST /users - Criar usuário
- **Endpoint**: `POST http://localhost:9999/users`
- **Body**: JSON com dados do usuário
- **Resposta**: Usuário criado com ID
- **Status**: 201 Created

### 4. PUT /users/{id} - Atualizar usuário
- **Endpoint**: `PUT http://localhost:9999/users/1`
- **Body**: JSON com dados atualizados
- **Resposta**: Usuário atualizado
- **Status**: 200 OK

### 5. DELETE /users/{id} - Remover usuário
- **Endpoint**: `DELETE http://localhost:9999/users/1`
- **Resposta**: Vazia
- **Status**: 204 No Content

### 6. Cenário de Erro - Usuário não encontrado
- **Endpoint**: `GET http://localhost:9999/users/999`
- **Resposta**: Mensagem de erro
- **Status**: 404 Not Found

## 📋 Checklist para Screenshots

- [ ] Capturar execução do comando `mvn test`
- [ ] Mostrar console com outputs dos testes
- [ ] Demonstrar diferentes status codes (200, 201, 204, 404)
- [ ] Mostrar requests e responses JSON
- [ ] Capturar logs do WireMock server
- [ ] Salvar screenshots na pasta `/docs/`

## 📝 Exemplos de Screenshots Sugeridos

### 1. `wiremock-console-overview.png`
- Visão geral da execução de todos os testes no terminal

### 2. `wiremock-get-users.png`  
- Teste específico do GET /users com resposta

### 3. `wiremock-post-user.png`
- Teste do POST /users mostrando request e response

### 4. `wiremock-error-scenario.png`
- Cenário de erro 404 com mensagem

### 5. `wiremock-all-tests-summary.png`
- Sumário final dos testes executados

## 🎯 Valor Demonstrado

Os testes WireMock demonstram:
- ✅ **Simulação de API**: Criar um mock completo da API
- ✅ **Teste de endpoints**: Validar todos os métodos HTTP
- ✅ **Cenários de erro**: Testar responses de erro
- ✅ **Validação de dados**: Verificar formato JSON
- ✅ **Integração**: Como usar WireMock em projetos reais

## 📞 Suporte

Se tiver problemas:
1. Verifique se Java 17 está instalado: `java -version`
2. Verifique se Maven está instalado: `mvn -version`
3. Execute `mvn clean test` para limpar e testar
4. Verifique se a porta 9999 está livre