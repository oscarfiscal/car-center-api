package com.carcenter.car_center_api.client.services.impl;

import com.carcenter.car_center_api.client.dtos.ClientCreateRequest;
import com.carcenter.car_center_api.client.dtos.ClientResponse;
import com.carcenter.car_center_api.client.dtos.ClientUpdateRequest;
import com.carcenter.car_center_api.client.entities.Client;
import com.carcenter.car_center_api.client.exceptions.ClientAlreadyExistsException;
import com.carcenter.car_center_api.client.exceptions.ClientNotFoundException;
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

    private static final String CLIENT_NOT_FOUND_MESSAGE = "Client not found with ID: ";
    private static final String CLIENT_ALREADY_EXISTS_MESSAGE = "A client already exists with document type: %s and number: %s";

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    public ClientResponse create(ClientCreateRequest createRequest) {


        if (clientRepository.existsByDocumentTypeAndDocument(createRequest.getDocumentType(), createRequest.getDocument())) {
            String errorMessage = String.format(CLIENT_ALREADY_EXISTS_MESSAGE,
                    createRequest.getDocumentType(),
                    createRequest.getDocument());
            throw new ClientAlreadyExistsException(errorMessage);
        }

        Client client = clientMapper.toEntity(createRequest);
        Client savedClient = clientRepository.save(client);

        return clientMapper.toResponse(savedClient);
    }

    @Override
    @Transactional(readOnly = true)
    public ClientResponse getById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> {
                    return new ClientNotFoundException(CLIENT_NOT_FOUND_MESSAGE + id);
                });
        return clientMapper.toResponse(client);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ClientResponse> getAll(Pageable pageable) {
        return clientRepository.findAll(pageable)
                .map(clientMapper::toResponse);
    }

    @Override
    public ClientResponse update(Long id, ClientUpdateRequest updateRequest) {

        Client client = clientRepository.findById(id)
                .orElseThrow(() -> {
                    return new ClientNotFoundException(CLIENT_NOT_FOUND_MESSAGE + id);
                });

        clientMapper.updateEntity(client, updateRequest);
        Client updatedClient = clientRepository.save(client);

        return clientMapper.toResponse(updatedClient);
    }

    @Override
    public void delete(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new ClientNotFoundException(CLIENT_NOT_FOUND_MESSAGE + id);
        }
        clientRepository.deleteById(id);

    }
}