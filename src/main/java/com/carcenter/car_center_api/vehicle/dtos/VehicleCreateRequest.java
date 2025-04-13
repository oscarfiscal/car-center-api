package com.carcenter.car_center_api.vehicle.dtos;

import com.carcenter.car_center_api.brand.entities.Brand;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleCreateRequest {

    @NotBlank
    @Size(max = 10)
    private String plate;

    @NotBlank
    @Size(max = 50)
    private Brand brand;

    @NotBlank
    @Size(max = 50)
    private String model;

    @NotNull
    @Min(1900)
    @Max(2100)
    private Integer year;

    @NotBlank
    @Size(max = 20)
    private String color;

    @NotNull
    private Long clientId;
}