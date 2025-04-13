package com.carcenter.car_center_api.maintenance.dtos;

import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class MaintenanceFullResponse {
    private Long id;
    private String description;
    private Double limitBudget;
    private String status;
    private Integer hoursWorked;
    private LocalDate startDate;
    private LocalDate endDate;
    private ClientInfo client;
    private MechanicInfo mechanic;
    private List<SparePartItem> spareParts;
    private List<ServiceItem> serviceItems;
    private Double totalCost;

    @Data @AllArgsConstructor @NoArgsConstructor
    public static class ClientInfo {
        private Long id;
        private String firstName, secondName, firstLastName, secondLastName;
        private String documentType;
        private Integer document;
        private String cellphone, address, email;
    }

    @Data @AllArgsConstructor @NoArgsConstructor
    public static class MechanicInfo {
        private Long id;
        private String firstName, secondName, firstLastName, secondLastName;
        private String documentType;
        private Integer document;
        private String cellphone, address, email;
        private String status;
    }

    @Data @AllArgsConstructor @NoArgsConstructor
    public static class SparePartItem {
        private Long id;
        private String name;
        private Integer quantity;
        private Double unitPrice;
        private Double lineTotal;
    }

    @Data @AllArgsConstructor @NoArgsConstructor
    public static class ServiceItem {
        private Long id;
        private String name;
        private Integer estimatedTime;
        private Double unitPrice;
        private Double lineTotal;
    }
}
