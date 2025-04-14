package com.carcenter.car_center_api.client.services.impl;

import com.carcenter.car_center_api.client.dtos.client.*;
import com.carcenter.car_center_api.client.entities.Client;
import com.carcenter.car_center_api.client.mappers.ClientMapper;
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
    private final ClientMapper clientMapper;

    @Override
    public ClientResponse create(ClientCreateRequest dto) {
        if (repo.existsByDocumentTypeAndDocument(dto.getDocumentType(), dto.getDocument())) {
            throw new IllegalArgumentException("Client already exists with that document");
        }

        Client client = clientMapper.toEntity(dto);
        return clientMapper.toResponse(repo.save(client));
    }

    @Override
    @Transactional(readOnly = true)
    public ClientResponse getById(Long id) {
        Client client = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));
        return clientMapper.toResponse(client);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ClientResponse> getAll(Pageable pageable) {
        return repo.findAll(pageable)
                .map(clientMapper::toResponse);
    }

    @Override
    public ClientResponse update(Long id, ClientUpdateRequest dto) {
        Client client = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));

        clientMapper.updateEntity(client, dto);
        return clientMapper.toResponse(repo.save(client));
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("Client not found");
        }
        repo.deleteById(id);
    }
}
