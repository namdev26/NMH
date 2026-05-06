package com.costumestore.billcostumeservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tblBill")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CustomerId")
    private Long customerId;

    @Column(name = "CreatedTime", nullable = false)
    private LocalDateTime createdTime;
}
