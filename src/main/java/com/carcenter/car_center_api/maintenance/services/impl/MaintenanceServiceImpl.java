package com.carcenter.car_center_api.maintenance.services.impl;

import com.carcenter.car_center_api.client.entities.Client;
import com.carcenter.car_center_api.client.repositories.ClientRepository;
import com.carcenter.car_center_api.maintenance.dtos.*;
import com.carcenter.car_center_api.maintenance.entities.*;
import com.carcenter.car_center_api.maintenance.repositories.*;
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
        // Buscar cliente por documento
        Client client = clientRepo.findByDocument(request.getClientDocument())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Client with document '" + request.getClientDocument() + "' not found"
                        )
                );
        // Buscar vehículo por placa
        Vehicle vehicle = vehicleRepo.findByPlate(request.getVehiclePlate())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Vehicle with license plate '" + request.getVehiclePlate() + "'  not found"
                        )
                );
        Maintenance maintenance = Maintenance.builder()
                .client(client)
                .vehicle(vehicle)
                .description(request.getDescription())
                .limitBudget(request.getLimitBudget())
                .status(MaintenanceStatus.PENDING)
                .build();

        Maintenance saved = maintenanceRepo.save(maintenance);
        return toResponse(saved);
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

    @Override
    public MaintenanceResponse assignMechanic(Long maintenanceId) {
        Maintenance m = maintenanceRepo.findById(maintenanceId)
                .orElseThrow(() -> new IllegalArgumentException("Maintenance not found"));
        if (m.getStatus() != MaintenanceStatus.PENDING) {
            throw new IllegalStateException("Can only assign mechanic to PENDING maintenance");
        }
        // obtenemos el top-ten y asignamos el primero
        List<MechanicResponse> candidates = mechanicService.getTopTenAvailable();
        if (candidates.isEmpty()) {
            throw new IllegalStateException("No available mechanics");
        }
        // buscamos la entidad Mechanic por id
        Long mechId = candidates.get(0).getId();
        Mechanic mech = new Mechanic();
        mech.setId(mechId); // asumimos que sólo necesitamos la referencia
        m.setMechanic(mech);
        m.setStatus(MaintenanceStatus.IN_PROGRESS);
        m.setStartDate(LocalDate.now());
        Maintenance updated = maintenanceRepo.save(m);
        return toResponse(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public MaintenanceFullResponse getFullDetails(Long maintenanceId) {
        Maintenance m = maintenanceRepo.findById(maintenanceId)
                .orElseThrow(() -> new IllegalArgumentException("Maintenance not found"));

        // Cliente
        Client c = m.getClient();
        MaintenanceFullResponse.ClientInfo ci = new MaintenanceFullResponse.ClientInfo(
                c.getId(),
                c.getFirstName(), c.getSecondName(), c.getFirstLastName(), c.getSecondLastName(),
                c.getDocumentType(), c.getDocument(),
                c.getCellphone(), c.getAddress(), c.getEmail()
        );

        // Mecánico (puede ser null si aún no asignado)
        MaintenanceFullResponse.MechanicInfo mi = null;
        if (m.getMechanic() != null) {
            Mechanic mech = m.getMechanic();
            mi = new MaintenanceFullResponse.MechanicInfo(
                    mech.getId(),
                    mech.getFirstName(), mech.getSecondName(), mech.getFirstLastName(), mech.getSecondLastName(),
                    mech.getDocumentType(), mech.getDocument(),
                    mech.getCellphone(), mech.getAddress(), mech.getEmail(),
                    mech.getStatus().name()
            );
        }

        // Repuestos
        List<MaintenanceFullResponse.SparePartItem> spareItems = mspRepo.findByMaintenanceId(m.getId())
                .stream()
                .map(sp -> {
                    double line = sp.getQuantity() * sp.getSparePart().getUnitPrice();
                    return new MaintenanceFullResponse.SparePartItem(
                            sp.getId(),
                            sp.getSparePart().getName(),
                            sp.getQuantity(),
                            sp.getSparePart().getUnitPrice(),
                            line
                    );
                }).collect(Collectors.toList());

        // Servicios
        List<MaintenanceFullResponse.ServiceItem> serviceItems = msiRepo.findByMaintenanceId(m.getId())
                .stream()
                .map(si -> {
                    double line = si.getEstimatedTime() * si.getMechanicalService().getPrice();
                    return new MaintenanceFullResponse.ServiceItem(
                            si.getId(),
                            si.getMechanicalService().getName(),
                            si.getEstimatedTime(),
                            si.getMechanicalService().getPrice(),
                            line
                    );
                }).collect(Collectors.toList());

        // Total
        double totalCost = spareItems.stream().mapToDouble(MaintenanceFullResponse.SparePartItem::getLineTotal).sum()
                + serviceItems.stream().mapToDouble(MaintenanceFullResponse.ServiceItem::getLineTotal).sum();

        return MaintenanceFullResponse.builder()
                .id(m.getId())
                .description(m.getDescription())
                .limitBudget(m.getLimitBudget())
                .status(m.getStatus().name())
                .hoursWorked(m.getHoursWorked())
                .startDate(m.getStartDate())
                .endDate(m.getEndDate())
                .client(ci)
                .mechanic(mi)
                .spareParts(spareItems)
                .serviceItems(serviceItems)
                .totalCost(totalCost)
                .build();
    }



}
