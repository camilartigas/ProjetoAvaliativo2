
package com.medicationManagement.MedicationManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class EstoqueResponse {
    private Long cnpj;
    private Integer nroRegistro;
    private Integer quantidade;
    private LocalDateTime dataAtualizacao;
}
// (DTO para a resposta de adição/atualização de medicamentos no estoque)