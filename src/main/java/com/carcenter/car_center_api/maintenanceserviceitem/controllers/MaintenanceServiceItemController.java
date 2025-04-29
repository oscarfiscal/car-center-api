package com.carcenter.car_center_api.maintenanceserviceitem.controllers;

import com.carcenter.car_center_api.maintenanceserviceitem.dtos.MaintenanceServiceItemCreateRequest;
import com.carcenter.car_center_api.maintenanceserviceitem.dtos.MaintenanceServiceItemResponse;
import com.carcenter.car_center_api.maintenanceserviceitem.services.MaintenanceServiceItemServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/maintenance-services")
@RequiredArgsConstructor
@Validated
@Tag(name = "Maintenance Service Items", description = "APIs for managing services assigned to maintenance")
public class MaintenanceServiceItemController {

    private final MaintenanceServiceItemServiceInterface service;

    @Operation(summary = "Get maintenance service item by ID", description = "Returns a specific maintenance service item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Maintenance service item found"),
            @ApiResponse(responseCode = "404", description = "Maintenance service item not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MaintenanceServiceItemResponse> getOne(@PathVariable Long id) {
        MaintenanceServiceItemResponse response = service.getById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Create a new maintenance service item", description = "Creates a new maintenance service item and updates maintenance costs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Maintenance service item created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<MaintenanceServiceItemResponse> create(
            @Valid @RequestBody MaintenanceServiceItemCreateRequest dto
    ) {
        MaintenanceServiceItemResponse created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
