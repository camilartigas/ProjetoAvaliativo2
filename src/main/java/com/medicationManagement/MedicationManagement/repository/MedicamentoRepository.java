package com.medicationManagement.MedicationManagement.repository;

import com.medicationManagement.MedicationManagement.model.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicamentoRepository extends JpaRepository<Medicamento, Integer> {

}
