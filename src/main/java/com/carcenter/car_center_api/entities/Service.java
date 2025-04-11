package com.carcenter.car_center_api.entities;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad que representa un tipo de servicio.
 * Por ejemplo: Pintura, Cambio de Aceite, etc.
 */
@Entity
@Table(name = "services")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double minLaborCost;
    private Double maxLaborCost;
}