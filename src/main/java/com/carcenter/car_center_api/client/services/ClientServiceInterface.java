package com.carcenter.car_center_api.client.services;



import com.carcenter.car_center_api.client.dtos.client.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientServiceInterface {
    ClientResponse create(ClientCreateRequest dto);
    ClientResponse getById(Long id);
    Page<ClientResponse> getAll(Pageable pageable);
    ClientResponse update(Long id, ClientUpdateRequest dto);
    void delete(Long id);
}

