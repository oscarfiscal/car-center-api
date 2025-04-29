package com.carcenter.car_center_api.vehicle.mappers;

import com.carcenter.car_center_api.brand.entities.Brand;
import com.carcenter.car_center_api.brand.repositories.BrandRepository;
import com.carcenter.car_center_api.client.entities.Client;
import com.carcenter.car_center_api.vehicle.dtos.*;
import com.carcenter.car_center_api.vehicle.entities.Vehicle;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VehicleMapper {

    private final BrandRepository brandRepository;

    public VehicleResponse toResponse(Vehicle v) {
        return VehicleResponse.builder()
                .id(v.getId())
                .plate(v.getPlate())
                .brandId(v.getBrand().getId())
                .brandName(v.getBrand().getName())
                .model(v.getModel())
                .year(v.getYear())
                .color(v.getColor())
                .client(new VehicleResponse.ClientInfo(
                        v.getClient().getDocumentType(),
                        v.getClient().getDocument(),
                        v.getClient().getFirstName(),
                        v.getClient().getSecondName(),
                        v.getClient().getFirstLastName(),
                        v.getClient().getSecondLastName(),
                        v.getClient().getCellphone(),
                        v.getClient().getAddress(),
                        v.getClient().getEmail()
                ))
                .build();
    }


    public Vehicle toEntity(VehicleCreateRequest dto, Brand brand, Client client) {
        return Vehicle.builder()
                .plate(dto.getPlate())
                .brand(brand)
                .model(dto.getModel())
                .year(dto.getYear())
                .color(dto.getColor())
                .client(client)
                .build();
    }

    public void updateVehicleFromDto(Vehicle vehicle, VehicleUpdateRequest dto, Brand brand) {
        vehicle.setBrand(brand);
        vehicle.setModel(dto.getModel());
        vehicle.setYear(dto.getYear());
        vehicle.setColor(dto.getColor());
    }

}
