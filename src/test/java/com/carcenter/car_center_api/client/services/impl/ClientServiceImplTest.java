package com.carcenter.car_center_api.client.services.impl;

import com.carcenter.car_center_api.client.dtos.ClientCreateRequest;
import com.carcenter.car_center_api.client.dtos.ClientResponse;
import com.carcenter.car_center_api.client.dtos.ClientUpdateRequest;
import com.carcenter.car_center_api.client.entities.Client;
import com.carcenter.car_center_api.client.exceptions.ClientAlreadyExistsException;
import com.carcenter.car_center_api.client.exceptions.ClientNotFoundException;
import com.carcenter.car_center_api.client.mappers.ClientMapper;
import com.carcenter.car_center_api.client.repositories.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClientServiceImplTest {

    @InjectMocks
    private ClientServiceImpl clientService;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientMapper clientMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_shouldReturnClientResponse_whenClientIsNew() {
        ClientCreateRequest request = new ClientCreateRequest("CC", 123456789, "John", "Doe", "Smith", "Perez", "3001234567", "Calle 1", "john@example.com");
        Client client = Client.builder().documentType("CC").document(123456789).firstName("John").build();
        Client savedClient = Client.builder().id(1L).documentType("CC").document(123456789).firstName("John").build();
        ClientResponse expectedResponse = new ClientResponse(1L, "CC", 123456789, "John", "Doe", "Smith", "Perez", "3001234567", "Calle 1", "john@example.com");

        when(clientRepository.existsByDocumentTypeAndDocument("CC", 123456789)).thenReturn(false);
        when(clientMapper.toEntity(request)).thenReturn(client);
        when(clientRepository.save(client)).thenReturn(savedClient);
        when(clientMapper.toResponse(savedClient)).thenReturn(expectedResponse);

        ClientResponse result = clientService.create(request);

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        verify(clientRepository).save(client);
    }

    @Test
    void create_shouldThrowException_whenClientExists() {
        ClientCreateRequest request = new ClientCreateRequest("CC", 123456789, "John", "Doe", "Smith", "Perez", "3001234567", "Calle 1", "john@example.com");

        when(clientRepository.existsByDocumentTypeAndDocument("CC", 123456789)).thenReturn(true);

        assertThrows(ClientAlreadyExistsException.class, () -> clientService.create(request));
        verify(clientRepository, never()).save(any());
    }

    @Test
    void getById_shouldReturnClientResponse_whenClientExists() {
        Client client = Client.builder().id(1L).firstName("John").build();
        ClientResponse response = new ClientResponse(1L, "CC", 123456789, "John", "", "", "", "", "", "");

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(clientMapper.toResponse(client)).thenReturn(response);

        ClientResponse result = clientService.getById(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void getById_shouldThrowException_whenClientDoesNotExist() {
        when(clientRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> clientService.getById(99L));
    }

    @Test
    void getAll_shouldReturnPagedClientResponses() {
        Pageable pageable = PageRequest.of(0, 2);
        List<Client> clients = List.of(Client.builder().id(1L).firstName("John").build());
        Page<Client> page = new PageImpl<>(clients);
        ClientResponse response = new ClientResponse(1L, "CC", 123456789, "John", "", "", "", "", "", "");

        when(clientRepository.findAll(pageable)).thenReturn(page);
        when(clientMapper.toResponse(any())).thenReturn(response);

        Page<ClientResponse> result = clientService.getAll(pageable);

        assertEquals(1, result.getTotalElements());
    }

    @Test
    void update_shouldUpdateClient_whenExists() {
        Client client = Client.builder().id(1L).firstName("OldName").build();
        ClientUpdateRequest updateRequest = new ClientUpdateRequest("NewName", null, null, null, null, null, null);
        Client updatedClient = Client.builder().id(1L).firstName("NewName").build();
        ClientResponse response = new ClientResponse(1L, "CC", 123456789, "NewName", "", "", "", "", "", "");

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        doAnswer(invocation -> {
            client.setFirstName("NewName");
            return null;
        }).when(clientMapper).updateEntity(client, updateRequest);
        when(clientRepository.save(client)).thenReturn(updatedClient);
        when(clientMapper.toResponse(updatedClient)).thenReturn(response);

        ClientResponse result = clientService.update(1L, updateRequest);

        assertEquals("NewName", result.getFirstName());
    }

    @Test
    void delete_shouldRemoveClient_whenExists() {
        when(clientRepository.existsById(1L)).thenReturn(true);
        doNothing().when(clientRepository).deleteById(1L);

        assertDoesNotThrow(() -> clientService.delete(1L));
    }

    @Test
    void delete_shouldThrowException_whenClientDoesNotExist() {
        when(clientRepository.existsById(99L)).thenReturn(false);

        assertThrows(ClientNotFoundException.class, () -> clientService.delete(99L));
    }
}