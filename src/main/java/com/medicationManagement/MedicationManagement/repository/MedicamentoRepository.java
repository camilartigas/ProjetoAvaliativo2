package com.medicationManagement.MedicationManagement.repository;

import com.medicationManagement.MedicationManagement.model.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicamentoRepository extends JpaRepository<Medicamento, Integer> {

    boolean existsByNumeroRegistro(Integer numeroRegistro);

    Optional<Medicamento> findByNumeroRegistro(Integer nroRegistro);

}
