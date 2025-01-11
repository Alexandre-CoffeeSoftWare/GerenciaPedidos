package org.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemDTO {

    private Long id;

    @NotNull(message = "A quantidade não pode ser nulo")
    @Min(value = 1, message = "A quantidade deve ser maior que zero")
    private Integer idProduto;

    @NotNull(message = "A quantidade não pode ser nulo")
    @Min(value = 1, message = "A quantidade deve ser maior que zero")
    private Integer quantidade;

    @NotNull(message = "O valor não pode ser nulo")
    @Min(value = 1, message = "O valor deve ser maior que zero")
    private Float valor;
}
