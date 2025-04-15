package com.carcenter.car_center_api.maintenanceserviceitem.controllers;

import com.carcenter.car_center_api.maintenanceserviceitem.dtos.*;
import com.carcenter.car_center_api.maintenanceserviceitem.services.MaintenanceServiceItemServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/maintenance-services")
@RequiredArgsConstructor
@Validated
public class MaintenanceServiceItemController {

    private final MaintenanceServiceItemServiceInterface service;



    @GetMapping("/{id}")
    public MaintenanceServiceItemResponse getOne(@PathVariable Long id) {
        return service.getById(id);
    }


    @PostMapping
    public ResponseEntity<MaintenanceServiceItemResponse> create(
            @Valid @RequestBody MaintenanceServiceItemCreateRequest dto
    ) {
        MaintenanceServiceItemResponse created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }


}
