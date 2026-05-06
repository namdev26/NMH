package com.costumestore.userservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tblUser")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "Discriminator", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Username", nullable = false, unique = true, length = 100)
    private String username;

    @Column(name = "Password", nullable = false, length = 255)
    private String password;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "AddressId")
    private Address address;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "FullNameId")
    private FullName fullName;
}
