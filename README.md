# API - Plataforma de Saúde Mental (Challenger SDS)

> Uma API RESTful robusta e segura construída com Spring Boot para uma plataforma B2B SaaS focada em medir e analisar a saúde mental de colaboradores de empresas clientes.

## 📝 Sobre o Projeto

Este projeto consiste no backend de uma aplicação SaaS (Software as a Service) projetada para que empresas possam se cadastrar e oferecer aos seus colaboradores uma ferramenta para o acompanhamento anônimo da saúde mental. A API gerencia a autenticação, autorização, segregação de dados entre empresas (multi-tenancy) e o cadastro de novas empresas e usuários.

A arquitetura foi pensada para ser segura e escalável, utilizando padrões de mercado como autenticação via Token JWT e Controle de Acesso Baseado em Perfis (RBAC).

## ✨ Funcionalidades Principais

-   **Autenticação via JWT:** Sistema de login seguro que gera tokens de acesso temporários.
-   **Controle de Acesso Baseado em Perfis (RBAC):**
    -   **Super Admin:** Gerencia a plataforma e cadastra empresas clientes.
    -   **Admin de Empresa:** Gerencia os usuários da sua própria empresa.
    -   **Colaborador:** Usuário final que responde às pesquisas.
-   **Multi-tenancy:** Segregação total dos dados, garantindo que uma empresa não possa acessar as informações de outra.
-   **Fluxo de Cadastro B2B:**
    -   Super Admin cadastra uma nova empresa e seu primeiro administrador.
    -   A API gera um link de cadastro exclusivo para os colaboradores daquela empresa.
-   **Gerenciamento de Usuários:** Rotas seguras para criação de novos administradores e para o reset de senhas.
-   **Coleta e Análise de Dados:** Endpoints para submissão de check-ins diários, questionários semanais e consulta de relatórios de risco psicossocial.

## 🛠️ Tecnologias Utilizadas

-   **Linguagem:** Java 21
-   **Framework:** Spring Boot 3+
-   **Segurança:** Spring Security 6+
-   **Banco de Dados:** Spring Data MongoDB, MongoDB
-   **Autenticação:** JSON Web Token (JJWT)
-   **Build Tool:** Gradle
-   **Utilitários:** Lombok

## 🚀 Como Executar o Projeto

**Pré-requisitos:**
-   JDK 21 ou superior.
-   Gradle 8+.
-   Uma instância do MongoDB rodando localmente (ou em um container Docker).

**Passos para execução:**
1.  Clone este repositório:
    ```bash
    git clone <URL_DO_SEU_REPOSITORIO>
    ```
2.  Navegue até a pasta do projeto:
    ```bash
    cd api-challenger-sds
    ```
3.  Configure o arquivo `src/main/resources/application.properties` com os dados de conexão do seu MongoDB e uma chave secreta para o JWT:
    ```properties
    # Conexão com o Banco de Dados MongoDB
    spring.data.mongodb.uri=mongodb://localhost:27017/sua_api_db

    # Chave secreta para assinatura dos tokens JWT
    api.security.token.secret=sua-chave-secreta-super-longa-e-segura-aqui
    ```
4.  Execute a aplicação usando o Gradle Wrapper:
    ```bash
    ./gradlew bootRun
    ```
5.  A API estará disponível em `http://localhost:8080`.

## 🔐 Modelo de Segurança

A API utiliza um fluxo de autenticação Bearer Token com JWT e um sistema de perfis (Roles) para autorização.

### Fluxo de Autenticação
1.  O usuário envia `username` e `password` para o endpoint `POST /api/auth/login`.
2.  Se as credenciais forem válidas, a API retorna um Token JWT.
3.  Para acessar rotas protegidas, o cliente deve enviar este token no header `Authorization`.
    -   **Exemplo:** `Authorization: Bearer <seu_token_jwt>`

### Perfis de Acesso (Roles)
-   `ROLE_PLATFORM_ADMIN`: O Super Admin da Softex. Pode gerenciar a plataforma inteira. O primeiro usuário é criado via *database seeding* (`DataInitializer.java`).
-   `ROLE_COMPANY_ADMIN`: O Administrador de uma empresa cliente. Pode gerenciar usuários e visualizar relatórios da sua própria empresa.
-   `ROLE_EMPLOYEE`: O Colaborador de uma empresa cliente. Usuário padrão que se cadastra pelo link da empresa para responder às pesquisas.

