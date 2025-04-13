package com.carcenter.car_center_api.maintenance.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceCreateRequest {

    @NotNull
    private Long clientId;

    @NotNull
    private Long vehicleId;

    @NotBlank
    @Size(max = 255)
    private String description;

    @NotNull
    private Double limitBudget;
}