package com.medicationManagement.MedicationManagement.controller;

import com.medicationManagement.MedicationManagement.dto.EstoqueDetalheDTO;
import com.medicationManagement.MedicationManagement.model.Estoque;
import com.medicationManagement.MedicationManagement.service.EstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/estoque")
public class EstoqueController {

    private final EstoqueService estoqueService;

    @Autowired
    public EstoqueController(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    @GetMapping("/{cnpj}")
    public ResponseEntity<List<EstoqueDetalheDTO>> consultarEstoquePorCnpj(@PathVariable Long cnpj) {
        List<EstoqueDetalheDTO> estoque = estoqueService.consultarEstoquePorCnpj(cnpj);
        if (estoque.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(estoque);
    }
}
