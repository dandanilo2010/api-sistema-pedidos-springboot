package com.example.sistema.de.pedidos.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "O id do cliente é obrigatório")
    private Long clienteId;

    private String nomeCliente;

    @NotEmpty(message = "O pedido deve conter ao menos um item")
    private List<@Valid PedidoItemDTO> itens;

    private BigDecimal valorTotal;

    private LocalDateTime dataPedido;

    private String status;
}