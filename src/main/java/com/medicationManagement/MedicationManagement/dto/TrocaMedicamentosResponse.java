package com.medicationManagement.MedicationManagement.dto;

import lombok.Data;

@Data
public class TrocaMedicamentosResponse {
    private Integer nroRegistro;
    private Long cnpjOrigem;
    private Integer quantidadeOrigem;
    private Long cnpjDestino;
    private Integer quantidadeDestino;
}
