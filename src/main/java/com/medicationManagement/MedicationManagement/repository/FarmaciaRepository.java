package com.medicationManagement.MedicationManagement.repository;

import com.medicationManagement.MedicationManagement.model.Farmacia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FarmaciaRepository extends JpaRepository<Farmacia, Long> {
    Farmacia findByCnpj(Long cnpj);
    boolean existsByCnpj(Long cnpj);

}
