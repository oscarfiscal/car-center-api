package com.carcenter.car_center_api.photo.repositories;

import com.carcenter.car_center_api.photo.entities.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<Photo> findByMaintenanceId(Long maintenanceId);
}