---

## 📚 Documentação da API (Endpoints)

### Autenticação 🔑 (Aberto a todos)

#### **Login de Usuário**
-   **Descrição:** Autentica qualquer tipo de usuário e retorna um token JWT.
-   **Endpoint:** `POST /api/auth/login`
-   **Corpo da Requisição (JSON):**
    ```json
    {
      "username": "email_do_usuario",
      "password": "senha_do_usuario"
    }
    ```
-   **Resposta de Sucesso (200 OK):**
    ```
    eyJhbGciOiJIUzI1NiJ9... (Token JWT)
    ```

#### **Registro de Colaborador**
-   **Descrição:** Endpoint público para um colaborador se registrar através do link da empresa.
-   **Endpoint:** `POST /api/auth/register/{empresaId}`
-   **Corpo da Requisição (JSON):**
    ```json
    {
      "username": "novo.colaborador@empresa.com",
      "password": "senhaDoColaborador"
    }
    ```
-   **Resposta de Sucesso (200 OK):**
    ```json
    "Colaborador registrado com sucesso!"
    ```

### Gestão da Plataforma 👑 (Requer `ROLE_PLATFORM_ADMIN`)

#### **Criar Nova Empresa Cliente**
-   **Descrição:** Cadastra uma nova empresa e seu primeiro administrador (`ROLE_COMPANY_ADMIN`).
-   **Endpoint:** `POST /api/empresas`
-   **Header:** `Authorization: Bearer <token_do_platform_admin>`
-   **Corpo da Requisição (JSON):**
    ```json
    {
      "nome": "Empresa Cliente Exemplo",
      "adminUsername": "admin@cliente.com",
      "adminPassword": "PasswordAdmin123",
      "setores": ["Financeiro", "RH"]
    }
    ```
-   **Resposta de Sucesso (201 Created):**
    ```json
    {
      "status": "SUCESSO",
      "message": "Empresa criada com sucesso!",
      "empresaId": "emp-...",
      "registrationLink": "http://localhost:8080/api/auth/register/emp-..."
    }
    ```

#### **Criar Novo Super Admin**
-   **Descrição:** Cria um novo usuário com permissões de Super Admin.
-   **Endpoint:** `POST /api/platform-admin/users`
-   **Header:** `Authorization: Bearer <token_do_platform_admin>`
-   **Corpo da Requisição (JSON):**
    ```json
    {
      "username": "outro.admin@softex.com",
      "password": "OutraSenhaSuperForte!"
    }
    ```
-   **Resposta de Sucesso (201 Created):** `"Platform Admin criado com sucesso."`

### Gestão da Empresa Cliente 👨‍💼 (Requer `ROLE_COMPANY_ADMIN`)

#### **Criar Novo Admin da Empresa**
-   **Descrição:** Um admin de empresa cria outro admin para a **sua própria empresa**.
-   **Endpoint:** `POST /api/empresas/{empresaId}/admins`
-   **Header:** `Authorization: Bearer <token_do_company_admin>`
-   **Corpo da Requisição (JSON):**
    ```json
    {
      "username": "admin2@empresa.com",
      "password": "NovaSenhaAdmin"
    }
    ```
-   **Resposta de Sucesso (201 Created):** `"Company Admin criado com sucesso para a empresa ..."`

### Gerenciamento de Usuários 🛡️ (Requer permissão de Admin)

#### **Reset de Senha por Admin**
-   **Descrição:** Um admin reseta a senha de outro usuário, respeitando as regras de permissão (Super Admin pode resetar qualquer senha; Company Admin só pode resetar de usuários da sua empresa).
-   **Endpoint:** `POST /api/users/{username}/reset-password`
-   **Header:** `Authorization: Bearer <token_do_admin_logado>`
-   **Corpo da Requisição (JSON):**
    ```json
    {
      "newPassword": "NovaSenhaParaOUsuario123"
    }
    ```
-   **Resposta de Sucesso (200 OK):** `"Senha resetada com sucesso para o usuário ..."`

