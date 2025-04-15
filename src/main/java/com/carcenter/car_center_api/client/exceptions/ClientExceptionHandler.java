package com.carcenter.car_center_api.client.exceptions;

import com.carcenter.car_center_api.client.exceptions.ClientAlreadyExistsException;
import com.carcenter.car_center_api.client.exceptions.ClientNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice(basePackages = "com.carcenter.car_center_api.client.controllers")
public class ClientExceptionHandler {

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<Object> handleClientNotFoundException(ClientNotFoundException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", "Client not found");

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ClientAlreadyExistsException.class)
    public ResponseEntity<Object> handleClientAlreadyExistsException(ClientAlreadyExistsException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());
        body.put("status", HttpStatus.CONFLICT.value());
        body.put("error", "Client already exists");

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "Unexpected error");
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("error", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });


        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Validation error");

        body.put("errors", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

}
