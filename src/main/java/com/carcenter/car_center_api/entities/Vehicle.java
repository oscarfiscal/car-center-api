package com.carcenter.car_center_api.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Entidad que representa un vehículo en el sistema.
 * Puede estar asociado a un Client y a una Brand (marca).
 */
@Entity
@Table(name = "vehicles")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String plate;  // Placa
    private String model;

    @ManyToOne
    @JoinColumn(name = "client_document")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;


    @OneToMany(mappedBy = "vehicle")
    private List<Maintenance> maintenances;
}
