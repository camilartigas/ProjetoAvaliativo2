package com.medicationManagement.MedicationManagement.dto;

import lombok.Data;


@Data
public class TrocaMedicamentosRequest {
    private Long cnpjOrigem;
    private Long cnpjDestino;
    private Integer nroRegistro;
    private Integer quantidade;
}
