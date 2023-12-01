package com.medicationManagement.MedicationManagement.controller;

import com.medicationManagement.MedicationManagement.exception.RegistroDuplicadoException;
import com.medicationManagement.MedicationManagement.model.Medicamento;
import com.medicationManagement.MedicationManagement.service.MedicamentoService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicamentos")
public class MedicamentoController {

    private final MedicamentoService medicamentoService;

    @Autowired
    public MedicamentoController(MedicamentoService medicamentoService) {
        this.medicamentoService = medicamentoService;
    }

    @GetMapping
    public List<Medicamento> listarMedicamentos() {
        return medicamentoService.listarTodosMedicamentos();
    }

    @PostMapping
    public ResponseEntity<?> incluirMedicamento(@Valid @RequestBody Medicamento medicamento) {
        try {
            Medicamento novoMedicamento = medicamentoService.incluirMedicamento(medicamento);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoMedicamento);
        } catch (RegistroDuplicadoException | ConstraintViolationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
