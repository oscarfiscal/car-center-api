package com.carcenter.car_center_api.dtos.mechanic;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO para crear un mecánico.
 */
@Data
public class MechanicCreateRequest {

    @NotBlank(message = "First name is required")
    private String firstName;

    private String secondName;

    @NotBlank(message = "First last name is required")
    private String firstLastName;

    private String secondLastName;

    @NotBlank(message = "Document is required")
    private String document;

    @NotBlank(message = "Document type is required")
    private String documentType;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Cellphone is required")
    private String cellphone;

    @NotBlank(message = "Address is required")
    private String address;

    // Estado inicial: AVAILABLE o BUSY
    private String status;
}