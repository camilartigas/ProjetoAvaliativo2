package com.medicationManagement.MedicationManagement.service;

import com.medicationManagement.MedicationManagement.model.Farmacia;
import com.medicationManagement.MedicationManagement.repository.FarmaciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FarmaciaService {

    private final FarmaciaRepository farmaciaRepository;

    @Autowired
    public FarmaciaService(FarmaciaRepository farmaciaRepository) {
        this.farmaciaRepository = farmaciaRepository;
    }

    public List<Farmacia> listarTodasFarmacias() {

        return farmaciaRepository.findAll();
    }

    public Farmacia obterFarmaciaPorCnpj(Long cnpj) {
        return farmaciaRepository.findByCnpj(cnpj);
    }
}
