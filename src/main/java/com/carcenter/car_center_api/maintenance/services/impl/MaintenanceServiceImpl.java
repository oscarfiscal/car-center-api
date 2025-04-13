package com.carcenter.car_center_api.maintenance.services.impl;

import com.carcenter.car_center_api.client.entities.Client;
import com.carcenter.car_center_api.client.repositories.ClientRepository;
import com.carcenter.car_center_api.maintenance.dtos.*;
import com.carcenter.car_center_api.maintenance.entities.*;
import com.carcenter.car_center_api.maintenance.repositories.*;
import com.carcenter.car_center_api.maintenance.services.MaintenanceServiceInterface;
import com.carcenter.car_center_api.mechanic.entities.Mechanic;
import com.carcenter.car_center_api.mechanic.repositories.MechanicRepository;
import com.carcenter.car_center_api.vehicle.entities.Vehicle;
import com.carcenter.car_center_api.vehicle.repositories.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MaintenanceServiceImpl implements MaintenanceServiceInterface {

    private final MaintenanceRepository maintenanceRepo;
    private final ClientRepository clientRepo;
    private final VehicleRepository vehicleRepo;
    private final MechanicRepository mechanicRepo;

    private MaintenanceResponse toResponse(Maintenance m) {
        return MaintenanceResponse.builder()
                .id(m.getId())
                .description(m.getDescription())
                .limitBudget(m.getLimitBudget())
                .status(m.getStatus())
                .hoursWorked(m.getHoursWorked())
                .startDate(m.getStartDate())
                .endDate(m.getEndDate())
                .clientId(m.getClient().getId())
                .vehicleId(m.getVehicle().getId())
                .mechanicId(m.getMechanic() != null ? m.getMechanic().getId() : null)
                .build();
    }

    @Override
    public MaintenanceResponse create(MaintenanceCreateRequest request) {
        Client client = clientRepo.findById(request.getClientId())
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));
        Vehicle vehicle = vehicleRepo.findById(request.getVehicleId())
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));

        Maintenance maintenance = Maintenance.builder()
                .client(client)
                .vehicle(vehicle)
                .description(request.getDescription())
                .limitBudget(request.getLimitBudget())
                .status(MaintenanceStatus.PENDING)
                .build();

        return toResponse(maintenanceRepo.save(maintenance));
    }

    @Override
    @Transactional(readOnly = true)
    public MaintenanceResponse getById(Long id) {
        Maintenance maintenance = maintenanceRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Maintenance not found"));
        return toResponse(maintenance);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MaintenanceResponse> getAll(Pageable pageable) {
        return maintenanceRepo.findAll(pageable).map(this::toResponse);
    }

    @Override
    public MaintenanceResponse update(Long id, MaintenanceUpdateRequest request) {
        Maintenance maintenance = maintenanceRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Maintenance not found"));

        maintenance.setDescription(request.getDescription());
        maintenance.setLimitBudget(request.getLimitBudget());
        maintenance.setStatus(request.getStatus());
        maintenance.setHoursWorked(request.getHoursWorked());

        if (request.getMechanicId() != null) {
            Mechanic mechanic = mechanicRepo.findById(request.getMechanicId())
                    .orElseThrow(() -> new IllegalArgumentException("Mechanic not found"));
            maintenance.setMechanic(mechanic);
        }

        return toResponse(maintenanceRepo.save(maintenance));
    }

    @Override
    public void delete(Long id) {
        if (!maintenanceRepo.existsById(id)) {
            throw new IllegalArgumentException("Maintenance not found");
        }
        maintenanceRepo.deleteById(id);
    }
}
