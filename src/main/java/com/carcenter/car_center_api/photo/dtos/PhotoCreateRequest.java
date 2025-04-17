package com.carcenter.car_center_api.photo.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhotoCreateRequest {

    @NotNull
    private MultipartFile file;

    @NotNull
    private Long maintenanceId;
}
