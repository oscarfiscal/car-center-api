package com.carcenter.car_center_api.brand.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Brand {

    private Long id;

    @NotBlank(message = "El nombre de la marca no puede estar vacío")
    private String name;
}
