package com.carcenter.car_center_api.photo.services.impl;

import com.carcenter.car_center_api.maintenance.entities.Maintenance;
import com.carcenter.car_center_api.maintenance.repositories.MaintenanceRepository;
import com.carcenter.car_center_api.photo.dtos.PhotoCreateRequest;
import com.carcenter.car_center_api.photo.dtos.PhotoResponse;
import com.carcenter.car_center_api.photo.entities.Photo;
import com.carcenter.car_center_api.photo.exceptions.PhotoNotFoundException;
import com.carcenter.car_center_api.photo.mappers.PhotoMapper;
import com.carcenter.car_center_api.photo.repositories.PhotoRepository;
import com.carcenter.car_center_api.photo.services.PhotoServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PhotoServiceImpl implements PhotoServiceInterface {

    private final PhotoRepository photoRepository;
    private final MaintenanceRepository maintenanceRepository;
    private final PhotoMapper photoMapper;

    @Override
    public PhotoResponse create(PhotoCreateRequest dto) {
        Maintenance maintenance = maintenanceRepository.findById(dto.getMaintenanceId())
                .orElseThrow(() -> new IllegalArgumentException("Maintenance not found with ID: " + dto.getMaintenanceId()));

        Photo photo = Photo.builder()
                .path(dto.getPath())
                .maintenance(maintenance)
                .build();

        Photo saved = photoRepository.save(photo);
        return photoMapper.toResponse(saved);
    }

    @Override
    public List<PhotoResponse> getByMaintenanceId(Long maintenanceId) {
        List<Photo> photos = photoRepository.findByMaintenanceId(maintenanceId);
        return photos.stream().map(photoMapper::toResponse).toList();
    }

    @Override
    public void delete(Long id) {
        if (!photoRepository.existsById(id)) {
            throw new PhotoNotFoundException("Photo not found with ID: " + id);
        }
        photoRepository.deleteById(id);
    }
}
