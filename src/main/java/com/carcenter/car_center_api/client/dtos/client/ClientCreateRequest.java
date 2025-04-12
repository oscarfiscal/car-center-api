package com.carcenter.car_center_api.client.dtos.client;

import jakarta.validation.constraints.*;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientCreateRequest {

    @NotBlank
    @Size(max = 2)
    private String documentType;

    @NotNull
    private Integer document;

    @NotBlank
    @Size(max = 30)
    private String firstName;

    @NotBlank
    @Size(max = 30)
    private String secondName;

    @NotBlank
    @Size(max = 30)
    private String firstLastName;

    @NotBlank
    @Size(max = 30)
    private String secondLastName;

    @NotBlank
    @Size(max = 10)
    private String cellphone;

    @NotBlank
    @Size(max = 20)
    private String address;

    @NotBlank
    @Email
    @Size(max = 100)
    private String email;
}
