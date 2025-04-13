package com.carcenter.car_center_api.vehicle.mappers;


import com.carcenter.car_center_api.client.entities.Client;
import com.carcenter.car_center_api.vehicle.dtos.*;
import com.carcenter.car_center_api.vehicle.entities.Vehicle;
import org.springframework.stereotype.Component;

@Component
public class VehicleMapper {

    public VehicleResponse toResponse(Vehicle v) {
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

    public Vehicle toEntity(VehicleCreateRequest dto, Client client) {
        return Vehicle.builder()
                .plate(dto.getPlate())
                .brand(dto.getBrand())
                .model(dto.getModel())
                .year(dto.getYear())
                .color(dto.getColor())
                .client(client)
                .build();
    }

    public void updateVehicleFromDto(Vehicle v, VehicleUpdateRequest dto) {
        v.setBrand(dto.getBrand());
        v.setModel(dto.getModel());
        v.setYear(dto.getYear());
        v.setColor(dto.getColor());
    }
}
