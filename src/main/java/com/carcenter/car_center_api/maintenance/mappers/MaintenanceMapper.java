package com.carcenter.car_center_api.maintenance.mappers;

import com.carcenter.car_center_api.client.entities.Client;
import com.carcenter.car_center_api.maintenance.dtos.MaintenanceFullResponse;
import com.carcenter.car_center_api.maintenance.dtos.MaintenanceResponse;
import com.carcenter.car_center_api.maintenance.entities.Maintenance;
import com.carcenter.car_center_api.maintenanceserviceitem.entities.MaintenanceServiceItem;
import com.carcenter.car_center_api.maintenancesparepart.entities.MaintenanceSparePart;
import com.carcenter.car_center_api.mechanic.entities.Mechanic;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MaintenanceMapper {

    public MaintenanceResponse toMaintenanceResponse(Maintenance maintenance) {
        if (maintenance == null) return null;

        return MaintenanceResponse.builder()
                .id(maintenance.getId())
                .description(maintenance.getDescription())
                .limitBudget(maintenance.getLimitBudget())
                .status(maintenance.getStatus())
                .hoursWorked(maintenance.getHoursWorked())
                .startDate(maintenance.getStartDate())
                .endDate(maintenance.getEndDate())
                .clientId(maintenance.getClient().getId())
                .vehicleId(maintenance.getVehicle().getId())
                .mechanicId(maintenance.getMechanic() != null ? maintenance.getMechanic().getId() : null)
                .build();
    }

    public MaintenanceFullResponse.ClientInfo toClientInfo(Client client) {
        if (client == null) return null;
        return new MaintenanceFullResponse.ClientInfo(
                client.getId(),
                client.getFirstName(),
                client.getSecondName(),
                client.getFirstLastName(),
                client.getSecondLastName(),
                client.getDocumentType(),
                client.getDocument(),
                client.getCellphone(),
                client.getAddress(),
                client.getEmail()
        );
    }

    public MaintenanceFullResponse.MechanicInfo toMechanicInfo(Mechanic mechanic) {
        if (mechanic == null) return null;
        return new MaintenanceFullResponse.MechanicInfo(
                mechanic.getId(),
                mechanic.getFirstName(),
                mechanic.getSecondName(),
                mechanic.getFirstLastName(),
                mechanic.getSecondLastName(),
                mechanic.getDocumentType(),
                mechanic.getDocument(),
                mechanic.getCellphone(),
                mechanic.getAddress(),
                mechanic.getEmail(),
                mechanic.getStatus().name()
        );
    }

    public MaintenanceFullResponse.SparePartItem toSparePartItem(MaintenanceSparePart sparePart) {
        BigDecimal unitPrice = BigDecimal.valueOf(sparePart.getSparePart().getUnitPrice());
        BigDecimal lineTotal = unitPrice.multiply(BigDecimal.valueOf(sparePart.getQuantity()));

        return new MaintenanceFullResponse.SparePartItem(
                sparePart.getId(),
                sparePart.getSparePart().getName(),
                sparePart.getQuantity(),
                unitPrice,
                lineTotal
        );
    }

    public MaintenanceFullResponse.ServiceItem toServiceItem(MaintenanceServiceItem serviceItem) {
        BigDecimal unitPrice = BigDecimal.valueOf(serviceItem.getMechanicalService().getPrice());
        BigDecimal lineTotal = unitPrice.multiply(BigDecimal.valueOf(serviceItem.getEstimatedTime()));

        return new MaintenanceFullResponse.ServiceItem(
                serviceItem.getId(),
                serviceItem.getMechanicalService().getName(),
                serviceItem.getEstimatedTime(),
                unitPrice,
                lineTotal
        );
    }

    public MaintenanceFullResponse toFullResponse(Maintenance maintenance,
                                                  List<MaintenanceFullResponse.SparePartItem> spareParts,
                                                  List<MaintenanceFullResponse.ServiceItem> serviceItems,
                                                  BigDecimal totalCost) {
        return MaintenanceFullResponse.builder()
                .id(maintenance.getId())
                .description(maintenance.getDescription())
                .limitBudget(maintenance.getLimitBudget())
                .status(maintenance.getStatus().name())
                .hoursWorked(maintenance.getHoursWorked())
                .startDate(maintenance.getStartDate())
                .endDate(maintenance.getEndDate())
                .client(toClientInfo(maintenance.getClient()))
                .mechanic(maintenance.getMechanic() != null ? toMechanicInfo(maintenance.getMechanic()) : null)
                .spareParts(spareParts)
                .serviceItems(serviceItems)
                .totalCost(totalCost)
                .build();
    }
}
