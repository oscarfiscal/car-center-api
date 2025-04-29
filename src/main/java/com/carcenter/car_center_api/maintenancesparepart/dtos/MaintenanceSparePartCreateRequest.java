package com.carcenter.car_center_api.maintenancesparepart.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request to assign a spare part to a maintenance")
public class MaintenanceSparePartCreateRequest {

    @NotNull(message = "Spare part ID must not be null")
    @Schema(description = "ID of the spare part", example = "1")
    private Long sparePartId;

    @NotNull(message = "Maintenance ID must not be null")
    @Schema(description = "ID of the maintenance", example = "10")
    private Long maintenanceId;

    @NotNull(message = "Quantity must not be null")
    @Min(value = 1, message = "Quantity must be at least 1")
    @Schema(description = "Quantity of spare parts to assign", example = "3")
    private Integer quantity;

    @NotNull(message = "Estimated time must not be null")
    @PositiveOrZero(message = "Estimated time must be zero or positive")
    @Schema(description = "Estimated time in hours to install or use the spare part", example = "2")
    private Integer estimatedTime;
}
