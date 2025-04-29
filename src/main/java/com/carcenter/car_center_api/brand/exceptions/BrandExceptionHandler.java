package com.carcenter.car_center_api.brand.exceptions;

import com.carcenter.car_center_api.global.ExceptionResponseBuilder;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice(basePackages = "com.carcenter.car_center_api.vehicle.controllers")
public class BrandExceptionHandler {

    @ExceptionHandler(BrandNotFoundException.class)
    public ResponseEntity<Object> handleBrandNotFoundException(BrandNotFoundException ex) {
        return new ResponseEntity<>(
                ExceptionResponseBuilder.build(
                        HttpStatus.NOT_FOUND,
                        ex.getMessage(),
                        "Brand not found"
                ),
                HttpStatus.NOT_FOUND
        );
    }
}
