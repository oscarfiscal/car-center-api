package com.carcenter.car_center_api.maintenancesparepart.controllers;

import com.carcenter.car_center_api.maintenancesparepart.dtos.*;
import com.carcenter.car_center_api.maintenancesparepart.services.MaintenanceSparePartServiceInterface;
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
public class MaintenanceSparePartController {

    private final MaintenanceSparePartServiceInterface service;


    @GetMapping("/by-maintenance/{maintenanceId}")
    public List<MaintenanceSparePartResponse> listByMaintenance(@PathVariable Long maintenanceId) {
        return service.getByMaintenance(maintenanceId);
    }

    @GetMapping("/by-spare-part/{sparePartId}")
    public List<MaintenanceSparePartResponse> listBySparePart(@PathVariable Long sparePartId) {
        return service.getBySparePart(sparePartId);
    }

    @PostMapping
    public ResponseEntity<MaintenanceSparePartResponse> create(
            @Valid @RequestBody MaintenanceSparePartCreateRequest dto
    ) {
        MaintenanceSparePartResponse created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}