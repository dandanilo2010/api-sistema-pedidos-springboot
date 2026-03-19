# Sistema de Pedidos - Backend Spring Boot

## Tecnologias
- Java 17+
- Spring Boot
- Spring Data JPA
- MySQL 
- Lombok
- Validation (`@Valid` / `@NotBlank`)
- Exception handling (`ResourceNotFoundException`, `BusinessException`)
- Paginação com Spring Data `Pageable`

## Endpoints
### Clientes
- `GET /clientes` → listar todos os clientes
- `GET /clientes/{id}` → buscar cliente por ID
- `POST /clientes` → criar cliente
- `PUT /clientes/{id}` → atualizar cliente
- `DELETE /clientes/{id}` → deletar cliente

### Produtos
- `GET /produtos` → listar todos os produtos
- `GET /produtos/{id}` → buscar produto por ID
- `POST /produtos` → criar produto
- `PUT /produtos/{id}` → atualizar produto
- `DELETE /produtos/{id}` → deletar produto

### Pedidos
- `GET /pedidos?page=0&size=10` → listar pedidos com paginação
- `GET /pedidos/{id}` → buscar pedido por ID
- `POST /pedidos` → criar pedido

## Como rodar
```bash
# rodar backend
./mvnw spring-boot:run
Acesse http://localhost:8080
