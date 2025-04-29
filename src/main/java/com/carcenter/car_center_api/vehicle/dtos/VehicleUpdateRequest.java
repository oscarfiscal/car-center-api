package com.carcenter.car_center_api.vehicle.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleUpdateRequest {

    @Schema(description = "ID de la marca del vehículo", example = "1")
    @NotNull(message = "El ID de la marca es obligatorio")
    private Long brandId;

    @Schema(description = "Modelo del vehículo", example = "Corolla")
    @NotBlank(message = "El modelo es obligatorio")
    @Size(max = 50, message = "El modelo no debe superar los 50 caracteres")
    private String model;

    @Schema(description = "Año del vehículo", example = "2020")
    @NotNull(message = "El año es obligatorio")
    @Min(value = 1900, message = "El año debe ser mayor o igual a 1900")
    @Max(value = 2100, message = "El año debe ser menor o igual a 2100")
    private Integer year;

    @Schema(description = "Color del vehículo", example = "Rojo")
    @NotBlank(message = "El color es obligatorio")
    @Size(max = 20, message = "El color no debe superar los 20 caracteres")
    private String color;
}
