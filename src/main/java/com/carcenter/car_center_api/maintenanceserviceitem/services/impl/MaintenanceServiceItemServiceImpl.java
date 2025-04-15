package com.carcenter.car_center_api.maintenanceserviceitem.services.impl;

import com.carcenter.car_center_api.maintenance.entities.Maintenance;
import com.carcenter.car_center_api.maintenance.repositories.MaintenanceRepository;
import com.carcenter.car_center_api.maintenance.services.MaintenanceCostServiceInterface;
import com.carcenter.car_center_api.maintenanceserviceitem.dtos.MaintenanceServiceItemCreateRequest;
import com.carcenter.car_center_api.maintenanceserviceitem.dtos.MaintenanceServiceItemResponse;
import com.carcenter.car_center_api.maintenanceserviceitem.entities.MaintenanceServiceItem;
import com.carcenter.car_center_api.maintenanceserviceitem.repositories.MaintenanceServiceItemRepository;
import com.carcenter.car_center_api.maintenanceserviceitem.services.MaintenanceServiceItemServiceInterface;
import com.carcenter.car_center_api.mechanicalservice.entities.MechanicalService;
import com.carcenter.car_center_api.mechanicalservice.repositories.MechanicalServiceRepository;
import com.carcenter.car_center_api.maintenancesparepart.repositories.MaintenanceSparePartRepository;
import com.carcenter.car_center_api.notifications.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Transactional
public class MaintenanceServiceItemServiceImpl implements MaintenanceServiceItemServiceInterface {

    private final MaintenanceServiceItemRepository repo;
    private final MechanicalServiceRepository serviceRepo;
    private final MaintenanceRepository maintenanceRepo;
    private final MaintenanceSparePartRepository mspRepo;
    private final NotificationService notificationService;
    private final MaintenanceCostServiceInterface MaintenanceCostService;

    private MaintenanceServiceItemResponse toResponse(MaintenanceServiceItem msi) {
        return MaintenanceServiceItemResponse.builder()
                .id(msi.getId())
                .estimatedTime(msi.getEstimatedTime())
                .serviceId(msi.getMechanicalService().getId())
                .maintenanceId(msi.getMaintenance().getId())
                .build();
    }

    @Override
    public MaintenanceServiceItemResponse create(MaintenanceServiceItemCreateRequest dto) {
        MechanicalService svc = serviceRepo.findById(dto.getServiceId())
                .orElseThrow(() -> new IllegalArgumentException("Service not found"));
        Maintenance mnt = maintenanceRepo.findById(dto.getMaintenanceId())
                .orElseThrow(() -> new IllegalArgumentException("Maintenance not found"));

        MaintenanceServiceItem msi = MaintenanceServiceItem.builder()
                .mechanicalService(svc)
                .maintenance(mnt)
                .estimatedTime(dto.getEstimatedTime())
                .build();
        MaintenanceServiceItem saved = repo.save(msi);

        // --- notificaciones ---
        String clientPhone = mnt.getClient().getCellphone();
        String clientName  = mnt.getClient().getFirstName();
        BigDecimal total   = MaintenanceCostService.calculateTotal(mnt.getId());
        String    fmtTotal = MaintenanceCostService.formatCOP(total);

        notificationService.sendSms(
                clientPhone,
                "Señor(a) " + clientName +
                        ", se agregó un servicio al mantenimiento #" + mnt.getId() +
                        ". Total actual: " + fmtTotal + "COP."
        );

        if (mnt.getLimitBudget() != null) {
            BigDecimal budget = BigDecimal.valueOf(mnt.getLimitBudget());
            if (total.compareTo(budget) > 0) {
                String fmtBudget = MaintenanceCostService.formatCOP((budget));
                notificationService.sendSms(
                        clientPhone,
                        "Señor(a) " + clientName +
                                ", alerta: el mantenimiento #" + mnt.getId() +
                                " ha superado su presupuesto de " + fmtBudget + "COP."
                );
            }
        }

        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public MaintenanceServiceItemResponse getById(Long id) {
        MaintenanceServiceItem msi = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));
        return toResponse(msi);
    }

    // método para sumar repuestos y servicios
    private BigDecimal calculateMaintenanceTotal(Long maintenanceId) {
        BigDecimal spareSum = mspRepo.findByMaintenanceId(maintenanceId).stream()
                .map(sp -> BigDecimal.valueOf(sp.getSparePart().getUnitPrice())
                        .multiply(BigDecimal.valueOf(sp.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal serviceSum = repo.findByMaintenanceId(maintenanceId).stream()
                .map(si -> BigDecimal.valueOf(si.getMechanicalService().getPrice())
                        .multiply(BigDecimal.valueOf(si.getEstimatedTime())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return spareSum.add(serviceSum);
    }
}
