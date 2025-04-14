package com.carcenter.car_center_api.maintenancesparepart.services.impl;

import com.carcenter.car_center_api.maintenance.entities.Maintenance;
import com.carcenter.car_center_api.maintenance.repositories.MaintenanceRepository;
import com.carcenter.car_center_api.maintenancesparepart.dtos.MaintenanceSparePartCreateRequest;
import com.carcenter.car_center_api.maintenancesparepart.dtos.MaintenanceSparePartResponse;
import com.carcenter.car_center_api.maintenancesparepart.entities.MaintenanceSparePart;
import com.carcenter.car_center_api.maintenancesparepart.repositories.MaintenanceSparePartRepository;
import com.carcenter.car_center_api.maintenancesparepart.services.MaintenanceSparePartServiceInterface;
import com.carcenter.car_center_api.maintenanceserviceitem.repositories.MaintenanceServiceItemRepository;
import com.carcenter.car_center_api.notifications.NotificationService;
import com.carcenter.car_center_api.sparepart.entities.SparePart;
import com.carcenter.car_center_api.sparepart.repositories.SparePartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MaintenanceSparePartServiceImpl implements MaintenanceSparePartServiceInterface {

    private final MaintenanceSparePartRepository repo;
    private final SparePartRepository spareRepo;
    private final MaintenanceRepository maintenanceRepo;
    private final MaintenanceServiceItemRepository msiRepo;
    private final NotificationService notificationService;

    private MaintenanceSparePartResponse toResponse(MaintenanceSparePart msp) {
        return MaintenanceSparePartResponse.builder()
                .id(msp.getId())
                .quantity(msp.getQuantity())
                .estimatedTime(msp.getEstimatedTime())
                .sparePartId(msp.getSparePart().getId())
                .maintenanceId(msp.getMaintenance().getId())
                .build();
    }

    @Override
    public MaintenanceSparePartResponse create(MaintenanceSparePartCreateRequest dto) {
        // 1. Cargar entidades
        SparePart sp = spareRepo.findById(dto.getSparePartId())
                .orElseThrow(() -> new IllegalArgumentException("Spare part not found"));
        Maintenance m = maintenanceRepo.findById(dto.getMaintenanceId())
                .orElseThrow(() -> new IllegalArgumentException("Maintenance not found"));

        // 2. Persistir el nuevo repuesto en el mantenimiento
        MaintenanceSparePart msp = MaintenanceSparePart.builder()
                .sparePart(sp)
                .maintenance(m)
                .quantity(dto.getQuantity())
                .estimatedTime(dto.getEstimatedTime())
                .build();
        repo.save(msp);

        //Recalcular el total actual del mantenimiento (repuestos + servicios)
        BigDecimal newTotal = calculateMaintenanceTotal(m.getId());

        // Notificar al cliente que se agregó un repuesto
        String phone = m.getClient().getCellphone();
        notificationService.sendSms(
                phone,
                "Se agregó un repuesto al mantenimiento #" + m.getId()
                        + ". Total actual: " + newTotal + " COP."
        );
        System.out.println("phone = " + newTotal);

        //Si supera el presupuesto definido, enviar alerta adicional
        if (m.getLimitBudget() != null) {
            BigDecimal budget = BigDecimal.valueOf(m.getLimitBudget());
            System.out.println("budget = " + budget);
            if (newTotal.compareTo(budget) > 0) {
                notificationService.sendSms(
                        phone,
                        "Alerta: el mantenimiento #" + m.getId()
                                + " ha superado su presupuesto de " + m.getLimitBudget() + " COP."
                );
            }
        }


        return toResponse(msp);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaintenanceSparePartResponse> getByMaintenance(Long maintenanceId) {
        return repo.findByMaintenanceId(maintenanceId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaintenanceSparePartResponse> getBySparePart(Long sparePartId) {
        return repo.findBySparePartId(sparePartId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Suma el total de repuestos y servicios para un mantenimiento dado.
     */
    private BigDecimal calculateMaintenanceTotal(Long maintenanceId) {
        // Total repuestos
        BigDecimal spareSum = repo.findByMaintenanceId(maintenanceId).stream()
                .map(sp -> BigDecimal.valueOf(sp.getSparePart().getUnitPrice())
                        .multiply(BigDecimal.valueOf(sp.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println("spareSum = " + spareSum);



        return spareSum.add(spareSum);
    }
}
