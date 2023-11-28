package com.medicationManagement.MedicationManagement.repository;

import com.medicationManagement.MedicationManagement.model.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Long> {

}
