package com.carcenter.car_center_api.entities;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad que representa la marca de un vehículo.
 */
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

    private String name;
}