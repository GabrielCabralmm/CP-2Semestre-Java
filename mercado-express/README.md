# Mercado Express API

**FIAP – Faculdade de Informática e Administração Paulista**
**Curso:** Tecnologia em Análise e Desenvolvimento de Sistemas (TDS)
**Disciplina/Atividade:** Checkpoint 4 – Parte 1 (API e Deploy)
**Professor:** Dr. Marcel Stefan Wagner

**Integrantes do grupo (nome e RM):**
> ⚠️ *Preencha aqui com o nome completo e RM de todos os integrantes do grupo.*
- Nome Completo – RM 000000

**IDE utilizada:**
> ⚠️ *Indique aqui: IntelliJ, Eclipse ou NetBeans.*

**Link do Deploy:**
> ⚠️ *Cole aqui o link da aplicação publicada (Render, Railway, Heroku, etc.).*

---

## 1. Descrição do projeto

API REST desenvolvida com **Spring Boot (Maven / Java)** para uma empresa do tipo
**"mercado express"** (venda de itens como meias, produtos de limpeza, frutas, etc.).

A aplicação:

- Utiliza **Spring Data JPA** para persistência dos dados no banco **Oracle (ORACLE_FIAP)**,
  na tabela `TDS_TB_MERCADO`.
- Utiliza **Lombok** (`@Data`, `@NoArgsConstructor`, `@AllArgsConstructor`) para eliminar
  código boilerplate (getters, setters, construtores) na entidade `Mercado`.
- Implementa o CRUD completo (**C**reate, **R**ead, **U**pdate, **D**elete) através do endpoint
  `/mercado`.
- Retorna as respostas seguindo o padrão **HATEOAS**, no **nível de maturidade 3** do
  Modelo de Maturidade de Richardson — cada recurso retornado traz links (`self`, `mercado`,
  `atualizar`, `atualizar-parcial`, `deletar`) que indicam as próximas ações possíveis.
- Roda no **Tomcat embutido, na porta 8082**.

### Arquitetura (fluxo de dados)

```
Postman/Insomnia (JSON) <--HTTP--> Controller (Spring) <--Persist--> Repository/EntityManager <--> Banco Oracle (TDS_TB_MERCADO)
```

### Estrutura de pastas

```
mercado-express/
├── pom.xml
├── src/main/java/com/fiap/mercadoexpress/
│   ├── MercadoExpressApplication.java      -> classe main
│   ├── model/Mercado.java                  -> entidade JPA (com Lombok)
│   ├── repository/MercadoRepository.java   -> Spring Data JPA (EntityManager)
│   ├── controller/MercadoController.java   -> endpoints REST (CRUD) /mercado
│   ├── assembler/MercadoModelAssembler.java-> monta os links HATEOAS
│   └── exception/                          -> tratamento de erros (404)
└── src/main/resources/application.properties -> configuração do datasource Oracle e porta 8082
```

---

## 2. Tabela no banco de dados

Tabela: **TDS_TB_MERCADO** (banco `ORACLE_FIAP`, criada automaticamente pelo Hibernate
via `spring.jpa.hibernate.ddl-auto=update`, configurado em `application.properties`).

| Coluna  | Tipo (Java)  | Descrição              |
|---------|--------------|-------------------------|
| ID      | Long         | Chave primária (auto)   |
| NOME    | String       | Nome do produto          |
| TIPO    | String       | Tipo do produto (ex.: Limpeza, Alimento) |
| SETOR   | String       | Setor/Departamento       |
| TAMANHO | String       | Tamanho/Embalagem        |
| PRECO   | Double       | Preço do produto         |

---

## 3. Configuração da conexão com o Oracle

No arquivo `src/main/resources/application.properties`, substitua as credenciais pelas
suas do laboratório Oracle FIAP:

