package com.carcenter.car_center_api.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Entidad que representa el mantenimiento de un vehículo.
 * Cada Maintenance puede tener fotos, repuestos, servicios, etc.
 */
@Entity
@Table(name = "maintenances")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Maintenance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;        // Fecha en que inicia o se registra el mantenimiento
    private String state;          // PENDIENTE, EN_PROGRESS, DONE, etc.
    private Double estimatedHours;
    private Double actualHours;
    private Double budgetLimit;    // Presupuesto máximo que define el cliente

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "mechanic_id")
    private Mechanic mechanic;

    @OneToMany(mappedBy = "maintenance", cascade = CascadeType.ALL)
    private List<Photo> photos;

    @OneToMany(mappedBy = "maintenance", cascade = CascadeType.ALL)
    private List<MaintenanceService> maintenanceServices;

    @OneToMany(mappedBy = "maintenance", cascade = CascadeType.ALL)
    private List<MaintenanceSparePart> maintenanceSpareParts;
}
