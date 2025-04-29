package com.carcenter.car_center_api.vehicle.services.impl;

import com.carcenter.car_center_api.brand.entities.Brand;
import com.carcenter.car_center_api.brand.exceptions.BrandNotFoundException;
import com.carcenter.car_center_api.brand.repositories.BrandRepository;
import com.carcenter.car_center_api.client.entities.Client;
import com.carcenter.car_center_api.client.exceptions.ClientNotFoundException;
import com.carcenter.car_center_api.client.repositories.ClientRepository;
import com.carcenter.car_center_api.vehicle.dtos.*;
import com.carcenter.car_center_api.vehicle.entities.Vehicle;
import com.carcenter.car_center_api.vehicle.exceptions.PlateAlreadyExistsException;
import com.carcenter.car_center_api.vehicle.exceptions.VehicleNotFoundException;
import com.carcenter.car_center_api.vehicle.mappers.VehicleMapper;
import com.carcenter.car_center_api.vehicle.repositories.VehicleRepository;
import com.carcenter.car_center_api.vehicle.services.VehicleServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class VehicleServiceImpl implements VehicleServiceInterface {

    private final VehicleRepository vehicleRepo;
    private final ClientRepository clientRepo;
    private final VehicleMapper vehicleMapper;
    private final BrandRepository brandRepo;

    @Override
    public VehicleResponse create(VehicleCreateRequest dto) {
        if (vehicleRepo.existsByPlate(dto.getPlate())) {
            throw new PlateAlreadyExistsException("Plate already in use: " + dto.getPlate());
        }

        Client client = clientRepo.findByDocument(dto.getClientDocument())
                .orElseThrow(() -> new ClientNotFoundException("Client not found with document: " + dto.getClientDocument()));


        Brand brand = brandRepo.findById(dto.getBrandId())
                .orElseThrow(() -> new BrandNotFoundException("Brand not found with ID: " + dto.getBrandId()));

        Vehicle vehicle = vehicleMapper.toEntity(dto, brand, client);
        return vehicleMapper.toResponse(vehicleRepo.save(vehicle));
    }


    @Override
    @Transactional(readOnly = true)
    public VehicleResponse getById(Long id) {
        Vehicle vehicle = vehicleRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));
        return vehicleMapper.toResponse(vehicle);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VehicleResponse> getAll(Pageable pageable) {
        return vehicleRepo.findAll(pageable)
                .map(vehicleMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehicleResponse> getByClient(Long clientId) {
        return vehicleRepo.findByClientId(clientId)
                .stream()
                .map(vehicleMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public VehicleResponse update(Long id, VehicleUpdateRequest dto) {
        Vehicle vehicle = vehicleRepo.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException("Vehicle not found with ID: " + id));

        Brand brand = brandRepo.findById(dto.getBrandId())
                .orElseThrow(() -> new BrandNotFoundException("Brand not found with ID: " + dto.getBrandId()));

        vehicleMapper.updateVehicleFromDto(vehicle, dto, brand);

        Vehicle updated = vehicleRepo.save(vehicle);
        return vehicleMapper.toResponse(updated);
    }


    @Override
    public void delete(Long id) {
        if (!vehicleRepo.existsById(id)) {
            throw new IllegalArgumentException("Vehicle not found");
        }
        vehicleRepo.deleteById(id);
    }
}
