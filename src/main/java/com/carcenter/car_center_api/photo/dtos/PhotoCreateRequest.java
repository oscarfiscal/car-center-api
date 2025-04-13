package com.carcenter.car_center_api.photo.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhotoCreateRequest {

    @NotBlank
    @Size(max = 200)
    private String path;

    @NotNull
    private Long maintenanceId;
}
