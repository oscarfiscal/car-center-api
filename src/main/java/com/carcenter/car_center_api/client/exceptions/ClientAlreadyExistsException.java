package com.carcenter.car_center_api.client.exceptions;

public class ClientAlreadyExistsException extends RuntimeException {
    public ClientAlreadyExistsException(String message) {
        super(message);
    }
}