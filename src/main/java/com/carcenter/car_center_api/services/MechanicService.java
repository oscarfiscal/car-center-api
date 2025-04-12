package com.carcenter.car_center_api.services;

import com.carcenter.car_center_api.dtos.mechanic.MechanicCreateRequest;
import com.carcenter.car_center_api.dtos.mechanic.MechanicResponse;
import com.carcenter.car_center_api.dtos.mechanic.MechanicUpdateRequest;
import com.carcenter.car_center_api.entities.Mechanic;
import com.carcenter.car_center_api.repositories.MechanicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.PageRequest;


import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MechanicService {

    private final MechanicRepository mechanicRepository;

    /**
     * Crea un nuevo mecánico a partir del DTO de creación, validando que cumpla los campos.
     */
    public MechanicResponse createMechanic(MechanicCreateRequest dto) {
        Mechanic mechanic = Mechanic.builder()
                .firstName(dto.getFirstName())
                .secondName(dto.getSecondName())
                .firstLastName(dto.getFirstLastName())
                .secondLastName(dto.getSecondLastName())
                .document(dto.getDocument())
                .documentType(dto.getDocumentType())
                .email(dto.getEmail())
                .cellphone(dto.getCellphone())
                .address(dto.getAddress())
                .status(dto.getStatus() == null ? "AVAILABLE" : dto.getStatus())
                .build();

        // Podríamos verificar si el documento o email ya existen, etc.
        Mechanic saved = mechanicRepository.save(mechanic);

        return mapToResponse(saved);
    }

    /**
     * Retorna todos los mecánicos en formato DTO de respuesta.
     */
    public List<MechanicResponse> getAllMechanics() {
        return mechanicRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Retorna un mecánico por ID en DTO.
     */
    public MechanicResponse getMechanic(Long id) {
        Mechanic mech = mechanicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mechanic not found"));
        return mapToResponse(mech);
    }

    /**
     * Actualiza ciertos campos de un mecánico existente.
     */
    public MechanicResponse updateMechanic(Long id, MechanicUpdateRequest dto) {
        Mechanic existing = mechanicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mechanic not found"));

        existing.setFirstName(dto.getFirstName());
        // secondName no se actualiza en este request, por ejemplo
        if (dto.getEmail() != null) {
            existing.setEmail(dto.getEmail());
        }
        if (dto.getCellphone() != null) {
            existing.setCellphone(dto.getCellphone());
        }
        if (dto.getAddress() != null) {
            existing.setAddress(dto.getAddress());
        }
        if (dto.getStatus() != null) {
            existing.setStatus(dto.getStatus());
        }

        Mechanic saved = mechanicRepository.save(existing);
        return mapToResponse(saved);
    }

    /**
     * Elimina un mecánico por ID.
     */
    public void deleteMechanic(Long id) {
        Mechanic mech = mechanicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mechanic not found"));
        mechanicRepository.delete(mech);
    }

    /**
     * Obtiene los 10 mecánicos disponibles con menor cantidad de horas en el último mes.
     */
    public List<MechanicResponse> findTop10MechanicsAvailable() {
        LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);
        return mechanicRepository.findTopMechanicsWithLowestHours(oneMonthAgo, PageRequest.of(0, 10))
                .stream().map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Método helper para mapear un Mechanic a MechanicResponse.
     */
    private MechanicResponse mapToResponse(Mechanic mechanic) {
        MechanicResponse resp = new MechanicResponse();
        resp.setId(mechanic.getId());
        resp.setFirstName(mechanic.getFirstName());
        resp.setSecondName(mechanic.getSecondName());
        resp.setFirstLastName(mechanic.getFirstLastName());
        resp.setSecondLastName(mechanic.getSecondLastName());
        resp.setDocument(mechanic.getDocument());
        resp.setDocumentType(mechanic.getDocumentType());
        resp.setEmail(mechanic.getEmail());
        resp.setCellphone(mechanic.getCellphone());
        resp.setAddress(mechanic.getAddress());
        resp.setStatus(mechanic.getStatus());
        return resp;
    }
}