package com.carcenter.car_center_api.maintenancesparepart.repositories;

import com.carcenter.car_center_api.maintenancesparepart.dtos.MaintenanceSparePartCreateRequest;
import com.carcenter.car_center_api.maintenancesparepart.dtos.MaintenanceSparePartResponse;
import com.carcenter.car_center_api.maintenancesparepart.entities.MaintenanceSparePart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaintenanceSparePartRepository extends JpaRepository<MaintenanceSparePart, Long> {
    List<MaintenanceSparePart> findByMaintenanceId(Long maintenanceId);
    List<MaintenanceSparePart> findBySparePartId(Long sparePartId);
}