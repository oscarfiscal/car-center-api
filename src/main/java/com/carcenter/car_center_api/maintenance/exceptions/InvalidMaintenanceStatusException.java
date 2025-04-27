package com.carcenter.car_center_api.maintenance.exceptions;

public class InvalidMaintenanceStatusException extends RuntimeException {
    public InvalidMaintenanceStatusException(String message) {
        super(message);
    }
}
