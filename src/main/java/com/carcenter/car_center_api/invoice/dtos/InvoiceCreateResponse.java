package com.carcenter.car_center_api.invoice.dtos;

import lombok.*;

import java.math.BigDecimal;
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
    private BigDecimal subtotal;
    private BigDecimal tax;
    private BigDecimal total;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
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
    @Builder
    public static class InvoiceDetailInfo {
        private String description;
        private Integer quantity;
        private BigDecimal unitPrice;
        private BigDecimal discount;
        private BigDecimal lineTotal;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
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
}
