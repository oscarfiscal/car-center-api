package com.carcenter.car_center_api.maintenancesparepart.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaintenanceSparePartResponse {

    private Long id;
    private Integer quantity;
    private Integer estimatedTime;
    private Long sparePartId;
    private Long maintenanceId;

}