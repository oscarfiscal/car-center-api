package com.carcenter.car_center_api.sparepart.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "spare_parts",
        uniqueConstraints = @UniqueConstraint(columnNames = "name")
)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SparePart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100, unique = true)
    private String name;

    @Column(name = "inventory_quantity", nullable = false)
    private Integer inventoryQuantity;

    @Column(nullable = false, length = 200)
    private String supplier;

    @Column(name = "unit_price", nullable = false)
    private Double unitPrice;
}