package com.costumestore.userservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tblFullName")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FullName {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "FirstName", length = 100)
    private String firstName;

    @Column(name = "LastName", length = 100)
    private String lastName;
}
