package com.medicationManagement.MedicationManagement.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "ESTOQUES")
@IdClass(IdEstoque.class)
public class Estoque implements Serializable {

    @Id
    private Long cnpj;
    @Id
    private Integer nroRegistro;


    @Column(nullable = false)
    private Integer quantidade;

    @Column(nullable = false)
    private LocalDateTime dataAtualizacao;

    @ManyToOne
    @JoinColumn(name = "cnpj", insertable = false, updatable = false)
    private Farmacia farmacia;

    @ManyToOne
    @JoinColumn(name = "nroRegistro", insertable = false, updatable = false)
    private Medicamento medicamento;


    // Construtores
    public Estoque(Long cnpj, Integer nroRegistro, Integer quantidade, LocalDateTime dataAtualizacao) {
        this.cnpj = cnpj;
        this.nroRegistro = nroRegistro;
        this.quantidade = quantidade;
        this.dataAtualizacao = dataAtualizacao;
    }
}
