package com.carcenter.car_center_api.maintenance.services;

import com.carcenter.car_center_api.maintenance.dtos.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MaintenanceServiceInterface {

    MaintenanceResponse create(MaintenanceCreateRequest request);

    MaintenanceResponse getById(Long id);

    Page<MaintenanceResponse> getAll(Pageable pageable);

    MaintenanceResponse update(Long id, MaintenanceUpdateRequest request);

    void delete(Long id);
}