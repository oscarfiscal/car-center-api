package com.carcenter.car_center_api.maintenance.dtos;

import com.carcenter.car_center_api.maintenance.entities.MaintenanceStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request to update a maintenance record")
public class MaintenanceUpdateRequest {

    @NotBlank
    @Size(max = 255)
    @Schema(description = "Updated maintenance description", example = "Updated: Oil change, tire rotation and brake inspection")
    private String description;

    @NotNull
    @Positive
    @Schema(description = "Updated maximum budget allowed for the maintenance", example = "3000000")
    private BigDecimal limitBudget;

    @NotNull
    @Schema(description = "Updated status of the maintenance", example = "COMPLETED")
    private MaintenanceStatus status;

    @Schema(description = "ID of the assigned mechanic (optional)", example = "5")
    private Long mechanicId;

    @Schema(description = "Total updated hours worked on the maintenance", example = "6")
    private Integer hoursWorked;
}
