package com.carcenter.car_center_api.photo.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class S3UploadService {

    private final S3Client s3Client;

    private final String bucketName = "carcenter-photo-bucket"; // cambia según tu nombre real

    public void uploadFile(String key, InputStream inputStream, String contentType) throws IOException {
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(contentType)
                .build();

        s3Client.putObject(request, RequestBody.fromInputStream(inputStream, inputStream.available()));
    }
}
