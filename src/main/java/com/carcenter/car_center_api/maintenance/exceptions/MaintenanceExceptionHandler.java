package com.carcenter.car_center_api.maintenance.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice(basePackages = "com.carcenter.car_center_api.maintenance.controllers")
public class MaintenanceExceptionHandler {

    @ExceptionHandler(MaintenanceNotFoundException.class)
    public ResponseEntity<Object> handleMaintenanceNotFoundException(MaintenanceNotFoundException ex) {
        Map<String, Object> body = buildBody(HttpStatus.NOT_FOUND, ex.getMessage(), "Maintenance not found");
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MechanicNotFoundException.class)
    public ResponseEntity<Object> handleMechanicNotFoundException(MechanicNotFoundException ex) {
        Map<String, Object> body = buildBody(HttpStatus.NOT_FOUND, ex.getMessage(), "Mechanic not found");
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidMaintenanceStatusException.class)
    public ResponseEntity<Object> handleInvalidMaintenanceStatusException(InvalidMaintenanceStatusException ex) {
        Map<String, Object> body = buildBody(HttpStatus.BAD_REQUEST, ex.getMessage(), "Invalid maintenance status");
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        Map<String, Object> body = buildBody(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Map<String, Object> buildBody(HttpStatus status, String message, String error) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("message", message);
        body.put("error", error);
        return body;
    }
}
