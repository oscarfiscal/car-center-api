package com.carcenter.car_center_api.maintenance.services.impl;

import com.carcenter.car_center_api.client.entities.Client;
import com.carcenter.car_center_api.client.repositories.ClientRepository;
import com.carcenter.car_center_api.maintenance.dtos.*;
import com.carcenter.car_center_api.maintenance.entities.*;
import com.carcenter.car_center_api.maintenance.exceptions.InvalidMaintenanceStatusException;
import com.carcenter.car_center_api.maintenance.exceptions.MaintenanceNotFoundException;
import com.carcenter.car_center_api.maintenance.exceptions.MechanicNotFoundException;
import com.carcenter.car_center_api.maintenance.mappers.MaintenanceMapper;
import com.carcenter.car_center_api.maintenance.repositories.MaintenanceRepository;
import com.carcenter.car_center_api.maintenance.services.MaintenanceServiceInterface;
import com.carcenter.car_center_api.maintenanceserviceitem.repositories.MaintenanceServiceItemRepository;
import com.carcenter.car_center_api.maintenancesparepart.repositories.MaintenanceSparePartRepository;
import com.carcenter.car_center_api.mechanic.dtos.MechanicResponse;
import com.carcenter.car_center_api.mechanic.entities.Mechanic;
import com.carcenter.car_center_api.mechanic.repositories.MechanicRepository;
import com.carcenter.car_center_api.mechanic.services.MechanicServiceInterface;
import com.carcenter.car_center_api.vehicle.entities.Vehicle;
import com.carcenter.car_center_api.vehicle.repositories.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MaintenanceServiceImpl implements MaintenanceServiceInterface {

    private final MaintenanceRepository maintenanceRepo;
    private final ClientRepository clientRepo;
    private final VehicleRepository vehicleRepo;
    private final MechanicRepository mechanicRepo;
    private final MaintenanceServiceItemRepository msiRepo;
    private final MaintenanceSparePartRepository mspRepo;
    private final MechanicServiceInterface mechanicService;
    private final MaintenanceMapper mapper;

    @Override
    public MaintenanceResponse create(MaintenanceCreateRequest request) {
        Client client = clientRepo.findByDocument(request.getClientDocument())
                .orElseThrow(() -> new MaintenanceNotFoundException("Client with document '" + request.getClientDocument() + "' not found"));

        Vehicle vehicle = vehicleRepo.findByPlate(request.getVehiclePlate())
                .orElseThrow(() -> new MaintenanceNotFoundException("Vehicle with plate '" + request.getVehiclePlate() + "' not found"));

        Maintenance maintenance = Maintenance.builder()
                .client(client)
                .vehicle(vehicle)
                .description(request.getDescription())
                .limitBudget(request.getLimitBudget())
                .status(MaintenanceStatus.PENDING)
                .build();

        Maintenance saved = maintenanceRepo.save(maintenance);
        return mapper.toMaintenanceResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public MaintenanceResponse getById(Long id) {
        Maintenance maintenance = maintenanceRepo.findById(id)
                .orElseThrow(() -> new MaintenanceNotFoundException("Maintenance not found with ID: " + id));
        return mapper.toMaintenanceResponse(maintenance);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MaintenanceResponse> getAll(Pageable pageable) {
        return maintenanceRepo.findAll(pageable)
                .map(mapper::toMaintenanceResponse);
    }

    @Override
    public MaintenanceResponse update(Long id, MaintenanceUpdateRequest request) {
        Maintenance maintenance = maintenanceRepo.findById(id)
                .orElseThrow(() -> new MaintenanceNotFoundException("Maintenance not found with ID: " + id));

        maintenance.setDescription(request.getDescription());
        maintenance.setLimitBudget(request.getLimitBudget());
        maintenance.setStatus(request.getStatus());
        maintenance.setHoursWorked(request.getHoursWorked());

        if (request.getMechanicId() != null) {
            Mechanic mechanic = mechanicRepo.findById(request.getMechanicId())
                    .orElseThrow(() -> new MechanicNotFoundException("Mechanic not found with ID: " + request.getMechanicId()));
            maintenance.setMechanic(mechanic);
        }

        return mapper.toMaintenanceResponse(maintenanceRepo.save(maintenance));
    }

    @Override
    public void delete(Long id) {
        if (!maintenanceRepo.existsById(id)) {
            throw new MaintenanceNotFoundException("Maintenance not found with ID: " + id);
        }
        maintenanceRepo.deleteById(id);
    }

    @Override
    public MaintenanceResponse assignMechanic(Long maintenanceId) {
        Maintenance maintenance = maintenanceRepo.findById(maintenanceId)
                .orElseThrow(() -> new MaintenanceNotFoundException("Maintenance not found with ID: " + maintenanceId));

        Mechanic mechanic = getMechanic(maintenance);

        maintenance.setMechanic(mechanic);
        maintenance.setStatus(MaintenanceStatus.IN_PROGRESS);
        maintenance.setStartDate(LocalDate.now());

        return mapper.toMaintenanceResponse(maintenanceRepo.save(maintenance));
    }

    private Mechanic getMechanic(Maintenance maintenance) {
        if (maintenance.getStatus() != MaintenanceStatus.PENDING) {
            throw new InvalidMaintenanceStatusException("Only PENDING maintenances can be assigned a mechanic");

        }

        List<MechanicResponse> candidates = mechanicService.getTopTenAvailable();
        if (candidates.isEmpty()) {
            throw new IllegalStateException("No available mechanics");
        }

        Long mechanicId = candidates.getFirst().getId();
        Mechanic mechanic = new Mechanic();
        mechanic.setId(mechanicId);
        return mechanic;
    }

    @Override
    @Transactional(readOnly = true)
    public MaintenanceFullResponse getFullDetails(Long maintenanceId) {
        Maintenance maintenance = maintenanceRepo.findById(maintenanceId)
                .orElseThrow(() -> new MaintenanceNotFoundException("Maintenance not found with ID: " + maintenanceId));

        List<MaintenanceFullResponse.SparePartItem> spareParts = mspRepo.findByMaintenanceId(maintenance.getId())
                .stream()
                .map(mapper::toSparePartItem)
                .collect(Collectors.toList());

        List<MaintenanceFullResponse.ServiceItem> serviceItems = msiRepo.findByMaintenanceId(maintenance.getId())
                .stream()
                .map(mapper::toServiceItem)
                .collect(Collectors.toList());

        BigDecimal totalCost = spareParts.stream()
                .map(MaintenanceFullResponse.SparePartItem::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .add(
                        serviceItems.stream()
                                .map(MaintenanceFullResponse.ServiceItem::getLineTotal)
                                .reduce(BigDecimal.ZERO, BigDecimal::add)
                );

        return mapper.toFullResponse(maintenance, spareParts, serviceItems, totalCost);
    }
}