### Coleta e Análise de Dados 📊

#### **Submeter Check-in Diário**
-   **Descrição:** Um colaborador (`ROLE_EMPLOYEE`) envia suas percepções diárias de humor e bem-estar.
-   **Endpoint:** `POST /api/checkins`
-   **Header:** `Authorization: Bearer <token_do_colaborador>`
-   **Corpo da Requisição (JSON):**
    ```json
    {
      "respostas": {
        "humor": 4,
        "sonoHoras": 7.5,
        "estresse": 2,
        "apetite": 5,
        "concentracao": 4,
        "fadiga": 2
      },
      "observacoes": "Dia produtivo hoje."
    }
    ```
-   **Resposta de Sucesso (201 Created):** `"Check-in salvo com sucesso."`

#### **Buscar Modelo do Questionário Semanal**
-   **Descrição:** O frontend busca a estrutura de perguntas do questionário ativo para renderizar na tela do usuário. Acessível a qualquer usuário logado.
-   **Endpoint:** `GET /api/questionarios/modelo/ativo`
-   **Header:** `Authorization: Bearer <token_de_qualquer_usuario_logado>`
-   **Resposta de Sucesso (200 OK):** Um JSON com a estrutura completa do `QuestionarioModelo`, contendo categorias e perguntas.

#### **Submeter Respostas do Questionário Semanal**
-   **Descrição:** Um colaborador (`ROLE_EMPLOYEE`) envia suas respostas para o questionário da semana. Esta ação dispara a análise de risco.
-   **Endpoint:** `POST /api/questionarios/respostas`
-   **Header:** `Authorization: Bearer <token_do_colaborador>`
-   **Corpo da Requisição (JSON):**
    ```json
    {
      "respostas": {
        "cargaTrabalho": 4,
        "relacionamentos": 5,
        "apoioGestor": 4,
        "clarezaFuncao": 5,
        "segurancaPercebida": 5
      }
    }
    ```
-   **Resposta de Sucesso (201 Created):** `"Respostas salvas com sucesso."`

#### **Consultar Relatórios de Risco da Empresa**
-   **Descrição:** Um admin de empresa (`ROLE_COMPANY_ADMIN`) consulta os relatórios de risco (anônimos) gerados para os colaboradores de **sua empresa**.
-   **Endpoint:** `GET /api/relatorios/empresa`
-   **Header:** `Authorization: Bearer <token_do_company_admin>`
-   **Resposta de Sucesso (200 OK):** Uma lista de objetos `RelatorioRisco`.
    ```json
    [
      {
        "id": "...",
        "empresaId": "emp-...",
        "usuarioAnonimoId": "user_...",
        "semanaReferencia": "2025-10-01",
        "dataAnalise": "...",
        "diagnosticos": {
          "DiagnosticoClimaRelacionamento": "Ambiente Saudável",
          "NivelCargaTrabalho": "Alta"
        }
      }
    ]
    ```

## 📌 Auditoria de Eventos

Criado mecanismo para **logar eventos importantes** (submissões de avaliações, edições de dados e alertas).

### Endpoints

