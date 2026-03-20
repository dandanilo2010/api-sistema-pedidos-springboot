📦 Sistema de Pedidos - Backend (Spring Boot)

API REST desenvolvida com Java + Spring Boot para gerenciamento de clientes, produtos e pedidos, seguindo boas práticas de arquitetura em camadas, validações e regras de negócio.

🚀 Tecnologias utilizadas

Java 17+

Spring Boot

Spring Data JPA

Hibernate

MySQL

Lombok

Bean Validation (@Valid, @NotNull, @NotEmpty)

Tratamento global de exceções

Paginação com Spring Data (Pageable)

🧠 Conceitos aplicados

Arquitetura em camadas (Controller, Service, Repository)

Uso de DTO para entrada e saída de dados

Regras de negócio no Service

Modelagem relacional correta de pedidos

Enum para controle de status

Validações de dados

Tratamento de exceções customizadas

📊 Modelagem do sistema

O sistema foi projetado com base em uma modelagem real de pedidos:

Pedido → PedidoItem → Produto
🔹 Pedido

Cliente

Lista de itens

Valor total

Data

Status

🔹 PedidoItem

Produto

Quantidade

Preço unitário (no momento da compra)

Subtotal

🔹 Produto

Nome

Preço

🔥 Funcionalidades implementadas
✔️ Gestão de Clientes

CRUD completo

✔️ Gestão de Produtos

CRUD completo

Bloqueio de exclusão de produtos vinculados a pedidos

✔️ Gestão de Pedidos

Criação de pedidos com múltiplos itens

Cálculo automático do valor total

Armazenamento de preço unitário no momento da compra

Paginação de pedidos

Busca por ID

🔄 Fluxo de criação de pedido
Exemplo de requisição:
{
  "clienteId": 1,
  "itens": [
    {
      "produtoId": 1,
      "quantidade": 2
    },
    {
      "produtoId": 2,
      "quantidade": 1
    }
  ]
}
O backend realiza:

Busca o cliente pelo ID

Busca cada produto pelo ID

Valida a quantidade de cada item

Define o preço unitário com base no produto

Calcula o subtotal de cada item

Soma todos os subtotais para gerar o valor total

Define o status inicial como PENDENTE

Persiste o pedido no banco

🔄 Controle de status do pedido

O pedido possui controle de fluxo através de Enum:

PENDENTE → PROCESSANDO → ENVIADO → ENTREGUE
Regras implementadas:

Não é possível pular etapas

Não é possível alterar pedidos já finalizados (ENTREGUE)

Não é possível alterar pedidos cancelados

📌 Endpoints
👤 Clientes

GET /clientes → listar clientes

GET /clientes/{id} → buscar por ID

POST /clientes → criar cliente

PUT /clientes/{id} → atualizar cliente

DELETE /clientes/{id} → deletar cliente

📦 Produtos

GET /produtos → listar produtos

GET /produtos/{id} → buscar por ID

POST /produtos → criar produto

PUT /produtos/{id} → atualizar produto

DELETE /produtos/{id} → deletar produto (com validação)

🧾 Pedidos

GET /pedidos?page=0&size=10 → listar pedidos com paginação

GET /pedidos/{id} → buscar pedido por ID

POST /pedidos → criar pedido

PATCH /pedidos/{id}/status?status=PROCESSANDO → atualizar status

⚠️ Tratamento de exceções

O sistema possui tratamento global com:

ResourceNotFoundException → recurso não encontrado

BusinessException → regras de negócio violadas

Exemplo de resposta:

{
  "erro": "Erro de negócio",
  "mensagem": "Pedido já foi finalizado",
  "status": 400
}
▶️ Como executar o projeto
# rodar aplicação
./mvnw spring-boot:run

Ou pela IDE (IntelliJ / VS Code)

🌐 Acesso
http://localhost:8080
💡 Próximas melhorias

Autenticação com JWT

Controle de acesso por perfil (roles)

Testes unitários com JUnit

Documentação com Swagger

👨‍💻 Autor

Danilo Augusto da Silva Araújo
