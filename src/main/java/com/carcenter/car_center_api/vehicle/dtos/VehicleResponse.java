package com.carcenter.car_center_api.vehicle.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleResponse {

    private Long id;
    private String plate;
    private String brand;
    private String model;
    private Integer year;
    private String color;
    private Long clientId;
}