- `POST /logs` (ROLE_PLATFORM_ADMIN e ROLE_COMPANY_ADMIN)  
  Cria um log de auditoria.
  ```json
  {
    "empresaId": "e_001",
    "acao": "SUBMISSAO_AVALIACAO",
    "detalhes": {
      "questionarioId": "qs_123",
      "usuario": "jane.doe",
      "nota": 4
    }
  }

Com certeza! Você já tem uma documentação excelente e vamos atualizá-la com as funcionalidades de negócio que implementamos. Inseri uma nova seção na documentação da API para cobrir os endpoints de Check-in, Questionários e Relatórios.

Aqui está o seu README.md completo e atualizado, com todas as rotas que desenvolvemos.

Markdown

# API - Plataforma de Saúde Mental (Challenger SDS)

> Uma API RESTful robusta e segura construída com Spring Boot para uma plataforma B2B SaaS focada em medir e analisar a saúde mental de colaboradores de empresas clientes.

## 📝 Sobre o Projeto

Este projeto consiste no backend de uma aplicação SaaS (Software as a Service) projetada para que empresas possam se cadastrar e oferecer aos seus colaboradores uma ferramenta para o acompanhamento anônimo da saúde mental. A API gerencia a autenticação, autorização, segregação de dados entre empresas (multi-tenancy) e o cadastro de novas empresas e usuários.

A arquitetura foi pensada para ser segura e escalável, utilizando padrões de mercado como autenticação via Token JWT e Controle de Acesso Baseado em Perfis (RBAC).

## ✨ Funcionalidades Principais

-   **Autenticação via JWT:** Sistema de login seguro que gera tokens de acesso temporários.
-   **Controle de Acesso Baseado em Perfis (RBAC):**
    -   **Super Admin:** Gerencia a plataforma e cadastra empresas clientes.
    -   **Admin de Empresa:** Gerencia os usuários da sua própria empresa.
    -   **Colaborador:** Usuário final que responde às pesquisas.
-   **Multi-tenancy:** Segregação total dos dados, garantindo que uma empresa não possa acessar as informações de outra.
-   **Fluxo de Cadastro B2B:**
    -   Super Admin cadastra uma nova empresa e seu primeiro administrador.
    -   A API gera um link de cadastro exclusivo para os colaboradores daquela empresa.
-   **Gerenciamento de Usuários:** Rotas seguras para criação de novos administradores e para o reset de senhas.
-   **Coleta e Análise de Dados:** Endpoints para submissão de check-ins diários, questionários semanais e consulta de relatórios de risco psicossocial.

## 🛠️ Tecnologias Utilizadas

-   **Linguagem:** Java 21
-   **Framework:** Spring Boot 3+
-   **Segurança:** Spring Security 6+
-   **Banco de Dados:** Spring Data MongoDB, MongoDB
-   **Autenticação:** JSON Web Token (JJWT)
-   **Build Tool:** Gradle
-   **Utilitários:** Lombok

## 🚀 Como Executar o Projeto

**Pré-requisitos:**
-   JDK 21 ou superior.
-   Gradle 8+.
-   Uma instância do MongoDB rodando localmente (ou em um container Docker).

**Passos para execução:**
1.  Clone este repositório:
    ```bash
    git clone <URL_DO_SEU_REPOSITORIO>
    ```
2.  Navegue até a pasta do projeto:
    ```bash
    cd api-challenger-sds
    ```
3.  Configure o arquivo `src/main/resources/application.properties` com os dados de conexão do seu MongoDB e uma chave secreta para o JWT:
    ```properties
    # Conexão com o Banco de Dados MongoDB
    spring.data.mongodb.uri=mongodb://localhost:27017/sua_api_db

    # Chave secreta para assinatura dos tokens JWT
    api.security.token.secret=sua-chave-secreta-super-longa-e-segura-aqui
    ```
4.  Execute a aplicação usando o Gradle Wrapper:
    ```bash
    ./gradlew bootRun
    ```
5.  A API estará disponível em `http://localhost:8080`.

## 🔐 Modelo de Segurança

A API utiliza um fluxo de autenticação Bearer Token com JWT e um sistema de perfis (Roles) para autorização.

### Fluxo de Autenticação
1.  O usuário envia `username` e `password` para o endpoint `POST /api/auth/login`.
2.  Se as credenciais forem válidas, a API retorna um Token JWT.
3.  Para acessar rotas protegidas, o cliente deve enviar este token no header `Authorization`.
    -   **Exemplo:** `Authorization: Bearer <seu_token_jwt>`

### Perfis de Acesso (Roles)
-   `ROLE_PLATFORM_ADMIN`: O Super Admin da Softex. Pode gerenciar a plataforma inteira. O primeiro usuário é criado via *database seeding* (`DataInitializer.java`).
-   `ROLE_COMPANY_ADMIN`: O Administrador de uma empresa cliente. Pode gerenciar usuários e visualizar relatórios da sua própria empresa.
-   `ROLE_EMPLOYEE`: O Colaborador de uma empresa cliente. Usuário padrão que se cadastra pelo link da empresa para responder às pesquisas.

---

## 📚 Documentação da API (Endpoints)

### Autenticação 🔑 (Aberto a todos)

