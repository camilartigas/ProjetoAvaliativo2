package com.medicationManagement.MedicationManagement.service;

import com.medicationManagement.MedicationManagement.model.Medicamento;
import com.medicationManagement.MedicationManagement.repository.MedicamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicamentoService {

    private final MedicamentoRepository medicamentoRepository;

    @Autowired
    public MedicamentoService(MedicamentoRepository medicamentoRepository) {
        this.medicamentoRepository = medicamentoRepository;
    }

    public List<Medicamento> listarTodosMedicamentos() {
        return medicamentoRepository.findAll();
    }
}
