package com.carcenter.car_center_api.maintenance.controllers;

import com.carcenter.car_center_api.maintenance.dtos.*;
import com.carcenter.car_center_api.maintenance.services.MaintenanceServiceInterface;
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
public class MaintenanceController {

    private final MaintenanceServiceInterface service;

    @GetMapping
    public Page<MaintenanceResponse> list(
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC)
            Pageable pageable) {
        return service.getAll(pageable);
    }

    @GetMapping("/{id}")
    public MaintenanceResponse getOne(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public ResponseEntity<MaintenanceResponse> create(
            @Valid @RequestBody MaintenanceCreateRequest request) {
        MaintenanceResponse created = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public MaintenanceResponse update(
            @PathVariable Long id,
            @Valid @RequestBody MaintenanceUpdateRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
