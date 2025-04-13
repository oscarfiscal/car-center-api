package com.carcenter.car_center_api.vehicle.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleUpdateRequest {

    @NotBlank
    @Size(max = 50)
    private String brand;

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
}
