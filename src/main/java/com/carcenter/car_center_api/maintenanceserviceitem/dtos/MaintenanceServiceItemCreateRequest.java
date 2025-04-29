package com.carcenter.car_center_api.maintenanceserviceitem.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request body for creating a new maintenance service item")
public class MaintenanceServiceItemCreateRequest {

    @NotNull(message = "Service ID must not be null")
    @Schema(description = "ID of the mechanical service", example = "1", required = true)
    private Long serviceId;

    @NotNull(message = "Maintenance ID must not be null")
    @Schema(description = "ID of the maintenance associated", example = "10", required = true)
    private Long maintenanceId;

    @NotNull(message = "Estimated time must not be null")
    @Min(value = 0, message = "Estimated time must be 0 or greater")
    @Schema(description = "Estimated time (hours) for the service", example = "2", required = true)
    private Integer estimatedTime;
}
