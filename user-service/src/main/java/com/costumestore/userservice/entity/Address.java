package com.costumestore.userservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tblAddress")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "City", length = 100)
    private String city;

    @Column(name = "Street", length = 255)
    private String street;
}
