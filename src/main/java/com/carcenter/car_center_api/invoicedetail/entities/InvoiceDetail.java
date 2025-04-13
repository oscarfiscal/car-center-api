package com.carcenter.car_center_api.invoicedetail.entities;

import com.carcenter.car_center_api.invoice.entities.Invoice;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "invoice_details")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class InvoiceDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id", nullable = false)
    private Invoice invoice;

    @Column(nullable = false, length = 255)
    private String description;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false)
    private Double unitPrice;

    @Column(nullable = false)
    private Double discount;

    @Column(name = "line_total", nullable = false)
    private Double lineTotal;
}