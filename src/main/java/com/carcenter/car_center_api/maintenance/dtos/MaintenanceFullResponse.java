package com.carcenter.car_center_api.maintenance.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Detailed response for a maintenance record including client, mechanic, parts, and services")
public class MaintenanceFullResponse {

    @Schema(description = "Maintenance ID", example = "1")
    private Long id;

    @Schema(description = "Maintenance description", example = "Oil change and brake inspection")
    private String description;

    @Schema(description = "Maximum allowed budget for the maintenance", example = "2500000")
    private BigDecimal limitBudget;

    @Schema(description = "Current status of the maintenance", example = "IN_PROGRESS")
    private String status;

    @Schema(description = "Total hours worked on the maintenance", example = "5")
    private Integer hoursWorked;

    @Schema(description = "Start date of the maintenance", example = "2024-04-26")
    private LocalDate startDate;

    @Schema(description = "End date of the maintenance", example = "2024-04-28")
    private LocalDate endDate;

    private ClientInfo client;
    private MechanicInfo mechanic;
    private List<SparePartItem> spareParts;
    private List<ServiceItem> serviceItems;

    @Schema(description = "Total cost of the maintenance", example = "2200000")
    private BigDecimal totalCost;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "Client information related to the maintenance")
    public static class ClientInfo {
        private Long id;
        private String firstName;
        private String secondName;
        private String firstLastName;
        private String secondLastName;
        private String documentType;
        private Integer document;
        private String cellphone;
        private String address;
        private String email;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "Mechanic information assigned to the maintenance")
    public static class MechanicInfo {
        private Long id;
        private String firstName;
        private String secondName;
        private String firstLastName;
        private String secondLastName;
        private String documentType;
        private Integer document;
        private String cellphone;
        private String address;
        private String email;
        private String status;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "Spare parts used in the maintenance")
    public static class SparePartItem {
        private Long id;
        private String name;
        private Integer quantity;

        @Schema(description = "Unit price of the spare part", example = "25000")
        private BigDecimal unitPrice;

        @Schema(description = "Total cost for the quantity of this spare part", example = "50000")
        private BigDecimal lineTotal;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "Services performed during the maintenance")
    public static class ServiceItem {
        private Long id;
        private String name;
        private Integer estimatedTime;

        @Schema(description = "Unit price for the service", example = "80000")
        private BigDecimal unitPrice;

        @Schema(description = "Total cost for the service based on estimated time", example = "160000")
        private BigDecimal lineTotal;
    }
}
