package com.carcenter.car_center_api.dtos.mechanic;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO para actualizar campos de un mecánico.
 */
@Data
public class MechanicUpdateRequest {

    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @Email(message = "Invalid email format")
    private String email;

    private String cellphone;
    private String address;
    private String status;
}
