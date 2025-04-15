package com.carcenter.car_center_api.mechanicalservice.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MechanicalService {

    private Integer id;
    private String name;
    private Double price;
}