#### **Login de Usuário**
-   **Descrição:** Autentica qualquer tipo de usuário e retorna um token JWT.
-   **Endpoint:** `POST /api/auth/login`
-   **Corpo da Requisição (JSON):**
    ```json
    {
      "username": "email_do_usuario",
      "password": "senha_do_usuario"
    }
    ```
-   **Resposta de Sucesso (200 OK):**
    ```
    eyJhbGciOiJIUzI1NiJ9... (Token JWT)
    ```

#### **Registro de Colaborador**
-   **Descrição:** Endpoint público para um colaborador se registrar através do link da empresa.
-   **Endpoint:** `POST /api/auth/register/{empresaId}`
-   **Corpo da Requisição (JSON):**
    ```json
    {
      "username": "novo.colaborador@empresa.com",
      "password": "senhaDoColaborador"
    }
    ```
-   **Resposta de Sucesso (200 OK):**
    ```json
    "Colaborador registrado com sucesso!"
    ```

### Gestão da Plataforma 👑 (Requer `ROLE_PLATFORM_ADMIN`)

#### **Criar Nova Empresa Cliente**
-   **Descrição:** Cadastra uma nova empresa e seu primeiro administrador (`ROLE_COMPANY_ADMIN`).
-   **Endpoint:** `POST /api/empresas`
-   **Header:** `Authorization: Bearer <token_do_platform_admin>`
-   **Corpo da Requisição (JSON):**
    ```json
    {
      "nome": "Empresa Cliente Exemplo",
      "adminUsername": "admin@cliente.com",
      "adminPassword": "PasswordAdmin123",
      "setores": ["Financeiro", "RH"]
    }
    ```
-   **Resposta de Sucesso (201 Created):**
    ```json
    {
      "status": "SUCESSO",
      "message": "Empresa criada com sucesso!",
      "empresaId": "emp-...",
      "registrationLink": "http://localhost:8080/api/auth/register/emp-..."
    }
    ```

#### **Criar Novo Super Admin**
-   **Descrição:** Cria um novo usuário com permissões de Super Admin.
-   **Endpoint:** `POST /api/platform-admin/users`
-   **Header:** `Authorization: Bearer <token_do_platform_admin>`
-   **Corpo da Requisição (JSON):**
    ```json
    {
      "username": "outro.admin@softex.com",
      "password": "OutraSenhaSuperForte!"
    }
    ```
-   **Resposta de Sucesso (201 Created):** `"Platform Admin criado com sucesso."`

### Gestão da Empresa Cliente 👨‍💼 (Requer `ROLE_COMPANY_ADMIN`)

#### **Criar Novo Admin da Empresa**
-   **Descrição:** Um admin de empresa cria outro admin para a **sua própria empresa**.
-   **Endpoint:** `POST /api/empresas/{empresaId}/admins`
-   **Header:** `Authorization: Bearer <token_do_company_admin>`
-   **Corpo da Requisição (JSON):**
    ```json
    {
      "username": "admin2@empresa.com",
      "password": "NovaSenhaAdmin"
    }
    ```
-   **Resposta de Sucesso (201 Created):** `"Company Admin criado com sucesso para a empresa ..."`

### Gerenciamento de Usuários 🛡️ (Requer permissão de Admin)

#### **Reset de Senha por Admin**
-   **Descrição:** Um admin reseta a senha de outro usuário, respeitando as regras de permissão (Super Admin pode resetar qualquer senha; Company Admin só pode resetar de usuários da sua empresa).
-   **Endpoint:** `POST /api/users/{username}/reset-password`
-   **Header:** `Authorization: Bearer <token_do_admin_logado>`
-   **Corpo da Requisição (JSON):**
    ```json
    {
      "newPassword": "NovaSenhaParaOUsuario123"
    }
    ```
-   **Resposta de Sucesso (200 OK):** `"Senha resetada com sucesso para o usuário ..."`

### Coleta e Análise de Dados 📊

