package com.carcenter.car_center_api.maintenanceserviceitem.exceptions;

public class MaintenanceServiceItemNotFoundException extends RuntimeException {
    public MaintenanceServiceItemNotFoundException(String message) {
        super(message);
    }
}
