package com.carcenter.car_center_api.client.mappers;

import com.carcenter.car_center_api.client.dtos.client.*;
import com.carcenter.car_center_api.client.entities.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    public ClientResponse toResponse(Client c) {
        return ClientResponse.builder()
                .id(c.getId())
                .documentType(c.getDocumentType())
                .document(c.getDocument())
                .firstName(c.getFirstName())
                .secondName(c.getSecondName())
                .firstLastName(c.getFirstLastName())
                .secondLastName(c.getSecondLastName())
                .cellphone(c.getCellphone())
                .address(c.getAddress())
                .email(c.getEmail())
                .build();
    }

    public Client toEntity(ClientCreateRequest dto) {
        return Client.builder()
                .documentType(dto.getDocumentType())
                .document(dto.getDocument())
                .firstName(dto.getFirstName())
                .secondName(dto.getSecondName())
                .firstLastName(dto.getFirstLastName())
                .secondLastName(dto.getSecondLastName())
                .cellphone(dto.getCellphone())
                .address(dto.getAddress())
                .email(dto.getEmail())
                .build();
    }

    public void updateEntity(Client c, ClientUpdateRequest dto) {
        c.setFirstName(dto.getFirstName());
        c.setSecondName(dto.getSecondName());
        c.setFirstLastName(dto.getFirstLastName());
        c.setSecondLastName(dto.getSecondLastName());
        c.setCellphone(dto.getCellphone());
        c.setAddress(dto.getAddress());
        c.setEmail(dto.getEmail());
    }
}
