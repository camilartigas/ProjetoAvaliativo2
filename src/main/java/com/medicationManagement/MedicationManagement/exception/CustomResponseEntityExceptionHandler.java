package com.medicationManagement.MedicationManagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@RestControllerAdvice
public class CustomResponseEntityExceptionHandler {

    @ExceptionHandler(FarmaciaExistenteException.class)
    public final ResponseEntity<Object> handleFarmaciaExistenteException(FarmaciaExistenteException ex, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EnderecoInvalidoException.class)
    public final ResponseEntity<Object> handleEnderecoInvalidoException(EnderecoInvalidoException ex, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage("É obrigatório o preenchimento do CEP");
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    static class ErrorMessage {
        private final String message;

        public ErrorMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
