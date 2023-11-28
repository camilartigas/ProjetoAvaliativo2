package com.medicationManagement.MedicationManagement.controller;

import com.medicationManagement.MedicationManagement.model.Farmacia;
import com.medicationManagement.MedicationManagement.service.FarmaciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


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
}
