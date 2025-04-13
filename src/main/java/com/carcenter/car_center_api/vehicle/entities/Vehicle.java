package com.carcenter.car_center_api.vehicle.entities;

import com.carcenter.car_center_api.client.entities.Client;
import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad que representa un vehículo en el sistema.
 * Puede estar asociado a un Client y a una Brand (marca).
 */

@Entity
@Table(name = "vehicles",
        uniqueConstraints = @UniqueConstraint(columnNames = "plate"))
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10, unique = true)
    private String plate;

    @Column(nullable = false, length = 50)
    private String brand;

    @Column(nullable = false, length = 50)
    private String model;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false, length = 20)
    private String color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
}
