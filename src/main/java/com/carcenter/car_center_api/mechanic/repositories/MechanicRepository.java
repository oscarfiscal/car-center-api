package com.carcenter.car_center_api.mechanic.repositories;

import com.carcenter.car_center_api.mechanic.entities.Mechanic;
import com.carcenter.car_center_api.mechanic.entities.MechanicStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MechanicRepository extends JpaRepository<Mechanic, Long> {

    boolean existsByDocumentTypeAndDocument(String documentType, Integer document);

    List<Mechanic> findByStatus(MechanicStatus status);

    @Query("""
      SELECT m 
      FROM Mechanic m
      WHERE m.status = :status
      ORDER BY (
        SELECT COALESCE(SUM(mt.hoursWorked), 0)
        FROM Maintenance mt
        WHERE mt.mechanic = m
          AND mt.endDate >= :startDate
      ) ASC
      """)
    List<Mechanic> findTopByStatusAndHoursSince(
            @Param("status") MechanicStatus status,
            @Param("startDate") LocalDate startDate,
            Pageable pageable
    );
}

