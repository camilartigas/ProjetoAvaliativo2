package com.medicationManagement.MedicationManagement.dto;

import com.medicationManagement.MedicationManagement.model.TipoMedicamento;
import jakarta.validation.constraints.*;
import lombok.Data;


@Data
public class MedicamentoRequest {
    @NotNull(message = "O número de registro é obrigatório")
    private Integer numeroRegistro;
    
    @NotBlank(message = "O nome é obrigatório")
    private String nome;
    
    @NotBlank(message = "O laboratório é obrigatório")
    private String laboratorio;
    
    @NotBlank(message = "A dosagem é obrigatória")
    private String dosagem;
    
    private String descricao;
    
    @NotNull(message = "O preço é obrigatório")
    @Positive(message = "O preço deve ser um valor positivo")
    private Float preco;
    
    @NotNull(message = "O tipo de medicamento é obrigatório")
    private TipoMedicamento tipo;
}
