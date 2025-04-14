package com.carcenter.car_center_api.maintenanceserviceitem.services.impl;

import com.carcenter.car_center_api.maintenance.entities.Maintenance;
import com.carcenter.car_center_api.maintenance.repositories.MaintenanceRepository;
import com.carcenter.car_center_api.maintenanceserviceitem.dtos.*;
import com.carcenter.car_center_api.maintenanceserviceitem.entities.*;
import com.carcenter.car_center_api.maintenanceserviceitem.repositories.*;
import com.carcenter.car_center_api.maintenanceserviceitem.services.MaintenanceServiceItemServiceInterface;
import com.carcenter.car_center_api.mechanicalservice.entities.MechanicalService;
import com.carcenter.car_center_api.mechanicalservice.repositories.MechanicalServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class MaintenanceServiceItemServiceImpl implements MaintenanceServiceItemServiceInterface {

    private final MaintenanceServiceItemRepository repo;
    private final MechanicalServiceRepository serviceRepo;
    private final MaintenanceRepository maintenanceRepo;

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

        return toResponse(repo.save(msi));
    }


    @Override
    @Transactional(readOnly = true)
    public MaintenanceServiceItemResponse getById(Long id) {
        MaintenanceServiceItem msi = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));
        return toResponse(msi);
    }



}
