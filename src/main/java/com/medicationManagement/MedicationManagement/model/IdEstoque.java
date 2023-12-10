package com.medicationManagement.MedicationManagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Objects;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdEstoque implements Serializable {
private Long cnpj;
private Integer nroRegistro;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IdEstoque idEstoque = (IdEstoque) o;
        return Objects.equals(cnpj, idEstoque.cnpj) && Objects.equals(nroRegistro, idEstoque.nroRegistro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cnpj, nroRegistro);
    }
}
