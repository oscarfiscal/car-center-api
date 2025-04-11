package com.carcenter.car_center_api.entities;

import jakarta.persistence.*;
import lombok.*;

/**
 * Representa la relación (Many-to-Many) entre Maintenance y SparePart.
 * Se nombra "MaintenanceSparePart" para mayor claridad y consistencia.
 */
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

    @ManyToOne
    @JoinColumn(name = "spare_part_id", nullable = false)
    private SparePart sparePart;

    @ManyToOne
    @JoinColumn(name = "maintenance_id", nullable = false)
    private Maintenance maintenance;

    private Integer quantity;
    private Double unitPrice;
    private Double discount;
}