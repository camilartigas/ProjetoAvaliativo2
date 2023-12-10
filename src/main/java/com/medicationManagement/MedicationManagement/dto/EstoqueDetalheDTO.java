package com.medicationManagement.MedicationManagement.dto;

import lombok.Data;
import java.time.LocalDateTime;


@Data
public class EstoqueDetalheDTO {
    private Integer nroRegistro;
    private String nome;
    private Integer quantidade;
    private LocalDateTime dataAtualizacao;
}
