package com.carcenter.car_center_api.mechanic.dtos;

import com.carcenter.car_center_api.mechanic.entities.MechanicStatus;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MechanicResponse {

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
    private MechanicStatus status;
}
