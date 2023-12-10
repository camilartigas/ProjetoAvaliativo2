package com.medicationManagement.MedicationManagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "MEDICAMENTOS")
public class Medicamento {

    @Id
    private Integer numeroRegistro;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String laboratorio;

    @Column(nullable = false)
    private String dosagem;

    private String descricao;

    @Column(nullable = false)
    private Float preco;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMedicamento tipoMedicamento;
}
