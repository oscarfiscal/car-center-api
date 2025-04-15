
package com.carcenter.car_center_api.client.controllers;

import com.carcenter.car_center_api.client.dtos.ClientCreateRequest;
import com.carcenter.car_center_api.client.dtos.ClientResponse;
import com.carcenter.car_center_api.client.dtos.ClientUpdateRequest;
import com.carcenter.car_center_api.client.services.ClientServiceInterface;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClientController.class)
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientServiceInterface clientService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnPaginatedClients() throws Exception {
        ClientResponse response = new ClientResponse(1L, "CC", 123456789, "John", "Doe", "Smith", "Perez", "3001234567", "Calle 1", "john@example.com");
        Page<ClientResponse> page = new PageImpl<>(List.of(response));
        when(clientService.getAll(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].firstName").value("John"));
    }

    @Test
    void shouldReturnClientById() throws Exception {
        ClientResponse response = new ClientResponse(1L, "CC", 123456789, "John", "Doe", "Smith", "Perez", "3001234567", "Calle 1", "john@example.com");
        when(clientService.getById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/clients/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    void shouldCreateClient() throws Exception {
        ClientCreateRequest request = new ClientCreateRequest("CC", 123456789, "John", "Doe", "Smith", "Perez", "3001234567", "Calle 1", "john@example.com");
        ClientResponse response = new ClientResponse(1L, "CC", 123456789, "John", "Doe", "Smith", "Perez", "3001234567", "Calle 1", "john@example.com");

        when(clientService.create(any())).thenReturn(response);

        mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    void shouldUpdateClient() throws Exception {
        ClientUpdateRequest updateRequest = new ClientUpdateRequest("John", "Doe", "Smith", "Perez", "3001234567", "Calle 1", "john@example.com");
        ClientResponse response = new ClientResponse(1L, "CC", 123456789, "John", "Doe", "Smith", "Perez", "3001234567", "Calle 1", "john@example.com");

        when(clientService.update(Mockito.eq(1L), any())).thenReturn(response);

        mockMvc.perform(put("/api/clients/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    void shouldDeleteClient() throws Exception {
        mockMvc.perform(delete("/api/clients/1"))
                .andExpect(status().isOk());
    }
}