package com.carcenter.car_center_api.photo.services;

import com.carcenter.car_center_api.photo.dtos.PhotoCreateRequest;
import com.carcenter.car_center_api.photo.dtos.PhotoResponse;

import java.util.List;

public interface PhotoServiceInterface {
    PhotoResponse create(PhotoCreateRequest dto);
    List<PhotoResponse> getByMaintenanceId(Long maintenanceId);
    void delete(Long id);
}
