package com.carcenter.car_center_api.maintenanceserviceitem.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Response body representing a maintenance service item")
public class MaintenanceServiceItemResponse {

    @Schema(description = "ID of the maintenance service item", example = "1")
    private Long id;

    @Schema(description = "Estimated time (in hours) for the service", example = "3")
    private Integer estimatedTime;

    @Schema(description = "ID of the associated mechanical service", example = "5")
    private Long serviceId;

    @Schema(description = "ID of the associated maintenance", example = "12")
    private Long maintenanceId;
}
