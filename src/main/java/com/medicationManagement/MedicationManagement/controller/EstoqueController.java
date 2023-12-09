package com.medicationManagement.MedicationManagement.controller;

import com.medicationManagement.MedicationManagement.dto.*;
import com.medicationManagement.MedicationManagement.exception.FarmaciaNotFoundException;
import com.medicationManagement.MedicationManagement.exception.MedicamentoNotFoundException;
import com.medicationManagement.MedicationManagement.exception.QuantidadeInvalidaException;
import com.medicationManagement.MedicationManagement.model.Farmacia;
import com.medicationManagement.MedicationManagement.service.EstoqueService;
import com.medicationManagement.MedicationManagement.service.FarmaciaService;
import com.medicationManagement.MedicationManagement.service.MedicamentoService;
import com.medicationManagement.MedicationManagement.service.TrocaMedicamentosService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/estoque")
public class EstoqueController {

    private final EstoqueService estoqueService;
    private final TrocaMedicamentosService trocaMedicamentosService;
    private final FarmaciaService farmaciaService;
    private final MedicamentoService medicamentoService;


    @Autowired
    public EstoqueController(EstoqueService estoqueService, TrocaMedicamentosService trocaMedicamentosService, FarmaciaService farmaciaService, MedicamentoService medicamentoService) {
        this.estoqueService = estoqueService;
        this.trocaMedicamentosService = trocaMedicamentosService;
        this.farmaciaService = farmaciaService;
        this.medicamentoService = medicamentoService;
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
        Farmacia farmaciaEncontrada = farmaciaService.obterFarmaciaPorCnpj(estoqueRequest.getCnpj());
        if (farmaciaEncontrada == null) {
            return ResponseEntity.badRequest().body("Falha na operação: CNPJ não encontrado no sistema.");
        }

        boolean medicamentoExiste = medicamentoService.existeMedicamentoComNumeroRegistro(estoqueRequest.getNroRegistro());
        if (!medicamentoExiste) {
            return ResponseEntity.badRequest().body("Falha na operação: Número de registro não encontrado no sistema.");
        }

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

    @PutMapping
    public ResponseEntity<Object> trocarMedicamentosEntreFarmacias(@RequestBody TrocaMedicamentosRequest request) {
        try {
            TrocaMedicamentosResponse response = trocaMedicamentosService.trocarMedicamentos(request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
