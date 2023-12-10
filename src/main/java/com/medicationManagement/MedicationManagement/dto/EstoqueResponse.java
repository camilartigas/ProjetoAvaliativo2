package com.medicationManagement.MedicationManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EstoqueResponse {
    private Long cnpj;
    private Integer nroRegistro;
    private Integer quantidade;
    private LocalDateTime dataAtualizacao;
}
