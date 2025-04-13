package com.carcenter.car_center_api.maintenance.dtos;

import com.carcenter.car_center_api.maintenance.entities.MaintenanceStatus;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceUpdateRequest {

    @NotBlank
    @Size(max = 255)
    private String description;

    @NotNull
    private Double limitBudget;

    @NotNull
    private MaintenanceStatus status;

    private Long mechanicId;

    private Double hoursWorked;
}