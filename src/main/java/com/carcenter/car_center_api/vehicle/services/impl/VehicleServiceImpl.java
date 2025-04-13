package com.carcenter.car_center_api.vehicle.services.impl;

import com.carcenter.car_center_api.client.entities.Client;
import com.carcenter.car_center_api.client.repositories.ClientRepository;
import com.carcenter.car_center_api.vehicle.dtos.*;
import com.carcenter.car_center_api.vehicle.entities.*;
import com.carcenter.car_center_api.vehicle.repositories.*;
import com.carcenter.car_center_api.vehicle.services.VehicleServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class VehicleServiceImpl implements VehicleServiceInterface {

    private final VehicleRepository vehicleRepo;
    private final ClientRepository clientRepo;

    private VehicleResponse toResponse(Vehicle v) {
        return VehicleResponse.builder()
                .id(v.getId())
                .plate(v.getPlate())
                .brand(v.getBrand())
                .model(v.getModel())
                .year(v.getYear())
                .color(v.getColor())
                .clientId(v.getClient().getId())
                .build();
    }

    @Override
    public VehicleResponse create(VehicleCreateRequest dto) {
        if (vehicleRepo.existsByPlate(dto.getPlate())) {
            throw new IllegalArgumentException("Plate already in use");
        }
        Client client = clientRepo.findById(dto.getClientId())
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));

        Vehicle v = Vehicle.builder()
                .plate(dto.getPlate())
                .brand(dto.getBrand())
                .model(dto.getModel())
                .year(dto.getYear())
                .color(dto.getColor())
                .client(client)
                .build();

        return toResponse(vehicleRepo.save(v));
    }

    @Override
    @Transactional(readOnly = true)
    public VehicleResponse getById(Long id) {
        Vehicle v = vehicleRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));
        return toResponse(v);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VehicleResponse> getAll(Pageable pageable) {
        return vehicleRepo.findAll(pageable).map(this::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehicleResponse> getByClient(Long clientId) {
        return vehicleRepo.findByClientId(clientId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public VehicleResponse update(Long id, VehicleUpdateRequest dto) {
        Vehicle v = vehicleRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));
        v.setBrand(dto.getBrand());
        v.setModel(dto.getModel());
        v.setYear(dto.getYear());
        v.setColor(dto.getColor());
        return toResponse(vehicleRepo.save(v));
    }

    @Override
    public void delete(Long id) {
        if (!vehicleRepo.existsById(id)) {
            throw new IllegalArgumentException("Vehicle not found");
        }
        vehicleRepo.deleteById(id);
    }
}