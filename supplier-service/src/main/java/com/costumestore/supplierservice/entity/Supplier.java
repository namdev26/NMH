package com.costumestore.supplierservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tblSupplier")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Email", length = 255)
    private String email;

    @Column(name = "Contact", length = 500)
    private String contact;

    @Column(name = "Name", nullable = false, length = 200)
    private String name;
}
