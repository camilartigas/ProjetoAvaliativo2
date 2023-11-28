package com.medicationManagement.MedicationManagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "FARMACIAS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Farmacia {

    @Id // Chave primária
    private Long cnpj;

    @Column(nullable = false)
    private String razaoSocial;

    @Column(nullable = false)
    private String nomeFantasia;

    @Column(nullable = false)
    private String email;

    private String telefone;

    @Column(nullable = false)
    private String celular;

    @Embedded
    private Endereco endereco;
}
