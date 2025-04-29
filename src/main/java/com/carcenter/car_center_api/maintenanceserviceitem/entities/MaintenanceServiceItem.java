package com.carcenter.car_center_api.maintenanceserviceitem.entities;

import com.carcenter.car_center_api.maintenance.entities.Maintenance;
import com.carcenter.car_center_api.mechanicalservice.entities.MechanicalService;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "services_maintenances")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entity representing a service assigned to a maintenance")
public class MaintenanceServiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID of the maintenance service item", example = "1")
    private Long id;

    @Column(name = "estimated_time", nullable = false)
    @Schema(description = "Estimated time (in hours) to perform the service", example = "2")
    private Integer estimatedTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    @Schema(description = "Reference to the mechanical service associated with this item")
    private MechanicalService mechanicalService;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maintenance_id", nullable = false)
    @Schema(description = "Reference to the maintenance where this service is assigned")
    private Maintenance maintenance;
}
