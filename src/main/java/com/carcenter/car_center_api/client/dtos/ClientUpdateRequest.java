package com.carcenter.car_center_api.client.dtos;

import jakarta.validation.constraints.*;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientUpdateRequest {

    @Size(max = 30)
    private String firstName;

    @Size(max = 30)
    private String secondName;

    @Size(max = 30)
    private String firstLastName;

    @Size(max = 30)
    private String secondLastName;

    @Size(max = 15)
    private String cellphone;

    @Size(max = 20)
    private String address;

    @Email
    @Size(max = 100)
    private String email;
}

