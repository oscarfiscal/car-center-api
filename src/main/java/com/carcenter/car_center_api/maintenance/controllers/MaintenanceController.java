package com.carcenter.car_center_api.maintenance.controllers;

import com.carcenter.car_center_api.maintenance.dtos.*;
import com.carcenter.car_center_api.maintenance.services.MaintenanceServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/maintenances")
@RequiredArgsConstructor
@Validated
@Tag(name = "Maintenance Management", description = "APIs for managing maintenance operations")
public class MaintenanceController {

    private final MaintenanceServiceInterface service;

    @GetMapping
    @Operation(summary = "List all maintenances", description = "Returns a paginated list of maintenances")
    @ApiResponse(responseCode = "200", description = "Maintenances retrieved successfully")
    public ResponseEntity<Page<MaintenanceResponse>> list(
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC)
            Pageable pageable
    ) {
        return ResponseEntity.ok(service.getAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get maintenance by ID", description = "Retrieves maintenance details by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Maintenance found"),
            @ApiResponse(responseCode = "404", description = "Maintenance not found")
    })
    public ResponseEntity<MaintenanceResponse> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new maintenance", description = "Creates a new maintenance record")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Maintenance created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<MaintenanceResponse> create(
            @Valid @RequestBody MaintenanceCreateRequest request
    ) {
        MaintenanceResponse created = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update maintenance", description = "Updates maintenance information by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Maintenance updated successfully"),
            @ApiResponse(responseCode = "404", description = "Maintenance not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<MaintenanceResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody MaintenanceUpdateRequest request
    ) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete maintenance", description = "Deletes a maintenance record by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Maintenance deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Maintenance not found")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/assign-mechanic")
    @Operation(summary = "Assign a mechanic", description = "Assigns an available mechanic and updates maintenance status to IN_PROGRESS")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Mechanic assigned successfully"),
            @ApiResponse(responseCode = "404", description = "Maintenance or mechanic not found")
    })
    public ResponseEntity<MaintenanceResponse> assignMechanic(@PathVariable Long id) {
        MaintenanceResponse resp = service.assignMechanic(id);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/{id}/full")
    @Operation(summary = "Get full maintenance details", description = "Returns full maintenance details including client, mechanic, parts, services, and totals")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Full details retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Maintenance not found")
    })
    public ResponseEntity<MaintenanceFullResponse> getFullDetails(@PathVariable Long id) {
        MaintenanceFullResponse full = service.getFullDetails(id);
        return ResponseEntity.ok(full);
    }
}
