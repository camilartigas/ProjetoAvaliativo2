package com.medicationManagement.MedicationManagement.controller;

import com.medicationManagement.MedicationManagement.service.InicializacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/inicializacao")
public class InicializacaoController {

    private final InicializacaoService inicializacaoService;

    @Autowired
    public InicializacaoController(InicializacaoService inicializacaoService) {
        this.inicializacaoService = inicializacaoService;
    }

    @PostMapping
    public ResponseEntity<Void> inicializarDados() {
        inicializacaoService.popularDadosIniciais();
        return ResponseEntity.ok().build();
    }
}
