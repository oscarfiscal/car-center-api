package com.carcenter.car_center_api.maintenancesparepart.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceSparePartCreateRequest {

    @NotNull
    private Long sparePartId;

    @NotNull
    private Long maintenanceId;

    @NotNull @Min(1)
    private Integer quantity;

    @NotNull @PositiveOrZero
    private Integer estimatedTime;
}
