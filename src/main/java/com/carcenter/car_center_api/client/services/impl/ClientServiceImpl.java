package com.carcenter.car_center_api.client.services.impl;

import com.carcenter.car_center_api.client.dtos.client.*;
import com.carcenter.car_center_api.client.entities.Client;
import com.carcenter.car_center_api.client.repositories.ClientRepository;
import com.carcenter.car_center_api.client.services.ClientServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientServiceImpl implements ClientServiceInterface {

    private final ClientRepository repo;

    private ClientResponse toResponse(Client c) {
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

    @Override
    public ClientResponse create(ClientCreateRequest dto) {
        if (repo.existsByDocumentTypeAndDocument(dto.getDocumentType(), dto.getDocument())) {
            throw new IllegalArgumentException("Client already exists with that document");
        }
        Client c = Client.builder()
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
        return toResponse(repo.save(c));
    }

    @Override
    @Transactional(readOnly = true)
    public ClientResponse getById(Long id) {
        Client c = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));
        return toResponse(c);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ClientResponse> getAll(Pageable pageable) {
        return repo.findAll(pageable).map(this::toResponse);
    }

    @Override
    public ClientResponse update(Long id, ClientUpdateRequest dto) {
        Client c = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));
        c.setFirstName(dto.getFirstName());
        c.setSecondName(dto.getSecondName());
        c.setFirstLastName(dto.getFirstLastName());
        c.setSecondLastName(dto.getSecondLastName());
        c.setCellphone(dto.getCellphone());
        c.setAddress(dto.getAddress());
        c.setEmail(dto.getEmail());
        return toResponse(repo.save(c));
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("Client not found");
        }
        repo.deleteById(id);
    }
}