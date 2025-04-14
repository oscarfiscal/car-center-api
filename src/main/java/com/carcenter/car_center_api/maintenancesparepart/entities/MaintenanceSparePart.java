package com.carcenter.car_center_api.maintenancesparepart.entities;

import com.carcenter.car_center_api.maintenance.entities.Maintenance;
import com.carcenter.car_center_api.sparepart.entities.SparePart;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "maintenance_spare_parts")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceSparePart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer quantity;      // unidades

    @Column(name = "estimated_time", nullable = false)
    private Integer estimatedTime;  // tiempo_estimado

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spare_part_id", nullable = false)
    private SparePart sparePart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maintenance_id", nullable = false)
    private Maintenance maintenance;
}