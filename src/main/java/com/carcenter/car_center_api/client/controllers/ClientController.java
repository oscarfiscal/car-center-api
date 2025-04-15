package com.carcenter.car_center_api.client.controllers;

import com.carcenter.car_center_api.client.dtos.ClientCreateRequest;
import com.carcenter.car_center_api.client.dtos.ClientResponse;
import com.carcenter.car_center_api.client.dtos.ClientUpdateRequest;
import com.carcenter.car_center_api.client.services.ClientServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
@Validated
@Tag(name = "Client Management", description = "APIs for client operations")
public class ClientController {

    private final ClientServiceInterface service;

    @GetMapping
    @Operation(summary = "List paginated clients", description = "Returns clients in paginated format with sorting")
    @ApiResponse(responseCode = "200", description = "Clients retrieved successfully")
    public ResponseEntity<Page<ClientResponse>> list(
            @ParameterObject
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC)
            Pageable pageable
    ) {
        return ResponseEntity.ok(service.getAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get client by ID", description = "Returns a single client by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client found"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    public ResponseEntity<ClientResponse> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    @Operation(summary = "Create new client", description = "Creates a new client with the provided details")
    @ApiResponse(responseCode = "201", description = "Client created successfully")
    public ResponseEntity<ClientResponse> create(
            @Valid @RequestBody ClientCreateRequest dto) {
        ClientResponse created = service.create(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update client", description = "Updates an existing client's information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client updated successfully"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    public ResponseEntity<ClientResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody ClientUpdateRequest dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete client", description = "Deletes a client by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}