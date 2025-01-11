package org.example.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoDTO {

    private Long id;

    @NotNull(message = "O nome não pode ser nulo")
    @Size(min = 4, max = 30, message = "O nome deve ter entre 4 e 30 caracteres")
    private String nomeProduto;

    @NotNull(message = "A quantidade não pode ser nulo")
    @Min(value = 1, message = "A quantidade deve ser maior que zero")
    private Integer quantidade;

    @NotNull(message = "O valor não pode ser nulo")
    @Min(value = 1, message = "O valor deve ser maior que zero")
    private Float valor;
}