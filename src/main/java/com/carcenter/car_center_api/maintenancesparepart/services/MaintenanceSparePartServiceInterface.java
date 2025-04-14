package com.carcenter.car_center_api.maintenancesparepart.services;

import com.carcenter.car_center_api.maintenancesparepart.dtos.MaintenanceSparePartCreateRequest;
import com.carcenter.car_center_api.maintenancesparepart.dtos.MaintenanceSparePartResponse;

import java.util.List;

public interface MaintenanceSparePartServiceInterface {
    List<MaintenanceSparePartResponse> getByMaintenance(Long maintenanceId);
    List<MaintenanceSparePartResponse> getBySparePart(Long sparePartId);
    MaintenanceSparePartResponse create(MaintenanceSparePartCreateRequest dto);
}
