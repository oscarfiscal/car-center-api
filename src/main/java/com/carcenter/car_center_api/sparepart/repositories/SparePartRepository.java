package com.carcenter.car_center_api.sparepart.repositories;

import com.carcenter.car_center_api.sparepart.entities.SparePart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SparePartRepository extends JpaRepository<SparePart, Long> {
    boolean existsByName(String name);
}
