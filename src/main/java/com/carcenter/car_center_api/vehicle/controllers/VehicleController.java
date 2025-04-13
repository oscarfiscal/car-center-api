package com.carcenter.car_center_api.vehicle.controllers;

import com.carcenter.car_center_api.vehicle.dtos.*;
import com.carcenter.car_center_api.vehicle.services.VehicleServiceInterface;
import lombok.RequiredArgsConstructor;
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
public class VehicleController {

    private final VehicleServiceInterface service;

    @GetMapping
    public Page<VehicleResponse> list(
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC)
            Pageable pageable) {
        return service.getAll(pageable);
    }

    @GetMapping("/{id}")
    public VehicleResponse getOne(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/by-client/{clientId}")
    public List<VehicleResponse> listByClient(@PathVariable Long clientId) {
        return service.getByClient(clientId);
    }

    @PostMapping
    public ResponseEntity<VehicleResponse> create(
            @Valid @RequestBody VehicleCreateRequest dto) {
        VehicleResponse created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public VehicleResponse update(
            @PathVariable Long id,
            @Valid @RequestBody VehicleUpdateRequest dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
        service.delete(id);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Vehicle with ID " + id + " deleted successfully.");
        response.put("statusCode", HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
