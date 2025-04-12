package com.carcenter.car_center_api.controllers;


import com.carcenter.car_center_api.dtos.mechanic.MechanicCreateRequest;
import com.carcenter.car_center_api.dtos.mechanic.MechanicResponse;
import com.carcenter.car_center_api.dtos.mechanic.MechanicUpdateRequest;
import com.carcenter.car_center_api.services.MechanicService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoints para CRUD de Mecánicos y top 10 disponibles.
 */
@RestController
@RequestMapping("/api/mechanics")
@RequiredArgsConstructor
public class MechanicController {

    private final MechanicService mechanicService;

    /**
     * Crea un mecánico nuevo recibiendo un DTO con validaciones.
     */
    @PostMapping
    public ResponseEntity<MechanicResponse> create(@Valid @RequestBody MechanicCreateRequest dto) {
        System.out.println(dto);
        MechanicResponse created = mechanicService.createMechanic(dto);
        return ResponseEntity.ok(created);
    }

    /**
     * Lista todos los mecánicos como DTOs.
     */
    @GetMapping
    public ResponseEntity<List<MechanicResponse>> getAll() {
        return ResponseEntity.ok(mechanicService.getAllMechanics());
    }

    /**
     * Retorna un mecánico por ID, en formato DTO.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MechanicResponse> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(mechanicService.getMechanic(id));
    }

    /**
     * Actualiza campos de un mecánico usando un DTO de actualización.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MechanicResponse> update(@PathVariable Long id,
                                                   @Valid @RequestBody MechanicUpdateRequest dto) {
        return ResponseEntity.ok(mechanicService.updateMechanic(id, dto));
    }

    /**
     * Elimina un mecánico existente.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        mechanicService.deleteMechanic(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint para obtener los 10 mecánicos disponibles con menor horas en el último mes.
     */
    @GetMapping("/available/top10")
    public ResponseEntity<List<MechanicResponse>> getTop10() {
        return ResponseEntity.ok(mechanicService.findTop10MechanicsAvailable());
    }
}


