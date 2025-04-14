package com.carcenter.car_center_api.maintenanceserviceitem.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceServiceItemCreateRequest {

    @NotNull
    private Long serviceId;

    @NotNull
    private Long maintenanceId;

    @NotNull
    @Min(0)
    private Integer estimatedTime;
}

