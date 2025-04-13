package com.carcenter.car_center_api.invoice.entities;

import com.carcenter.car_center_api.client.entities.Client;
import com.carcenter.car_center_api.invoicedetail.entities.InvoiceDetail;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "invoices")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InvoiceDetail> details;

    @Column(nullable = false)
    private Double subtotal;

    @Column(nullable = false)
    private Double tax;      // IVA 19%

    @Column(nullable = false)
    private Double total;
}