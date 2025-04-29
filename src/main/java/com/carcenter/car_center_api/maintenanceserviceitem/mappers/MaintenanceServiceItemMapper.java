package com.carcenter.car_center_api.maintenanceserviceitem.mappers;

import com.carcenter.car_center_api.maintenanceserviceitem.dtos.MaintenanceServiceItemResponse;
import com.carcenter.car_center_api.maintenanceserviceitem.entities.MaintenanceServiceItem;
import org.springframework.stereotype.Component;

@Component
public class MaintenanceServiceItemMapper {

    public MaintenanceServiceItemResponse toResponse(MaintenanceServiceItem entity) {
        if (entity == null) {
            return null;
        }
        return MaintenanceServiceItemResponse.builder()
                .id(entity.getId())
                .estimatedTime(entity.getEstimatedTime())
                .serviceId(entity.getMechanicalService().getId())
                .maintenanceId(entity.getMaintenance().getId())
                .build();
    }
}
