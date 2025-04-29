package com.carcenter.car_center_api.global;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ExceptionResponseBuilder {

    private ExceptionResponseBuilder() {
    }

    public static Map<String, Object> build(HttpStatus status, String message, String error) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("message", message);
        body.put("error", error);
        return body;
    }
}
