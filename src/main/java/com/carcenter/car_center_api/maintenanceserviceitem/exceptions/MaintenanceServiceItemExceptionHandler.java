package com.carcenter.car_center_api.maintenanceserviceitem.exceptions;

import com.carcenter.car_center_api.global.ExceptionResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.carcenter.car_center_api.maintenanceserviceitem.controllers")
public class MaintenanceServiceItemExceptionHandler {

    @ExceptionHandler(MaintenanceServiceItemNotFoundException.class)
    public ResponseEntity<Object> handleMaintenanceServiceItemNotFoundException(MaintenanceServiceItemNotFoundException ex) {
        return new ResponseEntity<>(
                ExceptionResponseBuilder.build(
                        HttpStatus.NOT_FOUND,
                        ex.getMessage(),
                        "Maintenance Service Item not found"
                ),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        return new ResponseEntity<>(
                ExceptionResponseBuilder.build(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Unexpected error",
                        ex.getMessage()
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
