package com.carcenter.car_center_api.maintenance.exceptions;

public class MechanicNotFoundException extends RuntimeException {
    public MechanicNotFoundException(String message) {
        super(message);
    }
}
