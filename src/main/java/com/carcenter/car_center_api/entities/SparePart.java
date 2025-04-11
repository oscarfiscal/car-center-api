package com.carcenter.car_center_api.entities;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad que representa un repuesto.
 * Ej: Neumáticos, Filtros, Baterías, etc.
 */
@Entity
@Table(name = "spare_parts")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SparePart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Double price;
    private Double discount;
}

