package com.carcenter.car_center_api.maintenanceserviceitem.services;

import com.carcenter.car_center_api.maintenanceserviceitem.dtos.MaintenanceServiceItemCreateRequest;
import com.carcenter.car_center_api.maintenanceserviceitem.dtos.MaintenanceServiceItemResponse;


public interface MaintenanceServiceItemServiceInterface {
    MaintenanceServiceItemResponse create(MaintenanceServiceItemCreateRequest dto);
    MaintenanceServiceItemResponse getById(Long id);

}