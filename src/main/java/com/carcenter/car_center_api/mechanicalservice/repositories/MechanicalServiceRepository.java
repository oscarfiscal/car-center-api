package com.carcenter.car_center_api.mechanicalservice.repositories;

import com.carcenter.car_center_api.mechanicalservice.entities.MechanicalService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MechanicalServiceRepository extends JpaRepository<MechanicalService, Long> {

}
