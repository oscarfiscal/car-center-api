package com.carcenter.car_center_api.maintenance.dtos;

import com.carcenter.car_center_api.maintenance.entities.MaintenanceStatus;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaintenanceResponse {

    private Long id;
    private String description;
    private Double limitBudget;
    private MaintenanceStatus status;
    private Integer hoursWorked;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long clientId;
    private Long vehicleId;
    private Long mechanicId;
}