package com.carcenter.car_center_api.mechanic.services;

import com.carcenter.car_center_api.mechanic.dtos.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MechanicServiceInterface {
    MechanicResponse create(MechanicCreateRequest dto);
    MechanicResponse getById(Long id);
    Page<MechanicResponse> getAll(Pageable pageable);
    MechanicResponse update(Long id, MechanicUpdateRequest dto);
    void delete(Long id);
    List<MechanicResponse> getTopTenAvailable();
}