package com.carcenter.car_center_api.entities;

import jakarta.persistence.*;
import lombok.*;

/**
 * Representa la relación (Many-to-Many) entre Maintenance y Service.
 */
@Entity
@Table(name = "maintenance_services")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Clave foránea a Service
    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;

    // Clave foránea a Maintenance
    @ManyToOne
    @JoinColumn(name = "maintenance_id", nullable = false)
    private Maintenance maintenance;

    // Campos específicos de la relación: costo, descuento, etc.
    private Double laborCost;
    private Double discount;
}