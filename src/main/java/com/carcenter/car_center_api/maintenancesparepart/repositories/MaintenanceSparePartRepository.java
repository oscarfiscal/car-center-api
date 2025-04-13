package com.carcenter.car_center_api.maintenancesparepart.repositories;

import com.carcenter.car_center_api.maintenancesparepart.entities.MaintenanceSparePart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaintenanceSparePartRepository extends JpaRepository<MaintenanceSparePart, Long> {

}