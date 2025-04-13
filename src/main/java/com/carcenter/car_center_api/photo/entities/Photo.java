package com.carcenter.car_center_api.photo.entities;

import com.carcenter.car_center_api.maintenance.entities.Maintenance;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "photos")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maintenance_id", nullable = false)
    private Maintenance maintenance;
}