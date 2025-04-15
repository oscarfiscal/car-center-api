package com.carcenter.car_center_api.mechanicalservice.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MechanicalServiceCreateRequest {

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotNull
    @PositiveOrZero
    private Double price;
}
