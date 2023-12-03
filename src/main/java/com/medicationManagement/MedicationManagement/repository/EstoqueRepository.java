package com.medicationManagement.MedicationManagement.repository;

import com.medicationManagement.MedicationManagement.model.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Long> {

    List<Estoque> findByCnpj(Long cnpj);

    Optional<Estoque> findByCnpjAndNroRegistro(Long cnpj, Integer nroRegistro);

}
