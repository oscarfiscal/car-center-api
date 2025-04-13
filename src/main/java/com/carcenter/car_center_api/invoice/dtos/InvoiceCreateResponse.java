package com.carcenter.car_center_api.invoice.dtos;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceCreateResponse {

    private Long id;
    private LocalDateTime createdAt;
    private ClientInfo client;
    private List<MechanicInfo> mechanics;
    private List<InvoiceDetailInfo> details;
    private Double subtotal;
    private Double tax;
    private Double total;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ClientInfo {
        private Long id;
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

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InvoiceDetailInfo {
        private String description;
        private Integer quantity;
        private Double unitPrice;
        private Double discount;
        private Double lineTotal;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MechanicInfo{
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
}