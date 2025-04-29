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
import com.carcenter.car_center_api.photo.services.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PhotoServiceImpl implements PhotoServiceInterface {

    private final PhotoRepository photoRepository;
    private final MaintenanceRepository maintenanceRepository;
    private final PhotoMapper photoMapper;
    private final S3UploadService s3UploadService;

    @Override
    public PhotoResponse create(PhotoCreateRequest dto) {
        MultipartFile file = dto.getFile();

        // 1. Obtener mantenimiento
        Maintenance maintenance = maintenanceRepository.findById(dto.getMaintenanceId())
                .orElseThrow(() -> new RuntimeException("Maintenance not found"));

        // 2. Subir archivo a S3
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        String key = "photos/" + filename;
        String contentType = file.getContentType();

        try {
            s3UploadService.uploadFile(key, file.getInputStream(), contentType);
        } catch (IOException e) {
            throw new RuntimeException("Error al subir el archivo a S3", e);
        }

        // 3. Guardar en base de datos
        String s3Url = "https://carcenter-photo-bucket.s3.us-east-2.amazonaws.com/" + key;

        Photo photo = Photo.builder()
                .path(s3Url)
                .maintenance(maintenance)
                .build();

        photoRepository.save(photo);

        return photoMapper.toResponse(photo); // o crear manualmente el DTO
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
