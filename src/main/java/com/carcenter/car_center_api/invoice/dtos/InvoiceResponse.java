package com.carcenter.car_center_api.invoice.dtos;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class InvoiceResponse {

    private Long id;
    private LocalDateTime createdAt;
    private ClientInfo client;
    private List<DetailInfo> details;
    private Double subtotal;
    private Double tax;
    private Double total;

    @Data @AllArgsConstructor @NoArgsConstructor
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

    @Data @AllArgsConstructor @NoArgsConstructor
    public static class DetailInfo {
        private String description;
        private Integer quantity;
        private Double unitPrice;
        private Double discount;
        private Double lineTotal;
    }
}
