package com.carcenter.car_center_api.brand.entities;

import com.carcenter.car_center_api.vehicle.entities.Vehicle;
import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad que representa la marca de un vehículo.
 */
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "brands")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vehicle> vehicles;
}