package com.carcenter.car_center_api.photo.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhotoResponse {

    private Long id;
    private String path;
    private Long maintenanceId;
}
