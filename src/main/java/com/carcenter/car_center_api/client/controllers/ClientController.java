package com.carcenter.car_center_api.client.controllers;

import com.carcenter.car_center_api.client.dtos.client.*;
import com.carcenter.car_center_api.client.services.ClientServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
@Validated
public class ClientController {

    private final ClientServiceInterface service;

    @GetMapping
    public Page<ClientResponse> list(
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC)
            Pageable pageable) {
        return service.getAll(pageable);
    }

    @GetMapping("/{id}")
    public ClientResponse getOne(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public ResponseEntity<ClientResponse> create(
            @Valid @RequestBody ClientCreateRequest dto) {
        ClientResponse created = service.create(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    @PutMapping("/{id}")
    public ClientResponse update(
            @PathVariable Long id,
            @Valid @RequestBody ClientUpdateRequest dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
        service.delete(id);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Client with ID " + id + " deleted successfully.");
        response.put("statusCode", HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
