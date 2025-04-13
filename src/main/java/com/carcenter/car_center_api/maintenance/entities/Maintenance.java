package com.carcenter.car_center_api.maintenance.entities;

import com.carcenter.car_center_api.client.entities.Client;
import com.carcenter.car_center_api.mechanic.entities.Mechanic;
import com.carcenter.car_center_api.vehicle.entities.Vehicle;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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

    @Column(nullable = false, length = 255)
    private String description;

    @Column(name = "limit_budget", nullable = false)
    private Double limitBudget;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MaintenanceStatus status;

    @Column(name = "hours_worked")
    private Double hoursWorked;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    // Relación con Client
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    // Relación con Vehicle
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    // Relación opcional con Mechanic (puede no asignarse al inicio)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mechanic_id")
    private Mechanic mechanic;
}
