package com.carcenter.car_center_api.client.mappers;

import com.carcenter.car_center_api.client.dtos.ClientCreateRequest;
import com.carcenter.car_center_api.client.dtos.ClientResponse;
import com.carcenter.car_center_api.client.dtos.ClientUpdateRequest;
import com.carcenter.car_center_api.client.entities.Client;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.function.Consumer;

@Component
public class ClientMapper {

    public ClientResponse toResponse(Client client) {
        if (client == null) {
            return null;
        }
        return ClientResponse.builder()
                .id(client.getId())
                .documentType(client.getDocumentType())
                .document(client.getDocument())
                .firstName(client.getFirstName())
                .secondName(client.getSecondName())
                .firstLastName(client.getFirstLastName())
                .secondLastName(client.getSecondLastName())
                .cellphone(client.getCellphone())
                .address(client.getAddress())
                .email(client.getEmail())
                .build();
    }

    public Client toEntity(ClientCreateRequest dto) {
        if (dto == null) {
            return null;
        }

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

    public void updateEntity(Client client, ClientUpdateRequest dto) {
        if (client == null || dto == null) {
            return;
        }

        updateIfNotBlank(dto.getFirstName(), client::setFirstName);
        updateIfNotBlank(dto.getSecondName(), client::setSecondName);
        updateIfNotBlank(dto.getFirstLastName(), client::setFirstLastName);
        updateIfNotBlank(dto.getSecondLastName(), client::setSecondLastName);
        updateIfNotBlank(dto.getCellphone(), client::setCellphone);
        updateIfNotBlank(dto.getAddress(), client::setAddress);
        updateIfNotBlank(dto.getEmail(), client::setEmail);
    }

    private void updateIfNotBlank(String value, Consumer<String> setter) {
        if (StringUtils.hasText(value)) {
            setter.accept(value);
        }
    }
}
