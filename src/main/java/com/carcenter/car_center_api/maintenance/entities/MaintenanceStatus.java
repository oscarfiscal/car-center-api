package com.carcenter.car_center_api.maintenance.entities;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Status values for maintenance progress tracking")
public enum MaintenanceStatus {

    @Schema(description = "Maintenance is created but not yet started")
    PENDING,

    @Schema(description = "Maintenance is currently in progress")
    IN_PROGRESS,

    @Schema(description = "Maintenance has been completed")
    COMPLETED
}
