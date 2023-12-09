package com.medicationManagement.MedicationManagement.service;

import com.medicationManagement.MedicationManagement.dto.MedicamentoRequest;
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

    public Medicamento incluirMedicamento(MedicamentoRequest medicamentoRequest) {
        Integer numeroRegistro = medicamentoRequest.getNumeroRegistro();

        // Verifica se o número de registro já está cadastrado
        if (medicamentoRepository.existsByNumeroRegistro(numeroRegistro)) {
            throw new RegistroDuplicadoException("Já existe um medicamento cadastrado com este número de registro.");
        }

        // Validação dos campos obrigatórios usando Bean Validation
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<MedicamentoRequest>> violations = validator.validate(medicamentoRequest);
        if (!violations.isEmpty()) {
            StringBuilder errorMsg = new StringBuilder("Erro ao cadastrar medicamento: ");
            for (ConstraintViolation<MedicamentoRequest> violation : violations) {
                errorMsg.append(violation.getMessage()).append("; ");
            }
            throw new ConstraintViolationException(errorMsg.toString(), violations);
        }

        Medicamento novoMedicamento = criarMedicamentoAPartirDoRequest(medicamentoRequest);
        return medicamentoRepository.save(novoMedicamento);
    }

    private Medicamento criarMedicamentoAPartirDoRequest(MedicamentoRequest medicamentoRequest) {
        Medicamento novoMedicamento = new Medicamento();
        novoMedicamento.setNumeroRegistro(medicamentoRequest.getNumeroRegistro());
        novoMedicamento.setNome(medicamentoRequest.getNome());
        novoMedicamento.setLaboratorio(medicamentoRequest.getLaboratorio());
        novoMedicamento.setDosagem(medicamentoRequest.getDosagem());
        novoMedicamento.setDescricao(medicamentoRequest.getDescricao());
        novoMedicamento.setPreco(medicamentoRequest.getPreco());
        novoMedicamento.setTipoMedicamento(medicamentoRequest.getTipo());
        return novoMedicamento;
    }
    public boolean existeMedicamentoComNumeroRegistro(Integer numeroRegistro) {
        return medicamentoRepository.existsByNumeroRegistro(numeroRegistro);
    }
}
