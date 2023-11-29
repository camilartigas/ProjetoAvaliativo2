package com.medicationManagement.MedicationManagement.controller;

import com.medicationManagement.MedicationManagement.dto.FarmaciaRequest;
import com.medicationManagement.MedicationManagement.model.Farmacia;
import com.medicationManagement.MedicationManagement.service.FarmaciaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/farmacias")
public class FarmaciaController {

    private final FarmaciaService farmaciaService;

    @Autowired
    public FarmaciaController(FarmaciaService farmaciaService) {
        this.farmaciaService = farmaciaService;
    }

    @GetMapping
    public ResponseEntity<List<Farmacia>> buscarTodasFarmacias() {
        List<Farmacia> farmacias = farmaciaService.listarTodasFarmacias();
        return ResponseEntity.ok(farmacias);
    }

    @GetMapping("/{cnpj}")
    public ResponseEntity<?> buscarFarmaciaPorCnpj(@PathVariable Long cnpj) {
        Farmacia farmacia = farmaciaService.obterFarmaciaPorCnpj(cnpj);

        if (farmacia != null) {
            return ResponseEntity.ok(farmacia);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Farmacia> cadastrarFarmacia(@Valid @RequestBody FarmaciaRequest farmaciaRequest) throws URISyntaxException {
        Farmacia novaFarmacia = farmaciaService.cadastrarFarmacia(farmaciaRequest);
        return ResponseEntity.created(new URI("/farmacias/" + novaFarmacia.getCnpj())).body(novaFarmacia);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
