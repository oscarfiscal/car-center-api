package com.carcenter.car_center_api.photo.controllers;

import com.carcenter.car_center_api.photo.dtos.PhotoCreateRequest;
import com.carcenter.car_center_api.photo.dtos.PhotoResponse;
import com.carcenter.car_center_api.photo.services.PhotoServiceInterface;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/photos")
@RequiredArgsConstructor
@Tag(name = "Photo Management", description = "Endpoints for photo operations")
public class PhotoController {

    private final PhotoServiceInterface service;

    @PostMapping
    public ResponseEntity<PhotoResponse> create(@Valid @RequestBody PhotoCreateRequest dto) {
        PhotoResponse created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/maintenance/{maintenanceId}")
    public ResponseEntity<List<PhotoResponse>> getByMaintenance(@PathVariable Long maintenanceId) {
        return ResponseEntity.ok(service.getByMaintenanceId(maintenanceId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
