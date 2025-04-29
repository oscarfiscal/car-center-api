package com.carcenter.car_center_api.maintenancesparepart.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response object for maintenance spare part assignment")
public class MaintenanceSparePartResponse {

    @Schema(description = "ID of the maintenance spare part association", example = "5")
    private Long id;

    @Schema(description = "Quantity of spare parts assigned", example = "3")
    private Integer quantity;

    @Schema(description = "Estimated installation time in hours", example = "2")
    private Integer estimatedTime;

    @Schema(description = "ID of the spare part", example = "1")
    private Long sparePartId;

    @Schema(description = "ID of the maintenance", example = "10")
    private Long maintenanceId;
}
