package com.medicationManagement.MedicationManagement.dto;

import lombok.Data;


@Data
public class EnderecoRequest {
    private Long cep;
    private String logradouro;
    private Integer numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String complemento;
    private Double latitude;
    private Double longitude;
}
