package com.medicationManagement.MedicationManagement.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EstoqueRequest {
    @NotNull(message = "O CNPJ é obrigatório")
    private Long cnpj;

    @NotNull(message = "O número de registro é obrigatório")
    private Integer nroRegistro;

    @NotNull(message = "A quantidade é obrigatória")
    private Integer quantidade;
}

//(DTO para a requisição de adição de medicamentos ao estoque)
