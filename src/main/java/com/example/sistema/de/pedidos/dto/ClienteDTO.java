package com.example.sistema.de.pedidos.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {

    private Long id;

    @NotBlank(message = "O nome do cliente é obrigatório")
    private String nome;

    @Email(message = "Email inválido")
    @NotBlank(message = "O email do cliente é obrigatório")
    private String email;
}