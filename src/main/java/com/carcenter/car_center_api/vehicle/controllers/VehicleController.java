package com.carcenter.car_center_api.vehicle.controllers;

import com.carcenter.car_center_api.vehicle.dtos.VehicleCreateRequest;
import com.carcenter.car_center_api.vehicle.dtos.VehicleResponse;
import com.carcenter.car_center_api.vehicle.dtos.VehicleUpdateRequest;
import com.carcenter.car_center_api.vehicle.services.VehicleServiceInterface;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
@Validated
@Tag(name = "Vehicle Management", description = "APIs for managing vehicles")
public class VehicleController {

    private final VehicleServiceInterface service;

    @GetMapping
    @Operation(summary = "List paginated vehicles", description = "Returns a paginated list of vehicles sorted by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicles retrieved successfully")
    })
    public ResponseEntity<Page<VehicleResponse>> list(
            @ParameterObject
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC)
            Pageable pageable) {
        return ResponseEntity.ok(service.getAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get vehicle by ID", description = "Returns a vehicle by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicle found"),
            @ApiResponse(responseCode = "404", description = "Vehicle not found")
    })
    public ResponseEntity<VehicleResponse> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/by-client/{clientId}")
    @Operation(summary = "List vehicles by client ID", description = "Returns vehicles associated with a specific client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicles retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    public ResponseEntity<List<VehicleResponse>> listByClient(@PathVariable Long clientId) {
        return ResponseEntity.ok(service.getByClient(clientId));
    }

    @PostMapping
    @Operation(summary = "Create a new vehicle", description = "Registers a new vehicle in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vehicle created successfully")
    })
    public ResponseEntity<VehicleResponse> create(
            @Valid @RequestBody VehicleCreateRequest dto) {
        VehicleResponse created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a vehicle", description = "Updates information about an existing vehicle")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicle updated successfully"),
            @ApiResponse(responseCode = "404", description = "Vehicle not found")
    })
    public ResponseEntity<VehicleResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody VehicleUpdateRequest dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a vehicle", description = "Deletes a vehicle by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicle deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Vehicle not found")
    })
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
        service.delete(id);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Vehicle with ID " + id + " deleted successfully.");
        response.put("statusCode", HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }
}
