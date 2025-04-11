package com.carcenter.car_center_api.entities;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad que representa las fotos asociadas a un mantenimiento,
 * por ejemplo, para evidenciar el estado del vehículo.
 */
@Entity
@Table(name = "maintenance_photos")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String photoUrl;

    @ManyToOne
    @JoinColumn(name = "maintenance_id")
    private Maintenance maintenance;
}

