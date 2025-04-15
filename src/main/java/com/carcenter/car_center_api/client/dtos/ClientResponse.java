package com.carcenter.car_center_api.client.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientResponse {

    private Long id;
    private String documentType;
    private Integer document;
    private String firstName;
    private String secondName;
    private String firstLastName;
    private String secondLastName;
    private String cellphone;
    private String address;
    private String email;
}
