package com.carcenter.car_center_api.service.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceCreateRequest {

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotNull
    @PositiveOrZero
    private Double price;
}
