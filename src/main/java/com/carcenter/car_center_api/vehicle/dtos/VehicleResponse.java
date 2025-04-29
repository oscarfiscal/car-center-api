// Archivo: VehicleResponse.java
package com.carcenter.car_center_api.vehicle.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleResponse {

    @Schema(description = "Vehicle ID")
    private Long id;

    @Schema(description = "License plate")
    private String plate;

    @Schema(description = "Brand ID")
    private Long brandId;

    @Schema(description = "Brand name")
    private String brandName;

    @Schema(description = "Model")
    private String model;

    @Schema(description = "Year")
    private Integer year;

    @Schema(description = "Color")
    private String color;

    @Schema(description = "Client information")
    private ClientInfo client;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ClientInfo {
        private String documentType;
        private Integer document;
        private String firstName;
        private String secondName;
        private String firstLastName;
        private String secondLastName;
        private String cellphone;
        private String address;
        private String email;
    }
}
