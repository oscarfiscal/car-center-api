package com.carcenter.car_center_api.photo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice(basePackages = "com.carcenter.car_center_api.photo.controllers")
public class PhotoExceptionHandler {

  @ExceptionHandler(PhotoNotFoundException.class)
  public ResponseEntity<Object> handlePhotoNotFound(PhotoNotFoundException ex) {
    Map<String, Object> body = new HashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("status", HttpStatus.NOT_FOUND.value());
    body.put("error", "Photo not found");
    body.put("message", ex.getMessage());
    return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
  }
}
