package com.carcenter.car_center_api.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "clients")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    @Id
    private String document;

    private String firstName;
    private String secondName;
    private String firstLastName;
    private String secondLastName;
    private String documentType;
    private String cellphone;
    private String address;
    private String email;

    @OneToMany(mappedBy = "client")
    private List<Vehicle> vehicles;
}


