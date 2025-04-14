package com.carcenter.car_center_api.maintenanceserviceitem.entities;

import com.carcenter.car_center_api.maintenance.entities.Maintenance;
import com.carcenter.car_center_api.mechanicalservice.entities.MechanicalService;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "services_maintenances")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceServiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "estimated_time", nullable = false)
    private Integer estimatedTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    private MechanicalService mechanicalService;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maintenance_id", nullable = false)
    private Maintenance maintenance;
}