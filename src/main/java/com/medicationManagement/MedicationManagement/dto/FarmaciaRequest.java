package com.medicationManagement.MedicationManagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FarmaciaRequest {
    @NotNull(message = "O CNPJ é obrigatório")
    private Long cnpj;
    @NotBlank(message = "A Razão Social é obrigatória")
    private String razaoSocial;
    @NotBlank(message = "O Nome Fantasia é obrigatório")
    private String nomeFantasia;
    @NotBlank(message = "O e-mail é obrigatório")
    private String email;
    private String telefone;
    @NotBlank(message = "O celular é obrigatório")
    private String celular;
    private EnderecoRequest endereco;
}
