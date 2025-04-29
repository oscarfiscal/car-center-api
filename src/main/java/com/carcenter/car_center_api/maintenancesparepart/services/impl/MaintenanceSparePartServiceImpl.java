package com.carcenter.car_center_api.maintenancesparepart.services.impl;

import com.carcenter.car_center_api.maintenance.entities.Maintenance;
import com.carcenter.car_center_api.maintenance.exceptions.MaintenanceNotFoundException;
import com.carcenter.car_center_api.maintenance.repositories.MaintenanceRepository;
import com.carcenter.car_center_api.maintenance.services.MaintenanceCostServiceInterface;
import com.carcenter.car_center_api.maintenancesparepart.dtos.MaintenanceSparePartCreateRequest;
import com.carcenter.car_center_api.maintenancesparepart.dtos.MaintenanceSparePartResponse;
import com.carcenter.car_center_api.maintenancesparepart.entities.MaintenanceSparePart;
import com.carcenter.car_center_api.maintenancesparepart.exceptions.MaintenanceSparePartNotFoundException;
import com.carcenter.car_center_api.maintenancesparepart.mappers.MaintenanceSparePartMapper;
import com.carcenter.car_center_api.maintenancesparepart.repositories.MaintenanceSparePartRepository;
import com.carcenter.car_center_api.maintenancesparepart.services.MaintenanceSparePartServiceInterface;
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
    private final NotificationService notificationService;
    private final MaintenanceCostServiceInterface maintenanceCostService;
    private final MaintenanceSparePartMapper mapper;

    @Override
    public MaintenanceSparePartResponse create(MaintenanceSparePartCreateRequest dto) {
        SparePart sparePart = spareRepo.findById(dto.getSparePartId())
                .orElseThrow(() -> new MaintenanceSparePartNotFoundException("Spare part not found with ID: " + dto.getSparePartId()));

        Maintenance maintenance = maintenanceRepo.findById(dto.getMaintenanceId())
                .orElseThrow(() -> new MaintenanceNotFoundException("Maintenance not found"));

        MaintenanceSparePart msp = mapper.toEntity(dto, sparePart, maintenance);


        MaintenanceSparePart saved = repo.save(msp);

        notifyClient(maintenance);

        return mapper.toResponse(saved);
    }

    private void notifyClient(Maintenance maintenance) {
        String phone = maintenance.getClient().getCellphone();
        String clientName = maintenance.getClient().getFirstName();
        BigDecimal total = maintenanceCostService.calculateTotal(maintenance.getId());

        notificationService.sendSms(
                phone,
                "Señor(a) " + clientName +
                        ", se agregó un repuesto al mantenimiento #" + maintenance.getId() +
                        ". El total actual es de " + total + " COP."
        );

        if (maintenance.getLimitBudget() != null && total.compareTo(maintenance.getLimitBudget()) > 0) {
            notificationService.sendSms(
                    phone,
                    "Señor(a) " + clientName +
                            ", alerta: el mantenimiento #" + maintenance.getId() +
                            " ha superado su presupuesto de " + maintenance.getLimitBudget() + " COP."
            );
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaintenanceSparePartResponse> getByMaintenance(Long maintenanceId) {
        return repo.findByMaintenanceId(maintenanceId)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaintenanceSparePartResponse> getBySparePart(Long sparePartId) {
        return repo.findBySparePartId(sparePartId)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }
}
