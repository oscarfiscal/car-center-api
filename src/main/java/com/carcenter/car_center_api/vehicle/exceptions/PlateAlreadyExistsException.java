package com.carcenter.car_center_api.vehicle.exceptions;

public class PlateAlreadyExistsException extends RuntimeException {
    public PlateAlreadyExistsException(String message) {
        super(message);
    }
}
