package com.carcenter.car_center_api.vehicle.repositories;

import com.carcenter.car_center_api.vehicle.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    boolean existsByPlate(String plate);
    List<Vehicle> findByClientId(Long clientId);
}
