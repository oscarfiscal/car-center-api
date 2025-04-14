package com.carcenter.car_center_api.maintenance.repositories;


import com.carcenter.car_center_api.maintenance.entities.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {
}