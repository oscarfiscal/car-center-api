package com.carcenter.car_center_api.maintenanceserviceitem.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaintenanceServiceItemResponse {

    private Long id;
    private Integer estimatedTime;
    private Long serviceId;
    private Long maintenanceId;
}