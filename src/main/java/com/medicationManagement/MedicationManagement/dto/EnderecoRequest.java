package com.medicationManagement.MedicationManagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class EnderecoRequest {
    @NotNull(message = "O CEP é obrigatório")
    private Long cep;

    @NotBlank(message = "O logradouro é obrigatório")
    private String logradouro;

    @NotNull(message = "O número é obrigatório")
    private Integer numero;

    @NotBlank(message = "O bairro é obrigatório")
    private String bairro;

    @NotBlank(message = "A cidade é obrigatória")
    private String cidade;

    @NotBlank(message = "O estado é obrigatório")
    private String estado;

    private String complemento;

    @NotNull(message = "A latitude é obrigatória")
    private Double latitude;

    @NotNull(message = "A longitude é obrigatória")
    private Double longitude;
}

