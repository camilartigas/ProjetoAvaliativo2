package com.medicationManagement.MedicationManagement.service;

import com.medicationManagement.MedicationManagement.model.Estoque;
import com.medicationManagement.MedicationManagement.repository.EstoqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstoqueService {

    private final EstoqueRepository estoqueRepository;

    @Autowired
    public EstoqueService(EstoqueRepository estoqueRepository) {
        this.estoqueRepository = estoqueRepository;
    }

    public List<Estoque> consultarEstoquePorCnpj(Long cnpj) {
        return estoqueRepository.findByCnpj(cnpj);
    }
}
