package com.medicationManagement.MedicationManagement.model;

import jakarta.persistence.*;

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



    // Construtores
    public Medicamento() {
    }

    public Medicamento(Integer numeroRegistro, String nome, String laboratorio, String dosagem, String descricao, Float preco, TipoMedicamento tipoMedicamento) {
        this.numeroRegistro = numeroRegistro;
        this.nome = nome;
        this.laboratorio = laboratorio;
        this.dosagem = dosagem;
        this.descricao = descricao;
        this.preco = preco;
        this.tipoMedicamento = tipoMedicamento;
    }


    // Getters e Setters

    public Integer getNumeroRegistro() {
        return numeroRegistro;
    }

    public void setNumeroRegistro(Integer numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    public String getDosagem() {
        return dosagem;
    }

    public void setDosagem(String dosagem) {
        this.dosagem = dosagem;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Float getPreco() {
        return preco;
    }

    public void setPreco(Float preco) {
        this.preco = preco;
    }

    public TipoMedicamento getTipoMedicamento() {
        return tipoMedicamento;
    }

    public void setTipoMedicamento(TipoMedicamento tipoMedicamento) {
        this.tipoMedicamento = tipoMedicamento;
    }
}
