package com.carcenter.car_center_api.entities;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad que representa a un mecánico.
 */
@Entity
@Table(name = "mechanics")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Mechanic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String secondName;
    private String firstLastName;
    private String secondLastName;
    private String document;
    private String documentType;
    private String cellphone;
    private String address;
    private String email;
    private String status;
}
