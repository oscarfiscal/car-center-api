package com.carcenter.car_center_api.maintenance.dtos;

import com.carcenter.car_center_api.maintenance.entities.MaintenanceStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Basic response for maintenance operations")
public class MaintenanceResponse {

    @Schema(description = "Maintenance ID", example = "1")
    private Long id;

    @Schema(description = "Short description of the maintenance", example = "Oil change and brake inspection")
    private String description;

    @Schema(description = "Maximum budget allowed for the maintenance", example = "2500000")
    private BigDecimal limitBudget;

    @Schema(description = "Current status of the maintenance", example = "IN_PROGRESS")
    private MaintenanceStatus status;

    @Schema(description = "Total hours worked", example = "5")
    private Integer hoursWorked;

    @Schema(description = "Maintenance start date", example = "2024-04-26")
    private LocalDate startDate;

    @Schema(description = "Maintenance end date", example = "2024-04-28")
    private LocalDate endDate;

    @Schema(description = "Associated client ID", example = "10")
    private Long clientId;

    @Schema(description = "Associated vehicle ID", example = "20")
    private Long vehicleId;

    @Schema(description = "Assigned mechanic ID", example = "30")
    private Long mechanicId;
}
