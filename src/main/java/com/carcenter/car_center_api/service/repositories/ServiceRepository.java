package com.carcenter.car_center_api.service.repositories;

import com.carcenter.car_center_api.service.entities.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {

}
