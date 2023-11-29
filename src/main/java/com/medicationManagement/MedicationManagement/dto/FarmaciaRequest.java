package com.medicationManagement.MedicationManagement.dto;

import lombok.Data;

@Data
public class FarmaciaRequest {
    private Long cnpj;
    private String razaoSocial;
    private String nomeFantasia;
    private String email;
    private String telefone;
    private String celular;
    private EnderecoRequest endereco;
}
