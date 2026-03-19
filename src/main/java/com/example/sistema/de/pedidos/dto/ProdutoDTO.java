package com.example.sistema.de.pedidos.dto;

import com.example.sistema.de.pedidos.entity.ProdutoEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {

    private Long id;

    @NotBlank(message = "O nome do produto é obrigatório")
    private String nome;

    @NotNull(message = "O preço do produto é obrigatório")
    @Positive(message = "O preço do produto deve ser maior que zero")
    private BigDecimal preco;

    public ProdutoDTO(ProdutoEntity produto) {
        this.id = produto.getId();
        this.nome = produto.getNome();
        this.preco = produto.getPreco();
    }
}