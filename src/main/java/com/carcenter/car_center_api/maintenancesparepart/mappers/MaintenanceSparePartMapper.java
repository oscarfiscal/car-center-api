package com.carcenter.car_center_api.maintenancesparepart.mappers;

import com.carcenter.car_center_api.maintenance.entities.Maintenance;
import com.carcenter.car_center_api.maintenancesparepart.dtos.MaintenanceSparePartCreateRequest;
import com.carcenter.car_center_api.maintenancesparepart.dtos.MaintenanceSparePartResponse;
import com.carcenter.car_center_api.maintenancesparepart.entities.MaintenanceSparePart;
import com.carcenter.car_center_api.sparepart.entities.SparePart;
import org.springframework.stereotype.Component;

@Component
public class MaintenanceSparePartMapper {

    public MaintenanceSparePartResponse toResponse(MaintenanceSparePart entity) {
        if (entity == null) {
            return null;
        }

        return MaintenanceSparePartResponse.builder()
                .id(entity.getId())
                .quantity(entity.getQuantity())
                .estimatedTime(entity.getEstimatedTime())
                .sparePartId(entity.getSparePart().getId())
                .maintenanceId(entity.getMaintenance().getId())
                .build();
    }

    public MaintenanceSparePart toEntity(MaintenanceSparePartCreateRequest dto, SparePart sparePart, Maintenance maintenance) {
        if (dto == null || sparePart == null || maintenance == null) {
            return null;
        }

        return MaintenanceSparePart.builder()
                .sparePart(sparePart)
                .maintenance(maintenance)
                .quantity(dto.getQuantity())
                .estimatedTime(dto.getEstimatedTime())
                .build();
    }
}
