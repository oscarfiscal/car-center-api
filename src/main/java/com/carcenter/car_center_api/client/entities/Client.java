package com.carcenter.car_center_api.client.entities;

import com.carcenter.car_center_api.vehicle.entities.Vehicle;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(
        name = "clients",
        uniqueConstraints = @UniqueConstraint(columnNames = {"document_type", "document"})
)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "document_type", nullable = false, length = 2)
    private String documentType;

    @Column(name = "document", nullable = false)
    private Integer document;

    @Column(name = "first_name", nullable = false, length = 30)
    private String firstName;

    @Column(name = "second_name", nullable = false, length = 30)
    private String secondName;

    @Column(name = "first_last_name", nullable = false, length = 30)
    private String firstLastName;

    @Column(name = "second_last_name", nullable = false, length = 30)
    private String secondLastName;

    @Column(nullable = false, length = 15)
    private String cellphone;

    @Column(nullable = false, length = 20)
    private String address;

    @Column(nullable = false, length = 100)
    private String email;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vehicle> vehicles;
}
