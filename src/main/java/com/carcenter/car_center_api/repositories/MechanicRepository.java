package com.carcenter.car_center_api.repositories;

import com.carcenter.car_center_api.entities.Mechanic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * Repositorio para acceso a datos de Mechanic.
 */
public interface MechanicRepository extends JpaRepository<Mechanic, Long> {

    @Query("""
      SELECT m 
      FROM Mechanic m
      WHERE m.status = 'AVAILABLE'
      ORDER BY (
        SELECT COALESCE(SUM(mt.actualHours), 0)
        FROM Maintenance mt
        WHERE mt.mechanic = m
          AND mt.date >= :oneMonthAgo
      ) ASC
      """)
    List<Mechanic> findTopMechanicsWithLowestHours(@Param("oneMonthAgo") LocalDate oneMonthAgo,
                                                   Pageable pageable);
}
