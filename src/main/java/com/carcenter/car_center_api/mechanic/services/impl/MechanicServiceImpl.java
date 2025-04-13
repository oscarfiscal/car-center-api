package com.carcenter.car_center_api.mechanic.services.impl;

import com.carcenter.car_center_api.mechanic.dtos.*;
import com.carcenter.car_center_api.mechanic.entities.*;
import com.carcenter.car_center_api.mechanic.services.MechanicServiceInterface;
import com.carcenter.car_center_api.mechanic.repositories.MechanicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MechanicServiceImpl implements MechanicServiceInterface {

    private final MechanicRepository repo;

    private MechanicResponse toResponse(Mechanic m) {
        return MechanicResponse.builder()
                .id(m.getId())
                .documentType(m.getDocumentType())
                .document(m.getDocument())
                .firstName(m.getFirstName())
                .secondName(m.getSecondName())
                .firstLastName(m.getFirstLastName())
                .secondLastName(m.getSecondLastName())
                .cellphone(m.getCellphone())
                .address(m.getAddress())
                .email(m.getEmail())
                .status(m.getStatus())
                .build();
    }

    @Override
    public MechanicResponse create(MechanicCreateRequest dto) {
        if (repo.existsByDocumentTypeAndDocument(dto.getDocumentType(), Integer.valueOf(dto.getDocument()))) {
            throw new IllegalArgumentException("Mechanic already exists");
        }
        Mechanic m = Mechanic.builder()
                .documentType(dto.getDocumentType())
                .document(Integer.valueOf(dto.getDocument()))
                .firstName(dto.getFirstName())
                .secondName(dto.getSecondName())
                .firstLastName(dto.getFirstLastName())
                .secondLastName(dto.getSecondLastName())
                .cellphone(dto.getCellphone())
                .address(dto.getAddress())
                .email(dto.getEmail())
                .status(dto.getStatus())
                .build();
        return toResponse(repo.save(m));
    }

    @Override
    @Transactional(readOnly = true)
    public MechanicResponse getById(Long id) {
        Mechanic m = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Mechanic not found"));
        return toResponse(m);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MechanicResponse> getAll(Pageable pageable) {
        return repo.findAll(pageable).map(this::toResponse);
    }

    @Override
    public MechanicResponse update(Long id, MechanicUpdateRequest dto) {
        Mechanic m = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Mechanic not found"));
        m.setFirstName(dto.getFirstName());
        m.setSecondName(dto.getSecondName());
        m.setFirstLastName(dto.getFirstLastName());
        m.setSecondLastName(dto.getSecondLastName());
        m.setCellphone(dto.getCellphone());
        m.setAddress(dto.getAddress());
        m.setEmail(dto.getEmail());
        m.setStatus(dto.getStatus());
        return toResponse(repo.save(m));
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("Mechanic not found");
        }
        repo.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MechanicResponse> getTopTenAvailable() {
        LocalDate since = LocalDate.now().minusDays(30);
        List<Mechanic> list = repo.findTopByStatusAndHoursSince(
                MechanicStatus.FREE, since, PageRequest.of(0, 10)
        );
        return list.stream().map(this::toResponse).collect(Collectors.toList());
    }
}