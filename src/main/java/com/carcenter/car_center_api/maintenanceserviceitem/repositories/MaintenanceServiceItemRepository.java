package com.carcenter.car_center_api.maintenanceserviceitem.repositories;

import com.carcenter.car_center_api.maintenanceserviceitem.entities.MaintenanceServiceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaintenanceServiceItemRepository extends JpaRepository<MaintenanceServiceItem, Long> {
    List<MaintenanceServiceItem> findByMaintenanceId(Long maintenanceId);
    List<MaintenanceServiceItem> findByServiceId(Long serviceId);
}
