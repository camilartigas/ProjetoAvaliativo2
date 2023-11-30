package com.medicationManagement.MedicationManagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EnderecoInvalidoException extends RuntimeException {
    public EnderecoInvalidoException(String message) {
        super(message);
    }
}
