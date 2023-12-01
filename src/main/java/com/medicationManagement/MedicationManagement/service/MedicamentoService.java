package com.medicationManagement.MedicationManagement.service;

import com.medicationManagement.MedicationManagement.exception.RegistroDuplicadoException;
import com.medicationManagement.MedicationManagement.model.Medicamento;
import com.medicationManagement.MedicationManagement.repository.MedicamentoRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

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

    public Medicamento incluirMedicamento(Medicamento medicamento) {
        // Verificar se o número de registro já está cadastrado
        if (medicamentoRepository.existsByNumeroRegistro(medicamento.getNumeroRegistro())) {
            throw new RegistroDuplicadoException("Erro ao cadastrar medicamento: Número de registro já existente.");
        }

        // Validar campos obrigatórios usando Bean Validation
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Medicamento>> violations = validator.validate(medicamento);
        if (!violations.isEmpty()) {
            StringBuilder errorMsg = new StringBuilder("Erro ao cadastrar medicamento: ");
            for (ConstraintViolation<Medicamento> violation : violations) {
                errorMsg.append(violation.getMessage()).append("; ");
            }
            throw new ConstraintViolationException(errorMsg.toString(), violations);
        }

        return medicamentoRepository.save(medicamento);
    }
}
