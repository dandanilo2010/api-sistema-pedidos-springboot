package com.example.sistema.de.pedidos.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PedidoItemDTO {

    @NotNull(message = "O id do produto é obrigatório")
    private Long produtoId;

    private String nomeProduto;

    @NotNull(message = "A quantidade é obrigatória")
    @Positive(message = "A quantidade deve ser maior que zero")
    private Integer quantidade;

    private BigDecimal precoUnitario;

    private BigDecimal subtotal;
}