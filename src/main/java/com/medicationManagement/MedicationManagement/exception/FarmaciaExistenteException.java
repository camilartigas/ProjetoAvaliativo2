package com.medicationManagement.MedicationManagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FarmaciaExistenteException extends RuntimeException {
    public FarmaciaExistenteException(String message) {
        super(message);
    }
}
