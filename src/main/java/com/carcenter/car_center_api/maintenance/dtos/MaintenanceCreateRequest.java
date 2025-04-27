package com.carcenter.car_center_api.maintenance.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request to create a new maintenance record")
public class MaintenanceCreateRequest {

    @NotNull
    @Schema(description = "Document number of the client", example = "123456789")
    private Integer clientDocument;

    @NotBlank
    @Size(max = 10)
    @Schema(description = "License plate of the vehicle", example = "ABC123")
    private String vehiclePlate;

    @NotBlank
    @Size(max = 255)
    @Schema(description = "Short description of the maintenance to perform", example = "Oil change and brake inspection")
    private String description;

    @NotNull
    @Positive
    @Schema(description = "Maximum budget allowed for the maintenance (COP)", example = "2500000")
    private BigDecimal limitBudget;
}
