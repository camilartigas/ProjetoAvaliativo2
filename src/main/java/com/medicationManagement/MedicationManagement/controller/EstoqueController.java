package com.medicationManagement.MedicationManagement.controller;

import com.medicationManagement.MedicationManagement.dto.EstoqueDetalheDTO;
import com.medicationManagement.MedicationManagement.dto.EstoqueRequest;
import com.medicationManagement.MedicationManagement.dto.EstoqueResponse;
import com.medicationManagement.MedicationManagement.exception.FarmaciaNotFoundException;
import com.medicationManagement.MedicationManagement.exception.MedicamentoNotFoundException;
import com.medicationManagement.MedicationManagement.exception.QuantidadeInvalidaException;
import com.medicationManagement.MedicationManagement.service.EstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<?> adicionarMedicamentoAoEstoque(@RequestBody EstoqueRequest estoqueRequest) {
        try {
            EstoqueResponse response = estoqueService.adicionarMedicamentoAoEstoque(estoqueRequest);
            return ResponseEntity.ok(response);
        } catch (FarmaciaNotFoundException | MedicamentoNotFoundException | QuantidadeInvalidaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno no servidor");
        }
    }

    @DeleteMapping
    public ResponseEntity<Object> venderMedicamento(@RequestBody EstoqueRequest estoqueRequest) {
        try {
            EstoqueResponse estoqueResponse = estoqueService.venderMedicamentoDoEstoque(estoqueRequest);
            return ResponseEntity.ok(estoqueResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
