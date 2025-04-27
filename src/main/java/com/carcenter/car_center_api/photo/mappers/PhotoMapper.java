package com.carcenter.car_center_api.photo.mappers;

import com.carcenter.car_center_api.photo.dtos.PhotoResponse;
import com.carcenter.car_center_api.photo.entities.Photo;
import org.springframework.stereotype.Component;

@Component
public class PhotoMapper {

    public PhotoResponse toResponse(Photo photo) {
        return PhotoResponse.builder()
                .id(photo.getId())
                .path(photo.getPath())
                .maintenanceId(photo.getMaintenance().getId())
                .build();
    }
}
