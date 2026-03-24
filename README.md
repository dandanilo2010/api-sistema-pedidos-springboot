📦 Sistema de Pedidos - API REST (Spring Boot)

API REST desenvolvida com Java + Spring Boot para gerenciamento de clientes, produtos e pedidos, com autenticação e autorização utilizando JWT e Spring Security.

🚀 Tecnologias

Java 17+

Spring Boot

Spring Data JPA / Hibernate

Spring Security

JWT (JSON Web Token)

MySQL

Lombok

Bean Validation

Maven

🔐 Segurança

Autenticação com JWT

Senhas criptografadas com BCrypt

Filtro de autenticação customizado (JwtAuthenticationFilter)

Integração com UserDetailsService

Controle de acesso por perfil (ROLE_ADMIN / ROLE_USER)

Autorização por endpoint com @PreAuthorize

API stateless (sem sessão)

🧠 Conceitos aplicados

Arquitetura em camadas (Controller, Service, Repository)

Uso de DTO para entrada e saída de dados

Regras de negócio centralizadas no Service

Paginação com Spring Data

Enum para controle de status

Tratamento global de exceções

API REST segura com JWT

Documentação automática com Swagger

📊 Modelagem

Cliente

Produto

Pedido

PedidoItem

Relacionamento:

Pedido → PedidoItem → Produto
🔥 Funcionalidades
👤 Clientes

CRUD completo

Paginação

📦 Produtos

CRUD completo

Validação para impedir exclusão de produtos vinculados a pedidos

🧾 Pedidos

Criação com múltiplos itens

Cálculo automático do valor total

Armazenamento de preço no momento da compra

Controle de status com regras de negócio

🔄 Controle de Status

Fluxo:

PENDENTE → PROCESSANDO → ENVIADO → ENTREGUE

Regras:

Não permite pular etapas

Não permite alterar pedidos finalizados

Não permite alterar pedidos cancelados

🔑 Autenticação
Registro
POST /auth/register
{
  "nome": "Admin",
  "email": "admin@email.com",
  "senha": "123456",
  "role": "ROLE_ADMIN"
}
Login
POST /auth/login
{
  "email": "admin@email.com",
  "senha": "123456"
}

Resposta:

{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
🛡️ Autorização

Utilizar no header:

Authorization: Bearer SEU_TOKEN

Regras:

ADMIN → acesso total

USER → apenas leitura (GET)

📌 Endpoints
Clientes

GET /clientes

GET /clientes/{id}

POST /clientes

PUT /clientes/{id}

DELETE /clientes/{id}

Produtos

GET /produtos

GET /produtos/{id}

POST /produtos

PUT /produtos/{id}

DELETE /produtos/{id}

Pedidos

GET /pedidos

GET /pedidos/{id}

POST /pedidos

PATCH /pedidos/{id}/status

⚠️ Tratamento de exceções

ResourceNotFoundException → 404

BusinessException → 400

Validação → 400

Método inválido → 405

Erro interno → 500

▶️ Como executar
./mvnw spring-boot:run

Ou via IDE (IntelliJ / VS Code)

🌐 Acesso
http://localhost:8080
💡 Próximas melhorias

Testes unitários com JUnit

Documentação com Swagger

👨‍💻 Autor

Danilo Augusto da Silva Araújo