```properties
server.port=8082

spring.datasource.url=jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL
spring.datasource.username=SEU_RM_AQUI
spring.datasource.password=SUA_SENHA_AQUI
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver

spring.jpa.database-platform=org.hibernate.dialect.OracleDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

> ⚠️ *Insira aqui um print da tela do SQL Developer conectado ao ORACLE_FIAP mostrando
> a tabela TDS_TB_MERCADO criada/populada.*

---

## 4. Configuração do Spring Initializr

> ⚠️ *Insira aqui o print (.jpg/.jpeg/.png) da configuração final do Spring Initializr,
> conforme exigido no enunciado, mostrando: Maven, Java, Spring Boot, e as dependências
> Spring Web, Spring Data JPA, Spring HATEOAS, Lombok, Oracle Driver, Validation.*

Dependências utilizadas (`pom.xml`):
- Spring Web
- Spring Data JPA
- Spring HATEOAS
- Validation
- Lombok
- Oracle JDBC Driver (`ojdbc11`)
- Spring Boot DevTools

---

## 5. Documentação dos endpoints (CRUD)

Base URL de testes: **http://localhost:8082**

### 5.1. CREATE — `POST /mercado`

Cria um novo produto.

**Request Body (JSON):**
```json
{
  "nome": "Detergente Neutro 500ml",
  "tipo": "Limpeza",
  "setor": "Higiene",
  "tamanho": "500ml",
  "preco": 3.99
}
```

**Response (201 Created) — HATEOAS:**
```json
{
  "id": 1,
  "nome": "Detergente Neutro 500ml",
  "tipo": "Limpeza",
  "setor": "Higiene",
  "tamanho": "500ml",
  "preco": 3.99,
  "_links": {
    "self": { "href": "http://localhost:8082/mercado/1" },
    "mercado": { "href": "http://localhost:8082/mercado" },
    "atualizar": { "href": "http://localhost:8082/mercado/1" },
    "atualizar-parcial": { "href": "http://localhost:8082/mercado/1" },
    "deletar": { "href": "http://localhost:8082/mercado/1" }
  }
}
```

> ⚠️ *Insira aqui o print do Postman/Insomnia mostrando o POST e a resposta acima.*

---

### 5.2. READ (listar todos) — `GET /mercado`

**Response (200 OK) — HATEOAS:**
```json
{
  "_embedded": {
    "mercadoList": [
      {
        "id": 1,
        "nome": "Detergente Neutro 500ml",
        "tipo": "Limpeza",
        "setor": "Higiene",
        "tamanho": "500ml",
        "preco": 3.99,
        "_links": {
          "self": { "href": "http://localhost:8082/mercado/1" },
          "mercado": { "href": "http://localhost:8082/mercado" }
        }
      }
    ]
  },
  "_links": {
    "self": { "href": "http://localhost:8082/mercado" }
  }
}
```

> ⚠️ *Insira aqui o print do Postman/Insomnia mostrando o GET /mercado.*

---

### 5.3. READ (buscar por id) — `GET /mercado/{id}`

Exemplo: `GET http://localhost:8082/mercado/1`

**Response (200 OK):** mesma estrutura do item 5.1, com os links `self`, `mercado`,
`atualizar`, `atualizar-parcial` e `deletar`.

> ⚠️ *Insira aqui o print do Postman/Insomnia mostrando o GET /mercado/1.*

---

### 5.4. UPDATE (completo) — `PUT /mercado/{id}`

Exemplo: `PUT http://localhost:8082/mercado/1`

**Request Body (JSON):**
```json
{
  "nome": "Detergente Neutro 1L",
  "tipo": "Limpeza",
  "setor": "Higiene",
  "tamanho": "1L",
  "preco": 6.49
}
```

**Response (200 OK):** produto atualizado, com os respectivos links HATEOAS.

> ⚠️ *Insira aqui o print do Postman/Insomnia mostrando o PUT.*

---

### 5.5. UPDATE (parcial) — `PATCH /mercado/{id}`

Exemplo: `PATCH http://localhost:8082/mercado/1`

**Request Body (JSON):** apenas os campos que deseja alterar.
```json
{
  "preco": 5.99
}
```

**Response (200 OK):** produto atualizado apenas no campo `preco`.

> ⚠️ *Insira aqui o print do Postman/Insomnia mostrando o PATCH.*

---

### 5.6. DELETE — `DELETE /mercado/{id}`

Exemplo: `DELETE http://localhost:8082/mercado/1`

**Response:** `204 No Content` (produto removido do banco pelo ID).

> ⚠️ *Insira aqui o print do Postman/Insomnia mostrando o DELETE.*

---

## 6. HATEOAS – nível de maturidade 3

Cada resposta da API traz, dentro de `_links`, as ações disponíveis para aquele recurso
(`self`, `mercado` (voltar à listagem), `atualizar`, `atualizar-parcial`, `deletar`).
Isso permite que o cliente da API "navegue" entre os recursos apenas seguindo os links
retornados, sem precisar conhecer previamente todas as URLs — característica do nível 3
(HATEOAS) do Modelo de Maturidade de Richardson.

A implementação está em `MercadoModelAssembler`, que usa `linkTo(methodOn(...))` do
Spring HATEOAS para gerar os links dinamicamente a partir dos métodos do `MercadoController`.

---

## 7. Como executar localmente

1. Configure as credenciais do Oracle em `src/main/resources/application.properties`.
2. Rode o projeto:
   ```bash
   mvn spring-boot:run
   ```
3. A API estará disponível em `http://localhost:8082/mercado`.
4. Importe/teste os endpoints acima no Postman ou Insomnia.

---

## 8. Deploy

> ⚠️ *Descreva aqui a plataforma escolhida para o deploy (ex.: Render, Railway, Heroku)
> e o passo a passo realizado, junto com o link final de acesso à aplicação publicada.*
