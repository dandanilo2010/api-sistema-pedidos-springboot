package com.example.sistema.de.pedidos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {

    private Long id;

    @NotBlank(message = "O nome do cliente é obrigatório")
    private String nomeCliente;

    @NotEmpty(message = "O pedido deve conter ao menos um produto")
    private List<@NotBlank(message = "O nome do produto não pode estar vazio") String> nomesProdutos;

    @NotNull(message = "O valor total é obrigatório")
    @Positive(message = "O valor total deve ser maior que zero")
    private BigDecimal valorTotal;

    @NotNull(message = "A data do pedido é obrigatória")
    private LocalDateTime dataPedido;
}