package com.carcenter.car_center_api.maintenance.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceCreateRequest {

    @NotNull
    private Integer clientDocument;

    @NotBlank
    @Size(max = 10)
    private String vehiclePlate;

    @NotBlank
    @Size(max = 255)
    private String description;

    @NotNull
    @Positive
    private Double limitBudget;
}
