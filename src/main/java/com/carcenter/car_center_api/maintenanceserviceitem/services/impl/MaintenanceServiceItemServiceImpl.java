package com.carcenter.car_center_api.maintenanceserviceitem.services.impl;

import com.carcenter.car_center_api.maintenance.entities.Maintenance;
import com.carcenter.car_center_api.maintenance.repositories.MaintenanceRepository;
import com.carcenter.car_center_api.maintenance.services.MaintenanceCostServiceInterface;
import com.carcenter.car_center_api.maintenanceserviceitem.dtos.MaintenanceServiceItemCreateRequest;
import com.carcenter.car_center_api.maintenanceserviceitem.dtos.MaintenanceServiceItemResponse;
import com.carcenter.car_center_api.maintenanceserviceitem.entities.MaintenanceServiceItem;
import com.carcenter.car_center_api.maintenanceserviceitem.exceptions.MaintenanceServiceItemNotFoundException;
import com.carcenter.car_center_api.maintenanceserviceitem.mappers.MaintenanceServiceItemMapper;
import com.carcenter.car_center_api.maintenanceserviceitem.repositories.MaintenanceServiceItemRepository;
import com.carcenter.car_center_api.maintenanceserviceitem.services.MaintenanceServiceItemServiceInterface;
import com.carcenter.car_center_api.mechanicalservice.entities.MechanicalService;
import com.carcenter.car_center_api.mechanicalservice.repositories.MechanicalServiceRepository;
import com.carcenter.car_center_api.notifications.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional
public class MaintenanceServiceItemServiceImpl implements MaintenanceServiceItemServiceInterface {

    private final MaintenanceServiceItemRepository repo;
    private final MechanicalServiceRepository serviceRepo;
    private final MaintenanceRepository maintenanceRepo;
    private final NotificationService notificationService;
    private final MaintenanceCostServiceInterface maintenanceCostService;
    private final MaintenanceServiceItemMapper mapper;

    @Override
    public MaintenanceServiceItemResponse create(MaintenanceServiceItemCreateRequest dto) {
        MechanicalService service = serviceRepo.findById(dto.getServiceId())
                .orElseThrow(() -> new MaintenanceServiceItemNotFoundException("Mechanical service not found with ID: " + dto.getServiceId()));

        Maintenance maintenance = maintenanceRepo.findById(dto.getMaintenanceId())
                .orElseThrow(() -> new MaintenanceServiceItemNotFoundException("Maintenance not found with ID: " + dto.getMaintenanceId()));

        MaintenanceServiceItem serviceItem = MaintenanceServiceItem.builder()
                .mechanicalService(service)
                .maintenance(maintenance)
                .estimatedTime(dto.getEstimatedTime())
                .build();

        MaintenanceServiceItem saved = repo.save(serviceItem);

        sendNotificationIfNeeded(maintenance);

        return mapper.toResponse(saved);
    }

    private void sendNotificationIfNeeded(Maintenance maintenance) {
        String clientPhone = maintenance.getClient().getCellphone();
        String clientName = maintenance.getClient().getFirstName();
        BigDecimal totalCost = maintenanceCostService.calculateTotal(maintenance.getId());

        notificationService.sendSms(
                clientPhone,
                "Señor(a) " + clientName +
                        ", se agregó un servicio al mantenimiento #" + maintenance.getId() +
                        ". Total actual: " + totalCost + " COP."
        );

        BigDecimal limitBudget = maintenance.getLimitBudget();
        if (limitBudget != null && totalCost.compareTo(limitBudget) > 0) {
            notificationService.sendSms(
                    clientPhone,
                    "Señor(a) " + clientName +
                            ", alerta: el mantenimiento #" + maintenance.getId() +
                            " ha superado su presupuesto de " + limitBudget + " COP."
            );
        }
    }

    @Override
    @Transactional(readOnly = true)
    public MaintenanceServiceItemResponse getById(Long id) {
        MaintenanceServiceItem serviceItem = repo.findById(id)
                .orElseThrow(() -> new MaintenanceServiceItemNotFoundException("Maintenance service item not found with ID: " + id));
        return mapper.toResponse(serviceItem);
    }
}
