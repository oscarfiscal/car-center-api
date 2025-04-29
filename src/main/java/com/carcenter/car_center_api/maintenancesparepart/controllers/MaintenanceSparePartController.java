package com.carcenter.car_center_api.maintenancesparepart.controllers;

import com.carcenter.car_center_api.maintenancesparepart.dtos.MaintenanceSparePartCreateRequest;
import com.carcenter.car_center_api.maintenancesparepart.dtos.MaintenanceSparePartResponse;
import com.carcenter.car_center_api.maintenancesparepart.services.MaintenanceSparePartServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/maintenance-spare-parts")
@RequiredArgsConstructor
@Validated
@Tag(name = "Maintenance Spare Parts", description = "Manage spare parts assigned to maintenances")
public class MaintenanceSparePartController {

    private final MaintenanceSparePartServiceInterface service;

    @GetMapping("/maintenance/{maintenanceId}")
    @Operation(summary = "List spare parts by maintenance", description = "Returns all spare parts associated with a specific maintenance")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    public ResponseEntity<List<MaintenanceSparePartResponse>> listByMaintenance(@PathVariable Long maintenanceId) {
        List<MaintenanceSparePartResponse> responses = service.getByMaintenance(maintenanceId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/spare-part/{sparePartId}")
    @Operation(summary = "List maintenances by spare part", description = "Returns all maintenances associated with a specific spare part")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    public ResponseEntity<List<MaintenanceSparePartResponse>> listBySparePart(@PathVariable Long sparePartId) {
        List<MaintenanceSparePartResponse> responses = service.getBySparePart(sparePartId);
        return ResponseEntity.ok(responses);
    }

    @PostMapping
    @Operation(summary = "Assign spare part to maintenance", description = "Creates a new association between a spare part and a maintenance")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Spare part assigned successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<MaintenanceSparePartResponse> create(
            @Valid @RequestBody MaintenanceSparePartCreateRequest dto) {
        MaintenanceSparePartResponse created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
