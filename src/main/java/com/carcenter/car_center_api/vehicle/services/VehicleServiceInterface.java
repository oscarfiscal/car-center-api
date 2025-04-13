package com.carcenter.car_center_api.vehicle.services;

import com.carcenter.car_center_api.vehicle.dtos.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VehicleServiceInterface {
    VehicleResponse create(VehicleCreateRequest dto);
    VehicleResponse getById(Long id);
    Page<VehicleResponse> getAll(Pageable pageable);
    List<VehicleResponse> getByClient(Long clientId);
    VehicleResponse update(Long id, VehicleUpdateRequest dto);
    void delete(Long id);
}
