package com.carcenter.car_center_api.mechanic.controllers;

import com.carcenter.car_center_api.mechanic.dtos.*;
import com.carcenter.car_center_api.mechanic.services.MechanicServiceInterface;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/mechanics")
@RequiredArgsConstructor
@Validated
public class MechanicController {

    private final MechanicServiceInterface service;

    @GetMapping
    public Page<MechanicResponse> list(
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC)
            Pageable pageable) {
        return service.getAll(pageable);
    }

    @GetMapping("/{id}")
    public MechanicResponse getOne(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public ResponseEntity<MechanicResponse> create(
            @Valid @RequestBody MechanicCreateRequest dto) {
        MechanicResponse created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public MechanicResponse update(
            @PathVariable Long id,
            @Valid @RequestBody MechanicUpdateRequest dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/top-ten")
    public List<MechanicResponse> topTen() {
        return service.getTopTenAvailable();
    }
}