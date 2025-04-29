package com.carcenter.car_center_api.vehicle.exceptions;

import com.carcenter.car_center_api.global.ExceptionResponseBuilder;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice(basePackages = "com.carcenter.car_center_api.vehicle.controllers")
public class VehicleExceptionHandler {

    @ExceptionHandler(PlateAlreadyExistsException.class)
    public ResponseEntity<Object> handlePlateAlreadyExistsException(PlateAlreadyExistsException ex) {
        return new ResponseEntity<>(
                ExceptionResponseBuilder.build(
                        HttpStatus.CONFLICT,
                        ex.getMessage(),
                        "Plate already exists"
                ),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(VehicleNotFoundException.class)
    public ResponseEntity<Object> handleVehicleNotFoundException(VehicleNotFoundException ex) {
        return new ResponseEntity<>(
                ExceptionResponseBuilder.build(
                        HttpStatus.NOT_FOUND,
                        ex.getMessage(),
                        "Vehicle not found"
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
