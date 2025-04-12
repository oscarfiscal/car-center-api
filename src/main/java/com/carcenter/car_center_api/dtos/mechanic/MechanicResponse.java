package com.carcenter.car_center_api.dtos.mechanic;

import lombok.Data;

@Data
public class MechanicResponse {
    private Long id;
    private String firstName;
    private String secondName;
    private String firstLastName;
    private String secondLastName;
    private String document;
    private String documentType;
    private String email;
    private String cellphone;
    private String address;
    private String status;
}