#### **Submeter Check-in Diário**
-   **Descrição:** Um colaborador (`ROLE_EMPLOYEE`) envia suas percepções diárias de humor e bem-estar.
-   **Endpoint:** `POST /api/checkins`
-   **Header:** `Authorization: Bearer <token_do_colaborador>`
-   **Corpo da Requisição (JSON):**
    ```json
    {
      "respostas": {
        "humor": 4,
        "sonoHoras": 7.5,
        "estresse": 2,
        "apetite": 5,
        "concentracao": 4,
        "fadiga": 2
      },
      "observacoes": "Dia produtivo hoje."
    }
    ```
-   **Resposta de Sucesso (201 Created):** `"Check-in salvo com sucesso."`

#### **Buscar Modelo do Questionário Semanal**
-   **Descrição:** O frontend busca a estrutura de perguntas do questionário ativo para renderizar na tela do usuário. Acessível a qualquer usuário logado.
-   **Endpoint:** `GET /api/questionarios/modelo/ativo`
-   **Header:** `Authorization: Bearer <token_de_qualquer_usuario_logado>`
-   **Resposta de Sucesso (200 OK):** Um JSON com a estrutura completa do `QuestionarioModelo`, contendo categorias e perguntas.

#### **Submeter Respostas do Questionário Semanal**
-   **Descrição:** Um colaborador (`ROLE_EMPLOYEE`) envia suas respostas para o questionário da semana. Esta ação dispara a análise de risco.
-   **Endpoint:** `POST /api/questionarios/respostas`
-   **Header:** `Authorization: Bearer <token_do_colaborador>`
-   **Corpo da Requisição (JSON):**
    ```json
    {
      "respostas": {
        "cargaTrabalho": 4,
        "relacionamentos": 5,
        "apoioGestor": 4,
        "clarezaFuncao": 5,
        "segurancaPercebida": 5
      }
    }
    ```
-   **Resposta de Sucesso (201 Created):** `"Respostas salvas com sucesso."`

#### **Consultar Relatórios de Risco da Empresa**
-   **Descrição:** Um admin de empresa (`ROLE_COMPANY_ADMIN`) consulta os relatórios de risco (anônimos) gerados para os colaboradores de **sua empresa**.
-   **Endpoint:** `GET /api/relatorios/empresa`
-   **Header:** `Authorization: Bearer <token_do_company_admin>`
-   **Resposta de Sucesso (200 OK):** Uma lista de objetos `RelatorioRisco`.
    ```json
    [
      {
        "id": "...",
        "empresaId": "emp-...",
        "usuarioAnonimoId": "user_...",
        "semanaReferencia": "2025-10-01",
        "dataAnalise": "...",
        "diagnosticos": {
          "DiagnosticoClimaRelacionamento": "Ambiente Saudável",
          "NivelCargaTrabalho": "Alta"
        }
      }
    ]
    ```

## 📌 Auditoria de Eventos

Criado mecanismo para **logar eventos importantes** (submissões de avaliações, edições de dados e alertas).

### Endpoints

- `POST /logs` (ROLE_PLATFORM_ADMIN e ROLE_COMPANY_ADMIN)  
  Cria um log de auditoria.
  ```json
  {
    "empresaId": "e_001",
    "acao": "SUBMISSAO_AVALIACAO",
    "detalhes": {
      "questionarioId": "qs_123",
      "usuario": "jane.doe",
      "nota": 4
    }
  }
  ```
- `GET /logs?empresaId=e_001&acao=SUBMISSAO_AVALIACAO` (ROLE_PLATFORM_ADMIN e ROLE_COMPANY_ADMIN)  
  Lista os logs com filtros opcionais.

### Sugestões de valores para `acao`
- `SUBMISSAO_AVALIACAO`
- `EDICAO_DADOS`
- `ALERTA`

Os registros são persistidos na coleção MongoDB **`logs_auditoria`** (modelo `LogAuditoria`).

### Como chamar (exemplo curl)

```bash
# Criar log
curl -X POST http://localhost:8080/logs \
  -H "Content-Type: application/json" \  -H "Authorization: Bearer <TOKEN>" \  -d '{
    "empresaId":"e_001",
    "acao":"ALERTA",
    "detalhes":{"tipo":"risco_ia","gravidade":"ALTA","descricao":"Uso inadequado de prompt"}
  }'

# Listar logs
curl -H "Authorization: Bearer <TOKEN>" "http://localhost:8080/logs?empresaId=e_001&acao=ALERTA"
```